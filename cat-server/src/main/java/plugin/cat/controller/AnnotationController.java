package plugin.cat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plugin.cat.model.Annotation;
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
    
    @RequestMapping(value = "/annotation", method = RequestMethod.HEAD)
    public String getAnnotationCount() {
        return annotationService.getAnnotationCount();
    }
    
    @RequestMapping(value = "/annotation/{id}", method = RequestMethod.DELETE)
    public void deleteAnnotation(@PathVariable("id") short id) {
        annotationService.deleteAnnotation(id);
    }
    
    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public int addAnnotation(@RequestBody String url) {
        return 12883;
    }
    
    
}
