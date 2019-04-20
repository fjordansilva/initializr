package io.spring.initializr.generator.spring.ci;

import io.spring.initializr.generator.project.contributor.SingleResourceProjectContributor;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class JenkinsContributor extends SingleResourceProjectContributor {

    public JenkinsContributor() {
        this("classpath:jenkins/Jenkinsfile");
    }

    public JenkinsContributor(String resourcePattern) {
        super("Jenkinsfile", resourcePattern);
    }
}
