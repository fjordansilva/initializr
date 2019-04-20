package io.spring.initializr.generator.spring.ci;

import io.spring.initializr.generator.project.contributor.SingleResourceProjectContributor;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class GitlabContributor extends SingleResourceProjectContributor {

    public GitlabContributor() {
        this("classpath:gitlab/.gitlab-ci.yml");
    }

    public GitlabContributor(String resourcePattern) {
        super(".gitlab-ci.yml", resourcePattern);
    }
}
