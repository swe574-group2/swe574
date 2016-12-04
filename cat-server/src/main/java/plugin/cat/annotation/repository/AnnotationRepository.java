package plugin.cat.annotation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import plugin.cat.annotation.model.Annotation;

/**
 * Created by okanm on 17.10.2016.
 * This class indicates that the annotation is a DAO component that uses Spring Data JPA in the persistence layer.
 */
@Repository
public interface AnnotationRepository extends PagingAndSortingRepository<Annotation, Long> {

    long countById(String id);

    @Query("select count(source) from Annotation a where a.target.source = :source")
    long countByTarget(@Param("source") String source);

    Iterable<Annotation> findAllById(String id);
}
