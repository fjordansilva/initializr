package io.spring.initializr.generator.spring.ci;

import io.spring.initializr.generator.ci.gitlab.GitlabContinuousIntegration;
import io.spring.initializr.generator.ci.jenkins.JenkinsContinuousIntegration;
import io.spring.initializr.generator.condition.ConditionalOnCI;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
@ProjectGenerationConfiguration
@Import(ContinuousIntegrationProjectGenerationDefaultContributorsConfiguration.class)
public class ContinuousIntegrationProjectGenerationConfiguration {

    @Bean
    public ContinuousIntegrationDocument continuousIntegrationDocument(MustacheTemplateRenderer templateRenderer, ObjectProvider<ContinuousIntegrationDocumentCustomizer> containerDocumentCustomizers) {
        ContinuousIntegrationDocument continuousIntegrationDocument = new ContinuousIntegrationDocument(templateRenderer);
        containerDocumentCustomizers.orderedStream().forEach((customizer) -> customizer.customize(continuousIntegrationDocument));
        return continuousIntegrationDocument;
    }

    @Bean
    @ConditionalOnCI(JenkinsContinuousIntegration.ID)
    public ContinuousIntegrationProjectContributor jenkinsContributor(ContinuousIntegrationDocument continuousIntegrationDocument) {
        return new JenkinsProjectContributor(continuousIntegrationDocument);
    }

    @Bean
    @ConditionalOnCI(GitlabContinuousIntegration.ID)
    public ContinuousIntegrationProjectContributor gitlabContributor(ContinuousIntegrationDocument continuousIntegrationDocument) {
        return new GitlabProjectContributor(continuousIntegrationDocument);
    }
}
