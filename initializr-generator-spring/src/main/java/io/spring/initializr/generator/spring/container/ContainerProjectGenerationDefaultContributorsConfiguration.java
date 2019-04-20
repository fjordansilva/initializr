package io.spring.initializr.generator.spring.container;

import io.spring.initializr.generator.condition.ConditionalOnContainer;
import io.spring.initializr.generator.container.docker.DockerContainer;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
@Configuration
public class ContainerProjectGenerationDefaultContributorsConfiguration {

    @Bean
    @ConditionalOnContainer(DockerContainer.ID)
    public ContainerDocumentCustomizer dockerCustomizer(ResolvedProjectDescription description, InitializrMetadata metadata) {
        return new DockerDocumentCustomizer(description, metadata);
    }

}
