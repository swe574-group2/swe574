package plugin.cat.annotation.model;

import com.sun.istack.internal.Nullable;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Arda on 10/23/2016.
 */
@Data
@MappedSuperclass
public class AnnotationBody extends AbstractEntity {

    @Nullable
    private String purpose;

    // Contains the body text
    @Column(columnDefinition = "TEXT")
    @Nullable
    private String value;

    @Nullable
    private String format;

    @Nullable
    private String language;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    private AnnotationSource source;
}
