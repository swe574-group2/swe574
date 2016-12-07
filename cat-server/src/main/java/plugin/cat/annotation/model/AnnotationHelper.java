package plugin.cat.annotation.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by tolgacaner on 31/10/16.
 */
@Data
@Entity
public class AnnotationHelper extends AbstractEntity {

    @OneToOne
    private AnnotationRefine refinedBy;
}
