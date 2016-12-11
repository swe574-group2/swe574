package plugin.cat.annotation.util;

import plugin.cat.annotation.model.*;

/**
 * Created by Arda on 12/11/2016.
 */
public class Util {
    public static void assignIdsToAnnotation(Annotation annotation) {

        String annotationIdUrlPrefix = "http://example.org/anno";
        annotation.setId(annotationIdUrlPrefix + annotation.getEntityId());

        AnnotationGenerator generator = annotation.getGenerator();
        if (generator != null) {
            String generatorIdUrlPrefix = "http://example.org/client";
            generator.setId(generatorIdUrlPrefix + generator.getEntityId());
        }

        AnnotationBody body = annotation.getBody();
        if (body != null) {
            String bodyIdUrlPrefix = "http://example.org/body";
            body.setId(bodyIdUrlPrefix + generator.getEntityId());
        }

        AnnotationTarget target = annotation.getTarget();
        if (target != null) {
            String targetIdUrlPrefix = "http://example.org/target";
            target.setId(targetIdUrlPrefix + target.getEntityId());

            Selector selector = target.getSelector();
            if (selector != null) {
                String selectorIdUrlPrefix = "http://example.org/selector";
                selector.setId(selectorIdUrlPrefix + selector.getEntityId());
            }
        }
    }
}
