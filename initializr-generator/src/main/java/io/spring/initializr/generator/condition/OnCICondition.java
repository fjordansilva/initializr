package io.spring.initializr.generator.condition;

import io.spring.initializr.generator.ci.ContinuousIntegration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class OnCICondition extends ProjectGenerationCondition {

    @Override
    protected boolean matches(ResolvedProjectDescription projectDescription, ConditionContext context, AnnotatedTypeMetadata metadata) {

        if (projectDescription.getCi() == null) {
            return false;
        }

        String                ciId = (String) metadata.getAllAnnotationAttributes(ConditionalOnCI.class.getName()).getFirst("value");
        ContinuousIntegration ci   = ContinuousIntegration.forId(ciId);
        return projectDescription.getCi().id().equals(ci.id());
    }
}
