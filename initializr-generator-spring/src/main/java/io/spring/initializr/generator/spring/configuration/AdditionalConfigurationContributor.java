package io.spring.initializr.generator.spring.configuration;

import io.spring.initializr.generator.project.contributor.MultipleResourcesProjectContributor;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class AdditionalConfigurationContributor extends MultipleResourcesProjectContributor {

    public AdditionalConfigurationContributor(String rootResource) {
        super(rootResource);
    }
}
