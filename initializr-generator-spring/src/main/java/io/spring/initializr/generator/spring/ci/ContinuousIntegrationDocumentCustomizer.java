package io.spring.initializr.generator.spring.ci;

import org.springframework.core.Ordered;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
@FunctionalInterface
public interface ContinuousIntegrationDocumentCustomizer extends Ordered {

    void customize(ContinuousIntegrationDocument document);

    @Override
    default int getOrder() {
        return 0;
    }
}
