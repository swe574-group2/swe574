package plugin.cat.annotation.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import plugin.cat.annotation.model.Annotation;

/**
 * Created by okanm on 17.10.2016.
 * This class indicates that the annotation is a DAO component that uses Spring Data JPA in the persistence layer.
 */
@Repository
public interface AnnotationRepository extends PagingAndSortingRepository<Annotation, Long> {

    long countById(String id);
    long countByTarget(String target);
    Iterable<Annotation> findAllById(String id);
}
