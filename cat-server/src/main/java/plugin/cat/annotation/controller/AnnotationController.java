package plugin.cat.annotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import plugin.cat.annotation.model.*;
import plugin.cat.annotation.request.AnnotationRequest;
import plugin.cat.annotation.service.IAnnotationService;
import plugin.cat.annotation.util.Util;

/**
 * Created by okanm on 17.10.2016.
 * Controller class that handles request and response methods of the annotation operations.
 * <p>
 * Note: When making changes to paths of the methods of this class, make sure their counterparts in the authentication server works too
 */
@RestController
public class AnnotationController {

    private IAnnotationService annotationService;

    @Autowired
    public AnnotationController(IAnnotationService annotationService) {
        this.annotationService = annotationService;
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Annotation is added successfully.")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addAnnotation(@RequestBody Annotation annotation) {

        annotationService.saveAnnotation(annotation);
        Util.assignIdsToAnnotation(annotation);
        annotationService.saveAnnotation(annotation);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @Transactional
    public void deleteAnnotation(@PathVariable String id) {
        annotationService.deleteById(id);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public Iterable<Annotation> getAllAnnotations(@RequestBody AnnotationRequest annotationRequest) {
        return annotationService.getAllAnnotations(annotationRequest.getPageNumber(), annotationRequest.getPageSize());
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public long countAllAnnotations() {
        return annotationService.countAllAnnotations();
    }

    @RequestMapping(value = "/public", method = RequestMethod.POST)
    public Iterable<Annotation> getPublicAnnotations(@RequestBody AnnotationRequest annotationRequest) {
        return annotationService.getAllPublicAnnotations(annotationRequest.getPageNumber(), annotationRequest.getPageSize());
    }

    @RequestMapping(value = "/public/count", method = RequestMethod.GET)
    public long countPublicAnnotations() {
        return annotationService.countAllPublicAnnotations();
    }

    @RequestMapping(value = "/byCreator/{creatorNickname}", method = RequestMethod.POST)
    public Iterable<Annotation> getAllAnnotationsByCreator(@PathVariable String creatorNickname, @RequestBody AnnotationRequest annotationRequest) {
        return annotationService.getAllAnnotationsByCreator(creatorNickname, annotationRequest.getPageNumber(), annotationRequest.getPageSize());
    }

    @RequestMapping(value = "/byCreator/{creatorNickname}/count", method = RequestMethod.GET)
    public long countAllAnnotationsByCreator(@PathVariable String creatorNickname) {
        return annotationService.countAllAnnotationsByCreator(creatorNickname);
    }

    @RequestMapping(value = "/source", method = RequestMethod.POST)
    public Iterable<Annotation> getAllAnnotationsByTargetSource(@RequestBody AnnotationRequest annotationRequest) {
        return annotationService.getAllAnnotationsByTargetSource(annotationRequest.getTargetSource(), annotationRequest.getPageNumber(), annotationRequest.getPageSize());
    }

    @RequestMapping(value = "/source/count", method = RequestMethod.POST)
    public long countAllAnnotationsByTargetSource(@RequestBody AnnotationRequest annotationRequest) {
        return annotationService.countAllAnnotationsByTargetSource(annotationRequest.getTargetSource());
    }

    @RequestMapping(value = "/source/public", method = RequestMethod.POST)
    public Iterable<Annotation> getAllPublicAnnotationsByTargetSource(@RequestBody AnnotationRequest annotationRequest) {
        return annotationService.getAllPublicAnnotationsByTargetSource(annotationRequest.getTargetSource(), annotationRequest.getPageNumber(), annotationRequest.getPageSize());
    }

    @RequestMapping(value = "/source/public/count", method = RequestMethod.POST)
    public long countPublicAnnotationsByTargetSource(@RequestBody AnnotationRequest annotationRequest) {
        return annotationService.countAllPublicAnnotationsByTargetSource(annotationRequest.getTargetSource());
    }

    @RequestMapping(value = "/source/byCreator/{creatorNickname}", method = RequestMethod.POST)
    public Iterable<Annotation> getAllAnnotationsByTargetSourceAndCreator(@RequestBody AnnotationRequest annotationRequest, @PathVariable String creatorNickname) {
        return annotationService.getAllAnnotationsByTargetSourceAndCreator(annotationRequest.getTargetSource(), creatorNickname, annotationRequest.getPageNumber(), annotationRequest.getPageSize());
    }

    @RequestMapping(value = "/source/byCreator/{creatorNickname}/count", method = RequestMethod.POST)
    public long countAllAnnotationsByTargetSourceAndCreator(@RequestBody AnnotationRequest annotationRequest, @PathVariable String creatorNickname) {
        return annotationService.countAllAnnotationsByTargetSourceAndCreator(annotationRequest.getTargetSource(), creatorNickname);
    }

    @RequestMapping(value = "/source/byCreator/public/{creatorNickname}", method = RequestMethod.POST)
    public Iterable<Annotation> getAllPublicAnnotationsByTargetSourceAndCreator(@RequestBody AnnotationRequest annotationRequest, @PathVariable String creatorNickname) {
        return annotationService.getAllPublicAnnotationsByTargetSourceAndCreator(annotationRequest.getTargetSource(), creatorNickname, annotationRequest.getPageNumber(), annotationRequest.getPageSize());
    }

    @RequestMapping(value = "/source/byCreator/public/{creatorNickname}/count", method = RequestMethod.POST)
    public long countAllPublicAnnotationsByTargetSourceAndCreator(@RequestBody AnnotationRequest annotationRequest, @PathVariable String creatorNickname) {
        return annotationService.countAllPublicAnnotationsByTargetSourceAndCreator(annotationRequest.getTargetSource(), creatorNickname);
    }

    @RequestMapping(value = "/source/byCreator/private/{creatorNickname}", method = RequestMethod.POST)
    public Iterable<Annotation> getAllPrivateAnnotationsByTargetSourceAndCreator(@RequestBody AnnotationRequest annotationRequest, @PathVariable String creatorNickname) {
        return annotationService.getAllPrivateAnnotationsByTargetSourceAndCreator(annotationRequest.getTargetSource(), creatorNickname, annotationRequest.getPageNumber(), annotationRequest.getPageSize());
    }

    @RequestMapping(value = "/source/byCreator/private/{creatorNickname}/count", method = RequestMethod.POST)
    public long countAllPrivateAnnotationsByTargetSourceAndCreator(@RequestBody AnnotationRequest annotationRequest, @PathVariable String creatorNickname) {
        return annotationService.countAllPrivateAnnotationsByTargetSourceAndCreator(annotationRequest.getTargetSource(), creatorNickname);
    }

    @RequestMapping(value = "/basicSearch", method = RequestMethod.POST)
    public Iterable<Annotation> basicSearch(@RequestBody AnnotationRequest annotationRequest) {
        return annotationService.basicSearch(annotationRequest.getStartDate(), annotationRequest.getEndDate(), annotationRequest.getBodyValue(), annotationRequest.getPageNumber(), annotationRequest.getPageSize());
    }

    @RequestMapping(value = "/basicSearch/count", method = RequestMethod.POST)
    public long countBasicSearch(@RequestBody AnnotationRequest annotationRequest) {
        return annotationService.countBasicSearch(annotationRequest.getStartDate(), annotationRequest.getEndDate(), annotationRequest.getBodyValue());
    }

    @RequestMapping(value = "/advancedSearch", method = RequestMethod.POST)
    public Iterable<Annotation> advancedSearch(@RequestBody AnnotationRequest annotationRequest) {
        return annotationService.advancedSearch(annotationRequest.getSelectorType(), annotationRequest.getMotivation(),
                annotationRequest.getBodyValue(), annotationRequest.getNickname(), annotationRequest.getStartDate(), annotationRequest.getEndDate(), annotationRequest.isPrivate(), annotationRequest.getPageNumber(), annotationRequest.getPageSize());
    }

    @RequestMapping(value = "/advancedSearch/count", method = RequestMethod.POST)
    public long countAdvancedSearch(@RequestBody AnnotationRequest annotationRequest) {
        return annotationService.countAdvancedSearch(annotationRequest.getSelectorType(), annotationRequest.getMotivation(),
                annotationRequest.getBodyValue(), annotationRequest.getNickname(), annotationRequest.getStartDate(), annotationRequest.getEndDate(), annotationRequest.isPrivate());
    }
}
