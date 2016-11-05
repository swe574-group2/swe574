package plugin.cat.model;

import com.sun.istack.internal.Nullable;
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

    @Nullable
    @OneToMany(cascade = CascadeType.ALL)
    private List<AnnotationBodyElement> items;
}
