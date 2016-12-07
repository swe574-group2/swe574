package plugin.cat.authentication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plugin.cat.authentication.model.User;
import plugin.cat.authentication.repository.IUserRepository;
import plugin.cat.authentication.service.IUserService;

/**
 * Created by okanm on 7.11.2016.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByNickname(String nickname) {
        return userRepository.findOneByNickname(nickname);
    }

    @Override
    public User registerUser(User user) {
        if (userRepository.findOneByNickname(user.getNickname()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void deleteUserByNickname(String nickname) {
        userRepository.deleteByNickname(nickname);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.getUserByNickname(s);
    }
}