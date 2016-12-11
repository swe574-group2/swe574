package plugin.cat.annotation.request;

import lombok.Data;

import java.util.Date;

/**
 * Created by tolgacaner on 29/10/16.
 */
@Data
public class AnnotationRequest {

    private String targetSource;

    private String nickname;

    private String bodyValue;

    private Date startDate;

    private Date endDate;

    private String selectorType;

    private String motivation;

    private String id;

    boolean isPrivate;

    private int pageNumber;

    private int pageSize;
}
