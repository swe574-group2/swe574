package plugin.cat.annotation.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by tolgacaner on 05/11/16.
 */
@Data
@Entity
public class AnnotationBodyParent extends AnnotationBody {

    @OneToMany(cascade = CascadeType.ALL)
    private List<AnnotationBodyElement> items;
}
