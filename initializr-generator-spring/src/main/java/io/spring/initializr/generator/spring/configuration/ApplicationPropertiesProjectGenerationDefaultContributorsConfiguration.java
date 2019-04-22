package io.spring.initializr.generator.spring.configuration;

import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
@Configuration
public class ApplicationPropertiesProjectGenerationDefaultContributorsConfiguration {

    @Bean
    public ApplicationPropertiesDocumentCustomizer applicationPropertiesDocumentCustomizer(ResolvedProjectDescription description, InitializrMetadata metadata) {
        return new ApplicationDocumentCustomizer(description, metadata);
    }

}
