package plugin.cat.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by tolgacaner on 31/10/16.
 */
@Data
@Entity
public class AnnotationSource extends AbstractEntity {
    private String format;
    private String language;
    @ManyToOne(cascade = CascadeType.ALL)
    private Creator creator;
}
