package plugin.cat.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import plugin.cat.authentication.model.User;
import plugin.cat.authentication.service.IUserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by tolgacaner on 05/11/16.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = {"/testing"}, method = RequestMethod.GET)
    public String getTesting() {
        return "TESTING";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/byId/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") long id) {
        return userService.getUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/byNickname/{nickname}", method = RequestMethod.GET)
    public User getUserByNickname(@PathVariable("nickname") String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "User is created successfully.")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        if (userService.registerUser(user) == null)
            return userService.registerUser(user);
        return null;
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

    // THESE ARE FOR THE DEMO PURPOSES.
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public String test1() {
        return "success";
    }

    @PreAuthorize("hasRole('MEMBER')")
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public String test2() {
        return "success";
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'MEMBER'})")
    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public String test3() {
        return "success";
    }

    // These methods call their counterparts in the Annotation server

    @RequestMapping(value = "/annotation", method = RequestMethod.GET)
    public void getAnnotations(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8080/annotation/");
    }
}
