package io.spring.initializr.generator.spring.container;

import io.spring.initializr.generator.condition.ConditionalOnContainer;
import io.spring.initializr.generator.container.docker.DockerContainer;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * Configuration for contributions specific to the Containers (Docker, Docker Compose, Kubernetes...) of a project.
 *
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
@ProjectGenerationConfiguration
@Import(ContainerProjectGenerationDefaultContributorsConfiguration.class)
public class ContainerProjectGenerationConfiguration {

    @Bean
    public ContainerDocument containerDocument(MustacheTemplateRenderer templateRenderer, ObjectProvider<ContainerDocumentCustomizer> containerDocumentCustomizers) {
        ContainerDocument containerDocument = new ContainerDocument(templateRenderer);
        containerDocumentCustomizers.orderedStream().forEach((customizer) -> customizer.customize(containerDocument));
        return containerDocument;
    }

    @Bean
    @ConditionalOnContainer(DockerContainer.ID)
    public ContainerProjectContributor dockerContributor(ContainerDocument containerDocument) {
        return new DockerProjectContributor(containerDocument);
    }
}
