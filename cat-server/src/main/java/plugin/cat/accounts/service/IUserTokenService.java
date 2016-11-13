package plugin.cat.accounts.service;

import plugin.cat.accounts.model.UserToken;

/**
 * Created by Arda on 11/12/2016.
 */
public interface IUserTokenService {
    void createToken(UserToken token);
    void deleteToken(UserToken token);
    UserToken getTokenById(long id);
}
