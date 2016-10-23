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
public class Annotation extends AbstractEntity {
    @JsonProperty("@context")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String context;

    // "Comment", "Translation" etc
    @Column(nullable = false)
    private String motivation;

    @OneToOne
    @JoinColumn(name="AnnotationBody", nullable = false)
    private AnnotationBody body;

    @OneToOne
    @JoinColumn(name="AnnotationTarget", nullable = false)
    private AnnotationTarget target;
}
