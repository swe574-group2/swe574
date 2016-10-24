package plugin.cat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @Column(nullable = false)
    private String motivation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "entity")
    private Creator creator;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp created;

    @OneToOne
    private AnnotationGenerator generator;

    private Timestamp generated;

    @OneToOne
    private AnnotationStylesheet stylesheet;

    @OneToOne
    private AnnotationBody body;

    @OneToOne
    @JoinColumn(nullable = false)
    private AnnotationTarget target;
}
