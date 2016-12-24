package plugin.cat.authentication.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import plugin.cat.authentication.model.User;
import plugin.cat.authentication.service.IUserService;
import plugin.cat.authentication.util.UserType;

import static com.sun.tools.javac.util.Assert.check;
import static org.junit.Assert.assertNull;
import static plugin.cat.authentication.util.UserType.ROLE_MEMBER;

/**
 * Created by tolgacaner on 24/12/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_mock.xml"})
@Rollback
@Transactional
public class UserServiceImplIntegrationTest {

    @Autowired
    private IUserService userService;

    @Before
    public void setup() {}

    @Test
    public void testRegistration() {
        for (int i = 0; i < 5; i++) {
            int num = i+1;
            User user = new User();
            user.setNickname("nickname" + num);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setName("Name" + num + " Surname" + num);
            user.setCredentialsNonExpired(true);
            user.setEmail("nickname" + num + "@hotmail.com");
            user.setPassword("password" + num);
            user.setUserType(ROLE_MEMBER);
            userService.registerUser(user);
        }
        check(userService.getUser(1).getNickname().equals("nickname1"),true);
        check(userService.getUser(3).getNickname().equals("nickname3"),true);
        check(userService.getUser(5).getNickname().equals("nickname5"),true);
        assertNull(userService.getUser(7));
    }

    @Test
    public void testDeletion() {
        User user = getTestUser();
        User registeredUser = userService.registerUser(user);
        check(registeredUser.equals(user));
        userService.deleteUserByNickname(registeredUser.getNickname());
        assertNull(userService.getUser(registeredUser.getId()));
    }

    @Test
    public void testUserDetailsAuthority() {
        User user = getTestUser();
        User registeredUser = userService.registerUser(user);
        UserDetails userDetails = userService.loadUserByUsername(registeredUser.getNickname());
        check(userDetails.getAuthorities().size() == 1);
        check(userDetails.getAuthorities().toArray()[0].toString().equals("ROLE_ADMIN"));
    }

    public User getTestUser() {
        User user = new User();
        user.setNickname("testNickName");
        user.setName("Bill Testerson");
        user.setEmail("testerson@gmail.com");
        user.setPassword("caputdraconis");
        user.setUserType(UserType.ROLE_ADMIN);
        return user;
    }

}
