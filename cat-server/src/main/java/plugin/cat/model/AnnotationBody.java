package plugin.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by Arda on 10/23/2016.
 */
@Data
@Entity
public class AnnotationBody extends AbstractEntity {
    @Column(nullable = false, columnDefinition = "TEXT")
    private String purpose;

    // Contains the text
    @Column(nullable = false, columnDefinition = "TEXT")
    private String value;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String format;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String language;
}