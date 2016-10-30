package plugin.cat.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Arda on 10/23/2016.
 */

@Data
@Entity
public class Selector extends AbstractEntity {
    // Only used for image selections
    private int start;

    // Only used for image selections
    private int end;

    // Value is simply a text for text selectors, and svg value for image selectors
    @Column(columnDefinition = "TEXT")
    private int value;
}
