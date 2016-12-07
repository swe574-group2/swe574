package plugin.cat.annotation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import plugin.cat.annotation.model.Annotation;
import plugin.cat.annotation.repository.AnnotationRepository;
import plugin.cat.annotation.service.IAnnotationService;

import java.util.Date;

/**
 * Created by okanm on 17.10.2016.
 * This class is the business layer of the annotation operations.
 */
@Service
public class AnnotationServiceImpl implements IAnnotationService {

    private AnnotationRepository annotationRepository;

    @Autowired
    public AnnotationServiceImpl(AnnotationRepository annotationRepository) {
        this.annotationRepository = annotationRepository;
    }

    /**
     * @param annotation to be saved
     * @should invoke save method of the annotation repository
     */
    @Override
    public void saveAnnotation(Annotation annotation) {
        annotationRepository.save(annotation);
    }

    @Override
    public Iterable<Annotation> getAllAnnotations(int pageNumber, int pageSize) {
        return annotationRepository.findAll(new PageRequest(pageNumber - 1, pageSize));
    }

    @Override
    public long countAllAnnotations() {
        return annotationRepository.count();
    }

    @Override
    public Iterable<Annotation> getAllPublicAnnotations(int pageNumber, int pageSize) {
        return annotationRepository.findAllByIsPrivateIsFalse(new PageRequest(pageNumber - 1, pageSize));
    }

    @Override
    public long countAllPublicAnnotations() {
        return annotationRepository.countByIsPrivateIsFalse();
    }

    @Override
    public Iterable<Annotation> getAllAnnotationsByCreator(String creatorNickname, int pageNumber, int pageSize) {
        return annotationRepository.findAllByCreatorNickname(creatorNickname, new PageRequest(pageNumber - 1, pageSize));
    }

    @Override
    public long countAllAnnotationsByCreator(String creatorNickname) {
        return annotationRepository.countByCreatorNickname(creatorNickname);
    }

    @Override
    public Iterable<Annotation> getAllAnnotationsByTargetSource(String targetSource, int pageNumber, int pageSize) {
        return annotationRepository.findAllByTargetSource(targetSource, new PageRequest(pageNumber - 1, pageSize));
    }

    @Override
    public long countAllAnnotationsByTargetSource(String targetSource) {
        return annotationRepository.countByTargetSource(targetSource);
    }

    @Override
    public Iterable<Annotation> getAllPublicAnnotationsByTargetSource(String targetSource, int pageNumber, int pageSize) {
        return annotationRepository.findAllByTargetSourceAndIsPrivateIsFalse(targetSource, new PageRequest(pageNumber - 1, pageSize));
    }

    @Override
    public long countAllPublicAnnotationsByTargetSource(String targetSource) {
        return annotationRepository.countByTargetSourceAndIsPrivateIsFalse(targetSource);
    }

    @Override
    public Iterable<Annotation> getAllAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname, int pageNumber, int pageSize) {
        return annotationRepository.findAllByTargetSourceAndCreatorNickname(targetSource, creatorNickname, new PageRequest(pageNumber - 1, pageSize));
    }

    @Override
    public long countAllAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname) {
        return annotationRepository.countByTargetSourceAndCreatorNickname(targetSource, creatorNickname);
    }

    @Override
    public Iterable<Annotation> getAllPublicAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname, int pageNumber, int pageSize) {
        return annotationRepository.findAllByTargetSourceAndCreatorNicknameAndIsPrivateIsFalse(targetSource, creatorNickname, new PageRequest(pageNumber - 1, pageSize));
    }

    @Override
    public long countAllPublicAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname) {
        return annotationRepository.countByTargetSourceAndCreatorNicknameAndIsPrivateIsFalse(targetSource, creatorNickname);
    }

    @Override
    public Iterable<Annotation> getAllPrivateAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname, int pageNumber, int pageSize) {
        return annotationRepository.findAllByTargetSourceAndCreatorNicknameAndIsPrivateIsTrue(targetSource, creatorNickname, new PageRequest(pageNumber - 1, pageSize));
    }

    @Override
    public long countAllPrivateAnnotationsByTargetSourceAndCreator(String targetSource, String creatorNickname) {
        return annotationRepository.countByTargetSourceAndCreatorNicknameAndIsPrivateIsTrue(targetSource, creatorNickname);
    }

    @Override
    public Iterable<Annotation> basicSearch(Date startDate, Date endDate, String bodyValue, int pageNumber, int pageSize) {
        return annotationRepository.basicSearch(startDate, endDate, bodyValue, new PageRequest(pageNumber - 1, pageSize));
    }

    @Override
    public long countBasicSearch(Date startDate, Date endDate, String bodyValue) {
        return annotationRepository.countBasicSearch(startDate, endDate, bodyValue);
    }

    @Override
    public Iterable<Annotation> advancedSearch(String selectorType, String motivation, String bodyValue, String nickname, Date startDate, Date endDate, boolean isPrivate, int pageNumber, int pageSize) {
        return annotationRepository.advancedSearch(selectorType, motivation, bodyValue, nickname, startDate, endDate, isPrivate, new PageRequest(pageNumber - 1, pageSize));
    }

    @Override
    public long countAdvancedSearch(String selectorType, String motivation, String bodyValue, String nickname, Date startDate, Date endDate, boolean isPrivate) {
        return annotationRepository.countAdvancedSearch(selectorType, motivation, bodyValue, nickname, startDate, endDate, isPrivate);
    }
}
