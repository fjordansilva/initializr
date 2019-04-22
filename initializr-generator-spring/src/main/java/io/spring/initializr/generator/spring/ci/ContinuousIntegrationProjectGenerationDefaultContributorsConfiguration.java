package io.spring.initializr.generator.spring.ci;

import io.spring.initializr.generator.ci.gitlab.GitlabContinuousIntegration;
import io.spring.initializr.generator.ci.jenkins.JenkinsContinuousIntegration;
import io.spring.initializr.generator.condition.ConditionalOnCI;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
@Configuration
public class ContinuousIntegrationProjectGenerationDefaultContributorsConfiguration {

    @Bean
    @ConditionalOnCI(JenkinsContinuousIntegration.ID)
    public ContinuousIntegrationDocumentCustomizer jenkinsCustomizer(ResolvedProjectDescription description, InitializrMetadata metadata) {
        return new JenkinsDocumentCustomizer(description, metadata);
    }

    @Bean
    @ConditionalOnCI(GitlabContinuousIntegration.ID)
    public ContinuousIntegrationDocumentCustomizer gitlabCustomizer(ResolvedProjectDescription description, InitializrMetadata metadata) {
        return new GitlabDocumentCustomizer(description, metadata);
    }
}
