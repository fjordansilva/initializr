package io.spring.initializr.generator.spring.ci;

import io.spring.initializr.generator.ci.gitlab.GitlabContinuousIntegration;
import io.spring.initializr.generator.ci.jenkins.JenkinsContinuousIntegration;
import io.spring.initializr.generator.condition.ConditionalOnCI;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
@ProjectGenerationConfiguration
public class ContinuousIntegrationProjectGenerationConfiguration {

    @Bean
    @ConditionalOnCI(JenkinsContinuousIntegration.ID)
    public JenkinsContributor jenkinsContributor() {
        return new JenkinsContributor();
    }

    @Bean
    @ConditionalOnCI(GitlabContinuousIntegration.ID)
    public GitlabContributor gitlabContributor() {
        return new GitlabContributor();
    }
}
