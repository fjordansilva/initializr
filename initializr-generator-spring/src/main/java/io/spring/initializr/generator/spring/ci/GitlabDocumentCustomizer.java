package io.spring.initializr.generator.spring.ci;

import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.core.Ordered;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class GitlabDocumentCustomizer implements ContinuousIntegrationDocumentCustomizer {

    private final ResolvedProjectDescription projectDescription;

    private final InitializrMetadata metadata;

    public GitlabDocumentCustomizer(ResolvedProjectDescription projectDescription, InitializrMetadata metadata) {
        this.projectDescription = projectDescription;
        this.metadata = metadata;
    }

    @Override
    public void customize(ContinuousIntegrationDocument document) {
        Map<String, Object> model = new HashMap<>();
        model.put("irn", projectDescription.getIrn());
        model.put("sia", projectDescription.getSia());
        // model.put("version", projectDescription.getVersion());
        // model.put("description", projectDescription.getDescription());
        // model.put("javaVersion", projectDescription.getJavaVersion());
        model.put("artifactId", projectDescription.getArtifactId());
        model.put("tab", "  ");
        model.put("tag", "<<:");
        document.addSection("ci/gitlab-ci", model);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
