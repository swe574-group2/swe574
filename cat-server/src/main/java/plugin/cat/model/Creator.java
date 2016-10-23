package plugin.cat.model;

import lombok.Data;
import javax.persistence.*;

/**
 * Created by tolgacaner on 23/10/16.
 */

@Data
@Entity
public class Creator extends AbstractEntity {
<<<<<<< Updated upstream
=======

    @Id
    @GeneratedValue
    private long creatorId;

>>>>>>> Stashed changes
    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String nickName;
}
