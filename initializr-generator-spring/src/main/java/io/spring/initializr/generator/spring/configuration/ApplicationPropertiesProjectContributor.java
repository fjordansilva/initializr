package io.spring.initializr.generator.spring.configuration;

import io.spring.initializr.generator.project.contributor.ProjectContributor;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public abstract class ApplicationPropertiesProjectContributor implements ProjectContributor {

    protected final ApplicationPropertiesDocument containerDocument;

    public ApplicationPropertiesProjectContributor(ApplicationPropertiesDocument containerDocument) {
        this.containerDocument = containerDocument;
    }
}
