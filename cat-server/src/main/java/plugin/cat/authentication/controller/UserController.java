package plugin.cat.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import plugin.cat.authentication.model.User;
import plugin.cat.authentication.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by tolgacaner on 05/11/16.
 */
@RestController
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@RequestBody User user) {
        User userFromDB = getUserByNickname(user.getNickname());
        if (userFromDB == null) {
            userService.registerUser(user);
            return "User is created successfully";
        } else {
            return "There is already a user with this nickname";
        }
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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/add", "/delete"}, method = RequestMethod.POST)
    public void annotationPrivateRedirectGateway(HttpServletRequest request, HttpServletResponse response) {
        redirectGateway(request,response);
    }

    @RequestMapping(value = {"/advancedSearch", "/basicSearch","/basicSearch/count","/advancedSearch/count","/source","/source/count"}, method = RequestMethod.POST)
    public void annotationPublicRedirectGateway(HttpServletRequest request, HttpServletResponse response) {
        redirectGateway(request,response);
    }

    private void redirectGateway(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        response.setStatus(308) ;
        response.addHeader("Location", "http://localhost:8081" + path);
    }
}