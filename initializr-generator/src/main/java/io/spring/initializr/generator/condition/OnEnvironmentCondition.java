package io.spring.initializr.generator.condition;

import io.spring.initializr.generator.environment.Environment;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class OnEnvironmentCondition extends ProjectGenerationCondition {

    @Override
    protected boolean matches(ResolvedProjectDescription projectDescription, ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (projectDescription.getEnvironment() == null) {
            return false;
        }
        String      environmentId = (String) metadata.getAllAnnotationAttributes(ConditionalOnEnvironment.class.getName()).getFirst("value");
        Environment language      = Environment.forId(environmentId);
        return projectDescription.getEnvironment().id().equals(language.id());
    }
}
