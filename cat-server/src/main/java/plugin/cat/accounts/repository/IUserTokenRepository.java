package plugin.cat.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import plugin.cat.accounts.model.UserToken;

public interface IUserTokenRepository extends CrudRepository<UserToken, Long> {
}