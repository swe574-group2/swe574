package plugin.cat.authentication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plugin.cat.authentication.model.User;

/**
 * Created by okanm on 7.11.2016.
 */
@Repository
public interface IUserRepository extends CrudRepository<User, Long> {

    User findOneByNickname(String nickname);

    void deleteByNickname(String nickname);
}
