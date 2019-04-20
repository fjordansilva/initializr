package io.spring.initializr.generator.ci;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public interface ContinuousIntegrationFactory {

    /**
     * Creates and returns a {@link ContinuousIntegration} for the given id. If the factory does not
     * recognise the given {@code id}, {@code null} should be returned.
     *
     * @param id
     *         the id of the ContinuousIntegration
     *
     * @return the ContinuousIntegration or {@code null}
     */
    ContinuousIntegration createContinuousIntegration(String id);
}
