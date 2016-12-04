package plugin.cat.annotation.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Arda on 10/23/2016.
 */
@Data
@Entity
public class AnnotationBody extends AbstractEntity {
    private String purpose;

    // Contains the body text
    @Column(columnDefinition = "TEXT")
    private String value;

    private String format;

    private String language;

    @OneToOne(cascade = CascadeType.ALL)
    private AnnotationSource source;
}
