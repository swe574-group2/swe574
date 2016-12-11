package plugin.cat.annotation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import plugin.cat.annotation.model.Annotation;

import java.util.Date;
import java.util.List;

/**
 * Created by okanm on 17.10.2016.
 * This class indicates that the annotation is a DAO component that uses Spring Data JPA in the persistence layer.
 */
@Repository
public interface AnnotationRepository extends PagingAndSortingRepository<Annotation, Long> {

    Page<Annotation> findAllByIsPrivateIsFalse(Pageable pageable);

    long countByIsPrivateIsFalse();

    Page<Annotation> findAllByCreatorNickname(String nickname, Pageable pageable);

    long countByCreatorNickname(String nickname);

    Page<Annotation> findAllByTargetSource(String targetSource, Pageable pageable);

    long countByTargetSource(String targetSource);

    Page<Annotation> findAllByTargetSourceAndIsPrivateIsFalse(String targetSource, Pageable pageable);

    long countByTargetSourceAndIsPrivateIsFalse(String targetSource);

    Page<Annotation> findAllByTargetSourceAndCreatorNickname(String targetSource, String creatorNickname, Pageable pageable);

    long countByTargetSourceAndCreatorNickname(String targetSource, String creatorNickname);

    Page<Annotation> findAllByTargetSourceAndCreatorNicknameAndIsPrivateIsFalse(String targetSource, String creatorNickname, Pageable pageable);

    long countByTargetSourceAndCreatorNicknameAndIsPrivateIsFalse(String targetSource, String creatorNickname);

    Page<Annotation> findAllByTargetSourceAndCreatorNicknameAndIsPrivateIsTrue(String targetSource, String creatorNickname, Pageable pageable);

    long countByTargetSourceAndCreatorNicknameAndIsPrivateIsTrue(String targetSource, String creatorNickname);

    int deleteById(String id);

    @Query("select a from Annotation a where (a.created between :startDate and :endDate)" +
            "and (lower(a.body.value) like %:bodyValue% or :bodyValue is null)")
    List<Annotation> basicSearch(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("bodyValue") String bodyValue, Pageable pageable);

    @Query("select count(a) from Annotation a where (a.created between :startDate and :endDate)" +
            "and (lower(a.body.value) like %:bodyValue% or :bodyValue is null)")
    long countBasicSearch(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("bodyValue") String bodyValue);

    @Query("select a from Annotation a where (a.target.selector.type=:selectorType or :selectorType is null)" +
            "and (a.motivation=:motivation or :motivation is null)" +
            "and (lower(a.body.value) like %:bodyValue% or :bodyValue is null)" +
            "and (a.created between :startDate and :endDate)" +
            "and (a.creator.nickname=:nickname or :nickname is null)" +
            "and (a.isPrivate=:isPrivate)")
    List<Annotation> advancedSearch(@Param("selectorType") String selectorType, @Param("motivation") String motivation, @Param("bodyValue") String bodyValue,
                                    @Param("nickname") String nickname, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("isPrivate") boolean isPrivate, Pageable pageable);

    @Query("select count(a) from Annotation a where (a.target.selector.type=:selectorType or :selectorType is null)" +
            "and (a.motivation=:motivation or :motivation is null)" +
            "and (lower(a.body.value) like %:bodyValue% or :bodyValue is null)" +
            "and (a.created between :startDate and :endDate)" +
            "and (a.creator.nickname=:nickname or :nickname is null)" +
            "and (a.isPrivate=:isPrivate)")
    long countAdvancedSearch(@Param("selectorType") String selectorType, @Param("motivation") String motivation, @Param("bodyValue") String bodyValue,
                             @Param("nickname") String nickname, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("isPrivate") boolean isPrivate);
}
