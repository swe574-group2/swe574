package plugin.cat.annotation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    @ManyToOne(cascade = CascadeType.ALL)
    private Creator creator;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp created;

    @OneToOne(cascade = CascadeType.ALL)
    private AnnotationGenerator generator;

    // Caution! This code may cause errors on some MYSQL versions.
    // Ex. Prefer XAMPP instead of WampServer
    private Timestamp generated;

    @OneToOne(cascade = CascadeType.ALL)
    private AnnotationStylesheet stylesheet;


    @OneToMany(cascade = CascadeType.ALL)
    private List<AnnotationBodyParent> body;

    @OneToOne(cascade = CascadeType.ALL)
    private AnnotationTarget target;
}
