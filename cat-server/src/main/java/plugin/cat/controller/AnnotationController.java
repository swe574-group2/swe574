package plugin.cat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plugin.cat.model.Annotation;
import plugin.cat.request.AnnotationIdRequest;
import plugin.cat.service.IAnnotationService;

/**
 * Created by okanm on 17.10.2016.
 * Controller class that handles request and response methods of the annotation operations.
 */
@RestController
@RequestMapping(value = "/annotation")
public class AnnotationController {

    @Autowired
    IAnnotationService annotationService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Iterable<Annotation> getAnnotations() {
        return annotationService.getAnnotations();
    }

    @RequestMapping(value = "/listById", method = RequestMethod.POST)
    public Iterable<Annotation> getAnnotationsById(@RequestBody AnnotationIdRequest annotationIdRequest) {
        return annotationService.getAnnotationsById(annotationIdRequest.getId());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addAnnotation(@RequestBody Annotation annotation) {
        annotationService.saveAnnotation(annotation);
    }

    @RequestMapping(value = "/countById", method = RequestMethod.POST)
    public long countAnnotationsById(@RequestBody AnnotationIdRequest annotationIdRequest) {
        return annotationService.countAnnotationsById(annotationIdRequest.getId());
    }
}
