package io.spring.initializr.generator.container.kubernetes;

import io.spring.initializr.generator.container.Container;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class KubernetesContainer implements Container {
    /**
     * Kubernetes {@link Container} identifier.
     */
    public static final String ID = "kubernetes";

    @Override
    public String id() {
        return KubernetesContainer.ID;
    }

    @Override
    public String toString() {
        return id();
    }
}
