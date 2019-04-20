package io.spring.initializr.generator.spring.container;

import org.springframework.core.Ordered;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
@FunctionalInterface
public interface ContainerDocumentCustomizer extends Ordered {

    void customize(ContainerDocument document);

    @Override
    default int getOrder() {
        return 0;
    }

}
