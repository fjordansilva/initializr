package io.spring.initializr.generator.spring.configuration;

import org.springframework.core.Ordered;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
@FunctionalInterface
public interface ApplicationPropertiesDocumentCustomizer extends Ordered {

    void customize(ApplicationPropertiesDocument document);

    @Override
    default int getOrder() {
        return 0;
    }
}
