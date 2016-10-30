package plugin.cat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plugin.cat.model.Annotation;
import plugin.cat.repository.AnnotationRepository;
import plugin.cat.service.IAnnotationService;

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
}
