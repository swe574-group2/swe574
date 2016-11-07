package plugin.cat.annotation.model;

import lombok.Data;

import javax.persistence.*;

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
    @OneToOne(cascade = CascadeType.ALL)
    private Selector selector;
}
