package io.spring.initializr.generator.spring.container;

import io.spring.initializr.generator.project.contributor.ProjectContributor;

/**
 * {@link ProjectContributor} for the project's container file.
 *
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public abstract class ContainerProjectContributor implements ProjectContributor {

    protected final ContainerDocument containerDocument;

    public ContainerProjectContributor(ContainerDocument containerDocument) {
        this.containerDocument = containerDocument;
    }
}
