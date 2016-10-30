package plugin.cat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plugin.cat.model.Annotation;
import plugin.cat.model.AnnotationTarget;
import plugin.cat.model.Selector;
import plugin.cat.repository.AnnotationRepository;
import plugin.cat.service.IAnnotationService;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public Iterable<Annotation> getAnnotations() {
        return annotationRepository.findAll();
    }

    @Override
    public Iterable<Annotation> getAnnotationsById(String id) {
        return annotationRepository.findAllById(id);
    }

    @Override
    public void saveAnnotation(Annotation annotation) {
        annotationRepository.save(annotation);
    }

    @Override
    public long countAnnotationsById(String id) {
        return annotationRepository.countById(id);
    }

    @Override
    public Iterable<Annotation> getAnnotationsByTextSelection(String id, int start, int end) {
        // First get all annotations on the given id (url)
        Iterable<Annotation> allAnnotations = annotationRepository.findAllById(id);
        List<Annotation> matches = new ArrayList<Annotation>();

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
