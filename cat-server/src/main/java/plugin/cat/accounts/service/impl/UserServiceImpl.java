package plugin.cat.accounts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plugin.cat.accounts.model.User;
import plugin.cat.accounts.repository.IUserRepository;
import plugin.cat.accounts.service.IUserService;

/**
 * Created by okanm on 7.11.2016.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

/*  Will be enabled after security
    @Autowired
    private PasswordEncoder passwordEncoder;*/

    @Override
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getMember(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getMemberByNickname(String nickname) {
        return userRepository.findOneByNickname(nickname);
    }

    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserByNickname(String nickname) {
        userRepository.deleteByNickname(nickname);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.getMemberByNickname(s);
    }
}
