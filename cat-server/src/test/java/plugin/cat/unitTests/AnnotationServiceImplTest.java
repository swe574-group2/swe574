package plugin.cat.unitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import plugin.cat.annotation.model.Annotation;
import plugin.cat.annotation.repository.AnnotationRepository;
import plugin.cat.annotation.service.impl.AnnotationServiceImpl;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Okan Men on 12.11.2016.
 * This class has the unit tests for the annotation service operations.
 */
public class AnnotationServiceImplTest {

    @Mock
    AnnotationRepository annotationRepository;

    @Mock
    private ArrayList<Annotation> annotationList;

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
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies invoke findAllById method of the annotation repository
     * @see AnnotationServiceImpl#getAnnotationsById(String)
     */
    @Test
    public void getAnnotationsById_shouldInvokeFindAllByIdMethodOfTheAnnotationRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return what the annotation repository returns
     * @see AnnotationServiceImpl#getAnnotationsById(String)
     */
    @Test
    public void getAnnotationsById_shouldReturnWhatTheAnnotationRepositoryReturns() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies invoke save method of the annotation repository
     * @see AnnotationServiceImpl#saveAnnotation(plugin.cat.annotation.model.Annotation)
     */
    @Test
    public void saveAnnotation_shouldInvokeSaveMethodOfTheAnnotationRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies invoke countById method of the annotation repository
     * @see AnnotationServiceImpl#countAnnotationsById(String)
     */
    @Test
    public void countAnnotationsById_shouldInvokeCountByIdMethodOfTheAnnotationRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return what the annotation repository returns
     * @see AnnotationServiceImpl#countAnnotationsById(String)
     */
    @Test
    public void countAnnotationsById_shouldReturnWhatTheAnnotationRepositoryReturns() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies invoke countByTarget method of the annotation repository
     * @see AnnotationServiceImpl#annotationCountForTarget(String)
     */
    @Test
    public void annotationCountForTarget_shouldInvokeCountByTargetMethodOfTheAnnotationRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return what the annotation repository returns
     * @see AnnotationServiceImpl#annotationCountForTarget(String)
     */
    @Test
    public void annotationCountForTarget_shouldReturnWhatTheAnnotationRepositoryReturns() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies invoke findAllById method of the annotation repository
     * @see AnnotationServiceImpl#getAnnotationsByTextSelection(String, int, int)
     */
    @Test
    public void getAnnotationsByTextSelection_shouldInvokeFindAllByIdMethodOfTheAnnotationRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }
}
