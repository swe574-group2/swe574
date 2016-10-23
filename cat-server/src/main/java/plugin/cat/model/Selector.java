package plugin.cat.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Arda on 10/23/2016.
 */
@Data
@Entity
public abstract class Selector extends AbstractEntity {
    // Only used for image selections
    @Column(columnDefinition = "INTEGER")
    private int start;

    // Only used for image selections
    @Column(columnDefinition = "INTEGER")
    private int end;

    // Value is simply a text for text selectors, and svg value for image selectors
    @Column(columnDefinition = "TEXT")
    private int value;
}
