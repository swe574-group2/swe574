package plugin.cat.annotation.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by okanm on 24.10.2016.
 */
@Data
@Entity
public class AnnotationStylesheet extends AbstractEntity {

    @Column(columnDefinition = "TEXT")
    private String value;
}
