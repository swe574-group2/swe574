package plugin.cat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import plugin.cat.model.Annotation;

import java.util.List;

/**
 * Created by okanm on 17.10.2016.
 * This class indicates that the annotation is a DAO component that uses Spring Data JPA in the persistence layer.
 */
@Repository
public interface AnnotationRepository extends PagingAndSortingRepository<Annotation, Long> {
    Iterable<Annotation> findAllByContext(String context);
}
