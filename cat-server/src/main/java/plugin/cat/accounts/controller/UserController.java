package plugin.cat.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import plugin.cat.accounts.model.LoginRequest;
import plugin.cat.accounts.model.User;
import plugin.cat.accounts.model.UserToken;
import plugin.cat.accounts.service.IUserService;
import plugin.cat.accounts.service.IUserTokenService;
import plugin.cat.accounts.security.TokenUtil;

import javax.validation.Valid;

/**
 * Created by tolgacaner on 05/11/16.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserTokenService userTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/byNickname/{nickname}/", method = RequestMethod.GET)
    public User getUserByNickname(@PathVariable("nickname") String nickname) {
        return userService.getMemberByNickname(nickname);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }

//    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    public Principal authenticate(Principal user) {
//        return user;
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{nickname}", method = RequestMethod.DELETE)
    public void deleteBulletin(@Valid @PathVariable("nickname") String nickname) {
        userService.deleteUserByNickname(nickname);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody LoginRequest authRequest) {
        String nickname = authRequest.getNickname();
        String password = authRequest.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nickname, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getMemberByNickname(nickname);

        String token = TokenUtil.createToken(nickname, password);

        saveUserTokenToDB(token, user);

        return user;
    }

    private void saveUserTokenToDB(String token, User user) {
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUser(user);
        userTokenService.createToken(userToken);
    }
}
