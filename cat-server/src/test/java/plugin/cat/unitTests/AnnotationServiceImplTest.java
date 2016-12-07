package plugin.cat.unitTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import plugin.cat.annotation.model.Annotation;
import plugin.cat.annotation.repository.AnnotationRepository;
import plugin.cat.annotation.service.impl.AnnotationServiceImpl;

import static org.mockito.Mockito.*;

/**
 * Created by Okan Menevseoglu on 12.11.2016.
 * This class has the unit tests for the annotation service operations.
 */

public class AnnotationServiceImplTest {

    @Mock
    AnnotationRepository annotationRepository;

    @Mock
    private Annotation annotation;

    @InjectMocks
    AnnotationServiceImpl annotationService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * @verifies invoke save method of the annotation repository
     * @see AnnotationServiceImpl#saveAnnotation(plugin.cat.annotation.model.Annotation)
     */
    @Test
    public void saveAnnotation_shouldInvokeSaveMethodOfTheAnnotationRepository() throws Exception {
        annotationService.saveAnnotation(annotation);
        verify(annotationRepository).save(annotation);
    }
}