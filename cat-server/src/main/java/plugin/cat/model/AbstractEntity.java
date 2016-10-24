package plugin.cat.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by tolgacaner on 23/10/16.
 */
@Data
@MappedSuperclass
public abstract class AbstractEntity {
    // Database primary key
    @Id
    @GeneratedValue
    private long entityId;

    // W3C id
    @Column(nullable = false, columnDefinition = "TEXT")
    private String id;

    // For selectors: "TextPositionSelector", "SvgSelector", etc
    // For targets: "Text", "Image"
    @Column(nullable = false)
    private String type;
}
