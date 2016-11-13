package plugin.cat.accounts.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import plugin.cat.accounts.service.IUserService;

/**
 * Created by okanm on 7.11.2016. THIS CLASS WILL BE USED AS MANUAL CONFIGURATION AFTER DISABLING THE AUTO SECURITY CONFIG.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String API_PATH = "/api/v1";

    public static final String AUTHENTICATE_URL = API_PATH + "/authenticate";
    public static final String STUFF_URL = API_PATH + "/stuff";

    // Spring Boot Actuator services
    public static final String AUTOCONFIG_ENDPOINT = "/autoconfig";
    public static final String BEANS_ENDPOINT = "/beans";
    public static final String CONFIGPROPS_ENDPOINT = "/configprops";
    public static final String ENV_ENDPOINT = "/env";
    public static final String MAPPINGS_ENDPOINT = "/mappings";
    public static final String METRICS_ENDPOINT = "/metrics";
    public static final String SHUTDOWN_ENDPOINT = "/shutdown";

    @Autowired
    private IUserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // For now we will disable security.

        http
                .httpBasic()
                .and()
                .authorizeRequests().antMatchers("*//*", "/users*", "/annotation*").permitAll()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter =
                new XAuthTokenConfigurer(userDetailsService, userService);
        http.apply(securityConfigurerAdapter);

//        http.
//                csrf().disable().
//                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
//                and().
//                authorizeRequests().
//                antMatchers(actuatorEndpoints()).hasRole(backendAdminRole).
//                anyRequest().authenticated().
//                and().
//                anonymous().disable().
//                exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());

//        http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class).
//                addFilterBefore(new ManagementEndpointAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
    }

    private String[] actuatorEndpoints() {
        return new String[]{AUTOCONFIG_ENDPOINT, BEANS_ENDPOINT, CONFIGPROPS_ENDPOINT,
                ENV_ENDPOINT, MAPPINGS_ENDPOINT,
                METRICS_ENDPOINT, SHUTDOWN_ENDPOINT};
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider ap = new DaoAuthenticationProvider();
        ap.setPasswordEncoder(passwordEncoder());
        ap.setUserDetailsService(userService);
        return ap;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
