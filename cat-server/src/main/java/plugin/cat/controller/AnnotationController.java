package plugin.cat.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plugin.cat.model.Annotation;
import plugin.cat.model.CountPostBody;
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
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Annotation getAnnotation(@PathVariable("id") short id) {
        return annotationService.getAnnotation(id);
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addAnnotation(@RequestBody Annotation annotation) {
        annotationService.saveAnnotation(annotation);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAnnotation(@PathVariable("id") short id) {
        annotationService.deleteAnnotation(id);
    }

    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public int getAnnotationCount(@RequestBody CountPostBody countPostBody) {
        Integer count = annotationService.getAnnotationCount(countPostBody.context);
        return count.intValue();
    }
}
