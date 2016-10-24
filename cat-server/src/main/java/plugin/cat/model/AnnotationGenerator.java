package plugin.cat.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by okanm on 24.10.2016.
 */
@Data
@Entity
public class AnnotationGenerator extends AbstractEntity {

    private String name;

    @Column(columnDefinition = "TEXT")
    private String homePage;
}
