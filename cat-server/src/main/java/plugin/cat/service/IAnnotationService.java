package plugin.cat.service;

import plugin.cat.model.Annotation;

/**
 * Created by okanm on 17.10.2016.
 * This class is the abstract representation of the service layer for the annotation operations.
 */
public interface IAnnotationService {
    Iterable<Annotation> getAnnotations();

    Iterable<Annotation> getAnnotationsById(String id);

    void saveAnnotation(Annotation annotation);

    long countAnnotationsById(String id);

    Iterable<Annotation> getAnnotationsByTextSelection(String id, int start, int end);
}
