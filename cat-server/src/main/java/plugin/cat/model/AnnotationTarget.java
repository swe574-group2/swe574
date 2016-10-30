package plugin.cat.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by Arda on 10/23/2016.
 */

@Data
@Entity
public class AnnotationTarget extends AbstractEntity {
    // Url
    @Column(nullable = false, columnDefinition = "TEXT")
    private String source;

    // Selection information
    @OneToOne
    @JoinColumn(nullable = false)
    private Selector selector;
}
