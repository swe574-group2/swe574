package plugin.cat.accounts.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import plugin.cat.accounts.model.User;
import plugin.cat.accounts.model.UserToken;
import plugin.cat.accounts.service.IUserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Arda on 11/13/2016.
 */
public class XAuthTokenFilter extends GenericFilterBean {

    private final UserDetailsService detailsService;
    private final IUserService userService;
    private String xAuthTokenHeaderName = "x-auth-token";

    public XAuthTokenFilter(UserDetailsService userDetailsService, IUserService userService) {
        this.detailsService = userDetailsService;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) arg0;
            String authToken = httpServletRequest.getHeader(this.xAuthTokenHeaderName);

            if (StringUtils.hasText(authToken)) {
                String nickname = TokenUtil.obtainNicknameFromToken(authToken);
                UserDetails details = detailsService.loadUserByUsername(nickname);
                User user = userService.getMemberByNickname(nickname);
                UserToken userToken = user.getToken();
                if (TokenUtil.validateToken(authToken, user.getNickname(), user.getPassword(), userToken)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                    System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
                }
            }

            filterChain.doFilter(arg0, arg1);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
