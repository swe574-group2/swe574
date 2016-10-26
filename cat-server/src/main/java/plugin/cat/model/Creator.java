package plugin.cat.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by tolgacaner on 23/10/16.
 */

@Data
@Entity
public class Creator extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;
}
