package plugin.cat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by okanm on 17.10.2016.
 * Entity representation of the annotation table that stores annotation information.
 */
@Data
@Entity
public class Annotation {
    @Id
    @GeneratedValue
    private long id;

    @JsonProperty("@context")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String context;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String annotationId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String motivation;

    @OneToOne
    @JoinColumn(name="AnnotationBody", nullable = false)
    @Class()
    private AnnotationBody body;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String target;
}
