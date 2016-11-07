package plugin.cat.annotation.model;

import lombok.Data;
import javax.persistence.MappedSuperclass;

/**
 * Created by tolgacaner on 31/10/16.
 */
@Data
@MappedSuperclass
public class AnnotationHelper extends AbstractEntity {
    private AnnotationRefine refinedBy;

}
