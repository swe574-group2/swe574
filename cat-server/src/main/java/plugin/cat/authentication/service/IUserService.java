package plugin.cat.authentication.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import plugin.cat.authentication.model.User;

/**
 * Created by okanm on 7.11.2016.
 */
public interface IUserService extends UserDetailsService {

    Iterable<User> getUsers();

    User getUser(long id);

    User getUserByNickname(String nickname);

    void registerUser(User user);

    void deleteUserByNickname(String nickname);
}
