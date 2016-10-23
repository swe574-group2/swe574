package plugin.cat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by okanm on 17.10.2016.
 * Entity representation of the annotation table that stores annotation information.
 */
@Data
@Entity
public class Annotation extends AbstractAnnotation{
//
    @Id
    @GeneratedValue
    private long annotationId;

    @JsonProperty("@context")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String context;

    @Column(nullable = false)
    private String motivation;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String target;
    /*
    @JsonIgnore
    @ManyToOne(optional = false)
    private Creator creator;
    */
}
