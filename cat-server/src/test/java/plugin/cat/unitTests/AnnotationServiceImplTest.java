package plugin.cat.unitTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import plugin.cat.annotation.model.Annotation;
import plugin.cat.annotation.repository.AnnotationRepository;
import plugin.cat.annotation.request.AnnotationsByTextSelectorRequest;
import plugin.cat.annotation.service.impl.AnnotationServiceImpl;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Okan Menevseoglu on 12.11.2016.
 * This class has the unit tests for the annotation service operations.
 */
public class AnnotationServiceImplTest {

    @Mock
    AnnotationRepository annotationRepository;

    @Spy
    private ArrayList<Annotation> annotationList;

    private long l;

    private int i;

    @Mock
    private AnnotationsByTextSelectorRequest annotationsByTextSelectorRequest;

    @Mock
    private Annotation annotation;

    @InjectMocks
    AnnotationServiceImpl annotationService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * @verifies invoke findAll method of the annotation repository
     * @see AnnotationServiceImpl#getAnnotations()
     */
    @Test
    public void getAnnotations_shouldInvokeFindAllMethodOfTheAnnotationRepository() throws Exception {
        annotationService.getAnnotations();
        verify(annotationRepository, times(1)).findAll();
    }

    /**
     * @verifies return what the annotation repository returns
     * @see AnnotationServiceImpl#getAnnotations()
     */
    @Test
    public void getAnnotations_shouldReturnWhatTheAnnotationRepositoryReturns() throws Exception {
        when(annotationRepository.findAll()).thenReturn(annotationList);
        assertEquals(annotationService.getAnnotations(), annotationList);
    }

    /**
     * @verifies invoke findAllById method of the annotation repository
     * @see AnnotationServiceImpl#getAnnotationsById(String)
     */
    @Test
    public void getAnnotationsById_shouldInvokeFindAllByIdMethodOfTheAnnotationRepository() throws Exception {
        annotationService.getAnnotationsById(anyString());
        verify(annotationRepository, times(1)).findAllById(anyString());
    }

    /**
     * @verifies return what the annotation repository returns
     * @see AnnotationServiceImpl#getAnnotationsById(String)
     */
    @Test
    public void getAnnotationsById_shouldReturnWhatTheAnnotationRepositoryReturns() throws Exception {
        when(annotationRepository.findAllById(anyString())).thenReturn(annotationList);
        assertEquals(annotationService.getAnnotationsById(anyString()), annotationList);
    }

    /**
     * @verifies invoke save method of the annotation repository
     * @see AnnotationServiceImpl#saveAnnotation(plugin.cat.annotation.model.Annotation)
     */
    @Test
    public void saveAnnotation_shouldInvokeSaveMethodOfTheAnnotationRepository() throws Exception {
        annotationService.saveAnnotation(annotation);
        verify(annotationRepository, times(1)).save(annotation);
    }

    /**
     * @verifies invoke countById method of the annotation repository
     * @see AnnotationServiceImpl#countAnnotationsById(String)
     */
    @Test
    public void countAnnotationsById_shouldInvokeCountByIdMethodOfTheAnnotationRepository() throws Exception {
        annotationService.countAnnotationsById(anyString());
        verify(annotationRepository, times(1)).countById(anyString());
    }

    /**
     * @verifies return what the annotation repository returns
     * @see AnnotationServiceImpl#countAnnotationsById(String)
     */
    @Test
    public void countAnnotationsById_shouldReturnWhatTheAnnotationRepositoryReturns() throws Exception {
        when(annotationRepository.countById(anyString())).thenReturn(l);
        assertEquals(annotationService.countAnnotationsById(anyString()), l);
    }

    /**
     * @verifies invoke countByTarget method of the annotation repository
     * @see AnnotationServiceImpl#countAnnotationByTarget(String)
     */
    @Test
    public void countAnnotationByTarget_shouldInvokeCountByTargetMethodOfTheAnnotationRepository() throws Exception {
        annotationService.countAnnotationByTarget(anyString());
        verify(annotationRepository, times(1)).countByTarget(anyString());
    }

    /**
     * @verifies return what the annotation repository returns
     * @see AnnotationServiceImpl#countAnnotationByTarget(String)
     */
    @Test
    public void annotationCountForTarget_shouldReturnWhatTheAnnotationRepositoryReturns() throws Exception {
        when(annotationRepository.countByTarget(anyString())).thenReturn(l);
        assertEquals(annotationService.countAnnotationByTarget(anyString()), l);
    }

}
