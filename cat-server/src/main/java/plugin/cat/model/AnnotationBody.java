package plugin.cat.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

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
}
