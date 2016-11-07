package plugin.cat.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import plugin.cat.accounts.model.User;
import plugin.cat.accounts.service.IUserService;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by tolgacaner on 05/11/16.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

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

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal authenticate(Principal user) {
        return user;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{nickname}", method = RequestMethod.DELETE)
    public void deleteBulletin(@Valid @PathVariable("nickname") String nickname) {
        userService.deleteUserByNickname(nickname);
    }
}
