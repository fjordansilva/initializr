package io.spring.initializr.generator.spring.ci;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class JenkinsProjectContributor extends ContinuousIntegrationProjectContributor {

    public JenkinsProjectContributor(ContinuousIntegrationDocument continuousIntegrationDocument) {
        super(continuousIntegrationDocument);
    }

    /**
     * Contribute additional resources to the project in the specified root directory.
     *
     * @param projectRoot
     *         the root directory of the project
     *
     * @throws IOException
     *         if contributing a resource failed
     */
    @Override
    public void contribute(Path projectRoot) throws IOException {
        Path file = Files.createFile(projectRoot.resolve("Jenkinsfile"));
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file))) {
            containerDocument.write(writer);
        }
    }
}
