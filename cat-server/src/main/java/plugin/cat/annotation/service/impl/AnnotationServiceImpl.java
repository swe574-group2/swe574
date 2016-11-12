package plugin.cat.annotation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plugin.cat.annotation.model.Annotation;
import plugin.cat.annotation.model.AnnotationTarget;
import plugin.cat.annotation.model.Selector;
import plugin.cat.annotation.repository.AnnotationRepository;
import plugin.cat.annotation.service.IAnnotationService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by okanm on 17.10.2016.
 * This class is the business layer of the annotation operations.
 */
@Service
public class AnnotationServiceImpl implements IAnnotationService {

    @Autowired
    AnnotationRepository annotationRepository;

    /**
     * @return Iterable Annotation object
     * @should invoke findAll method of the annotation repository
     * @should return what the annotation repository returns
     */
    @Override
    public Iterable<Annotation> getAnnotations() {
        return annotationRepository.findAll();
    }

    /**
     * @param id of annotation that will be listed
     * @return Iterable Annotation object by id
     * @should invoke findAllById method of the annotation repository
     * @should return what the annotation repository returns
     */
    @Override
    public Iterable<Annotation> getAnnotationsById(String id) {
        return annotationRepository.findAllById(id);
    }

    /**
     * @param annotation to be saved
     * @should invoke save method of the annotation repository
     */
    @Override
    public void saveAnnotation(Annotation annotation) {
        annotationRepository.save(annotation);
    }

    /**
     * @param id of the annotations that will be counted
     * @return annotation count number by id
     * @should invoke countById method of the annotation repository
     * @should return what the annotation repository returns
     */
    @Override
    public long countAnnotationsById(String id) {
        return annotationRepository.countById(id);
    }


    /**
     * @param target of the annotations that will be counted
     * @return annotation count number by target
     * @should invoke countByTarget method of the annotation repository
     * @should return what the annotation repository returns
     */
    @Override
    public long annotationCountForTarget(String target) {
        return annotationRepository.countByTarget(target);
    }

    /**
     * @param id    of annotation that will be listed
     * @param start of annotation that will be listed
     * @param end   of annotation that will be listed
     * @return Iterable Annotation object by id, start and end parameters
     * @should invoke findAllById method of the annotation repository
     */
    @Override
    public Iterable<Annotation> getAnnotationsByTextSelection(String id, int start, int end) {
        // First get all annotations on the given id (url)
        Iterable<Annotation> allAnnotations = annotationRepository.findAllById(id);
        List<Annotation> matches = new ArrayList<>();

        Iterator iterator = allAnnotations.iterator();
        while (iterator.hasNext()) {
            Annotation annotation = (Annotation) iterator.next();

            AnnotationTarget target = annotation.getTarget();
            Selector selector = target.getSelector();

            // Check if there are any annotations on given indexes
            if (selector.getStart() == start && selector.getEnd() == end) {
                matches.add(annotation);
            }
        }
        return matches;
    }
}
