package plugin.cat.annotation.service;

import plugin.cat.annotation.model.Annotation;

import java.util.Date;

/**
 * Created by okanm on 17.10.2016.
 * This class is the abstract representation of the service layer for the annotation operations.
 */
public interface IAnnotationService {

    void saveAnnotation(Annotation annotation);

    int deleteById(String id);

    Iterable<Annotation> getAllAnnotations(int pageNumber, int pageSize);

    long countAllAnnotations();

    Iterable<Annotation> getAllPublicAnnotations(int pageNumber, int pageSize);

    long countAllPublicAnnotations();

    Iterable<Annotation> getAllAnnotationsByCreator(String creatorNickname, int pageNumber, int pageSize);

    long countAllAnnotationsByCreator(String creatorNickname);

    Iterable<Annotation> getAllAnnotationsByTargetSource(String targetSource, int pageNumber, int pageSize);

    long countAllAnnotationsByTargetSource(String targetSource);

    Iterable<Annotation> getAllPublicAnnotationsByTargetSource(String targetSource, int pageNumber, int pageSize);

    long countAllPublicAnnotationsByTargetSource(String targetSource);

    Iterable<Annotation> getAllAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname, int pageNumber, int pageSize);

    long countAllAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname);

    Iterable<Annotation> getAllPublicAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname, int pageNumber, int pageSize);

    long countAllPublicAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname);

    Iterable<Annotation> getAllPrivateAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname, int pageNumber, int pageSize);

    long countAllPrivateAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname);

    Iterable<Annotation> basicSearch(Date startDate, Date endDate, String bodyValue, int pageNumber, int pageSize);

    long countBasicSearch(Date startDate, Date endDate, String bodyValue);

    Iterable<Annotation> advancedSearch(String selectorType, String motivation, String bodyValue, String nickname, Date startDate, Date endDate, boolean isPrivate, int pageNumber, int pageSize);

    long countAdvancedSearch(String selectorType, String motivation, String bodyValue, String nickname, Date startDate, Date endDate, boolean isPrivate);
}
