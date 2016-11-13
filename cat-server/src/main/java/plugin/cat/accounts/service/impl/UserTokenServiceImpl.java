package plugin.cat.accounts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plugin.cat.accounts.model.UserToken;
import plugin.cat.accounts.repository.IUserTokenRepository;
import plugin.cat.accounts.service.IUserTokenService;

/**
 * Created by Arda on 11/12/2016.
 */
@Service
public class UserTokenServiceImpl implements IUserTokenService {
    @Autowired
    IUserTokenRepository userTokenRepository;

    @Override
    public void createToken(UserToken token) {
        userTokenRepository.save(token);
    }

    @Override
    public void deleteToken(UserToken token) {
        userTokenRepository.delete(token);
    }

    @Override
    public UserToken getTokenById(long id) {
        return userTokenRepository.findOne(id);
    }
}
