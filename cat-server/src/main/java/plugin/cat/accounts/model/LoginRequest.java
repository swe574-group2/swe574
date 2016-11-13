package plugin.cat.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Arda on 11/12/2016.
 */
@Data
@Entity
public class LoginRequest {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long entityId;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;
}
