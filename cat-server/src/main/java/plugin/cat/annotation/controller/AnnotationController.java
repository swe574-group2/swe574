package plugin.cat.annotation.controller;

import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import plugin.cat.annotation.model.Annotation;
import plugin.cat.annotation.request.AnnotationIdRequest;
import plugin.cat.annotation.request.AnnotationTargetRequestBody;
import plugin.cat.annotation.request.AnnotationsByTextSelectorRequest;
import plugin.cat.annotation.service.IAnnotationService;

import java.util.List;

/**
 * Created by okanm on 17.10.2016.
 * Controller class that handles request and response methods of the annotation operations.
 *
 * Note: When making changes to paths of the methods of this class, make sure their counterparts in the authentication server work too
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

    // *** Please do not delete this for some time. ***

//    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
//    public ResponseEntity<Iterable<Annotation>> getAnnotations(RequestEntity<String> requestEntity) {
//        System.out.println("request body : " + requestEntity.getBody());
//        HttpHeaders headers = requestEntity.getHeaders();
//        System.out.println("request headers : " + headers);
//        HttpMethod method = requestEntity.getMethod();
//        System.out.println("request method : " + method);
//        System.out.println("request url: " + requestEntity.getUrl());
//
//        ResponseEntity<Iterable<Annotation>> responseEntity = new ResponseEntity<Iterable<Annotation>>(annotationService.getAnnotations(),
//                HttpStatus.OK);
//
//        return responseEntity;
//    }

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
