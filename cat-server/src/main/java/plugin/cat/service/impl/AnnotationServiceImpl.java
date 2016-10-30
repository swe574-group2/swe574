package plugin.cat.service.impl;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plugin.cat.model.Annotation;
import plugin.cat.repository.AnnotationRepository;
import plugin.cat.service.IAnnotationService;

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
    public Annotation getAnnotation(long id) {
        return annotationRepository.findOne(id);
    }

    @Override
    public void saveAnnotation(Annotation annotation) {
        annotationRepository.save(annotation);
    }

    @Override
    public void deleteAnnotation(long id) {
        annotationRepository.delete(id);
    }

    @Override
    public Integer getAnnotationCount(String url) {
        Iterable<Annotation> all = annotationRepository.findAllByContext(url);
        return Lists.newArrayList(all).size();
    }
}
