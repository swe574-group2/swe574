package plugin.cat.annotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import plugin.cat.annotation.model.Annotation;
import plugin.cat.annotation.request.AnnotationIdRequest;
import plugin.cat.annotation.request.AnnotationTargetRequestBody;
import plugin.cat.annotation.request.AnnotationsByTextSelectorRequest;
import plugin.cat.annotation.service.IAnnotationService;

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

    @ResponseStatus(value = HttpStatus.OK, reason = "Annotation is added successfully.")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addAnnotation(@RequestBody Annotation annotation) {
        annotationService.saveAnnotation(annotation);
    }

    @RequestMapping(value = "/countById", method = RequestMethod.POST)
    public long countAnnotationsById(@RequestBody AnnotationIdRequest annotationIdRequest) {
        return annotationService.countAnnotationsById(annotationIdRequest.getId());
    }

    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public long annotationCount(@RequestBody AnnotationTargetRequestBody annotationIdRequest) {
        return annotationService.countAnnotationByTarget(annotationIdRequest.getTarget());
    }

    @RequestMapping(value = "/annotationByTextSelection", method = RequestMethod.POST)
    public Iterable<Annotation> getAnnotationsByTextSelection(@RequestBody AnnotationsByTextSelectorRequest annotationsByTextSelectorRequest) {
        return annotationService.getAnnotationsByTextSelection(
                annotationsByTextSelectorRequest.getId(),
                annotationsByTextSelectorRequest.getStart(),
                annotationsByTextSelectorRequest.getEnd());
    }
}
