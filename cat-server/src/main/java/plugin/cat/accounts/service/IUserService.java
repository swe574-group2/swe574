package plugin.cat.accounts.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import plugin.cat.accounts.model.User;

/**
 * Created by okanm on 7.11.2016.
 */
public interface IUserService extends UserDetailsService {

    Iterable<User> getUsers();

    User getMember(long id);

    User getMemberByNickname(String nickname);

    void registerUser(User user);

    void deleteUserByNickname(String nickname);
}
