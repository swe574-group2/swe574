package plugin.cat.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Arda on 10/23/2016.
 */
@Data
@Entity
public class AnnotationTarget {
    // Url
    @Column(nullable = false, columnDefinition = "TEXT")
    private String source;

    // Selection information
    @Column(nullable = false, columnDefinition = "TEXT")
    private Selector selector;
}
