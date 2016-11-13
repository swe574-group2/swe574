package plugin.cat.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Arda on 11/12/2016.
 */
@Data
@Entity
public class UserToken {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    private String token;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
