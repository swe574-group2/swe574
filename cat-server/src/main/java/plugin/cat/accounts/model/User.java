package plugin.cat.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import plugin.cat.accounts.util.UserType;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by okanm on 7.11.2016.
 */
@Data
@Entity
public class User implements UserDetails {

    @Id
    @JsonIgnore
    @GeneratedValue
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    private String userType;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserToken token;

    @JsonIgnore
    private String password;

    private boolean isEnabled;

    private boolean isAccountNonExpired;

    private boolean isCredentialsNonExpired;

    private boolean isAccountNonLocked;

    public User() {
        super();
        this.isEnabled = true;
        this.isAccountNonExpired = true;
        this.isCredentialsNonExpired = true;
        this.isAccountNonLocked = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.getUserType().toString()));
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
