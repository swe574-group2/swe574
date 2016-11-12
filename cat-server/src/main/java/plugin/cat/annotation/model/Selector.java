package plugin.cat.annotation.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Arda on 10/23/2016.
 */

@Data
@Entity
public class Selector extends AbstractEntity {
    // Only used for text selections: represents the starting index of selected text in html page
    private int start;

    // Only used for text selections: represents the ending index of selected text in html page
    private int end;

    // Value is simply a text for text selectors, and svg value for image selectors
    @Column(columnDefinition = "TEXT")
    private String value;
    // conformsTo for fragment selection i.e.: http://www.w3.org/TR/media-frags/
    //xywh=160,120,320,240        # => results in a 320x240 box at x=160 and y=120
    @Column(columnDefinition = "TEXT")
    private String conformsTo;
}
