package plugin.cat.annotation.request;

import lombok.Data;

/**
 * Created by Arda on 10/30/2016.
 *
 * This class represents a request for a particular text selection, specified by its start and end indexes
 * on the html page
 */
@Data
public class AnnotationsByTextSelectorRequest {
    private String id;
    private int start;
    private int end;
}
