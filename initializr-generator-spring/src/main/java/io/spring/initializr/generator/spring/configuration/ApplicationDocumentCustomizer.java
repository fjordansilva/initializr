package io.spring.initializr.generator.spring.configuration;

import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.core.Ordered;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class ApplicationDocumentCustomizer implements ApplicationPropertiesDocumentCustomizer {

    private final ResolvedProjectDescription projectDescription;

    private final InitializrMetadata metadata;

    public ApplicationDocumentCustomizer(ResolvedProjectDescription projectDescription, InitializrMetadata metadata) {
        this.projectDescription = projectDescription;
        this.metadata = metadata;
    }

    @Override
    public void customize(ApplicationPropertiesDocument document) {
        Map<String, Object> model = new HashMap<>();
        model.put("description", projectDescription.getDescription());
        model.put("artifactId", projectDescription.getArtifactId());
        model.put("irn", projectDescription.getIrn());
        model.put("sia", projectDescription.getSia());
        model.put("tab", "  ");
        document.addSection("configuration/application", model);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
