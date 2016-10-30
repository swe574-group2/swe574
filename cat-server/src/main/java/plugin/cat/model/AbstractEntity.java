package plugin.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by tolgacaner on 23/10/16.
 */
@Data
@MappedSuperclass
public class AbstractEntity {
    // Database primary key
    @Id
    @GeneratedValue
    @JsonIgnore
    private long entityId;

    // W3C id
    @Column(columnDefinition = "TEXT")
    private String id;

    // For annotations: "Annotation", etc.
    // For selectors: "TextPositionSelector", "SvgSelector", etc.
    // For targets: "Text", "Image"
    // For creators: "Person", etc.
    private String type;
}
