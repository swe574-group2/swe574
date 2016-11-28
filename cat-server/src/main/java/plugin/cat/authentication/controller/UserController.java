package plugin.cat.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import plugin.cat.annotation.model.Annotation;
import plugin.cat.annotation.util.AnnotationRoutingUtil;
import plugin.cat.authentication.model.User;
import plugin.cat.authentication.service.IUserService;

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
        System.out.println(id);
        return userService.getUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/byNickname/{nickname}", method = RequestMethod.GET)
    public User getUserByNickname(@PathVariable("nickname") String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "User is created successfully.")
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
    @RequestMapping(value = {"", "/annotation"}, method = RequestMethod.GET)
    public String getAnnotations() {
        return AnnotationRoutingUtil.makeHttpGet("http://localhost:8080/annotation/");
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Annotation is added successfully.")
    @RequestMapping(value = "/annotation", method = RequestMethod.POST)
    public void addAnnotation(@RequestBody Annotation annotation) {
        AnnotationRoutingUtil.createAnnotation(annotation);
    }
}
