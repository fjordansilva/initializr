package io.spring.initializr.generator.condition;

import io.spring.initializr.generator.container.Container;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class OnContainerCondition extends ProjectGenerationCondition {
    @Override
    protected boolean matches(ResolvedProjectDescription projectDescription, ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (projectDescription.getContainer() == null) {
            return false;
        }
        String    containerId = (String) metadata.getAllAnnotationAttributes(ConditionalOnContainer.class.getName()).getFirst("value");
        Container container   = Container.forId(containerId);
        return projectDescription.getContainer().id().equals(container.id());
    }
}
