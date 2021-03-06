package plugin.cat.annotation.model;

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

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Creator creator;

    @JsonProperty("private")
    @Column(nullable = false)
    private boolean isPrivate = false;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp created;

    @OneToOne(cascade = CascadeType.ALL)
    private AnnotationGenerator generator;

    // Caution! This code may cause errors on some MYSQL versions.
    // Ex. Prefer XAMPP instead of WampServer
    @JsonProperty("generated")
    private Timestamp generatedTimeStamp;

    @OneToOne(cascade = CascadeType.ALL)
    private AnnotationStylesheet stylesheet;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private AnnotationBody body;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private AnnotationTarget target;
}
