/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.generator.spring.configuration;

import io.spring.initializr.generator.project.contributor.SingleResourceProjectContributor;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A {@link SingleResourceProjectContributor} that contributes a
 * {@code application.yml} file to a project.
 *
 * @author Stephane Nicoll
 */
public class ApplicationPropertiesContributor extends ApplicationPropertiesProjectContributor {

    public ApplicationPropertiesContributor(ApplicationPropertiesDocument continuousIntegrationDocument) {
        super(continuousIntegrationDocument);
    }

    /**
     * Contribute additional resources to the project in the specified root directory.
     *
     * @param projectRoot
     *         the root directory of the project
     *
     * @throws IOException
     *         if contributing a resource failed
     */
    @Override
    public void contribute(Path projectRoot) throws IOException {
        Path file = projectRoot.resolve("src/main/resources/application.yml");
        if (!Files.exists(file)) {
            Files.createDirectories(file.getParent());
            Files.createFile(file);
        }
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file))) {
            containerDocument.write(writer);
        }
    }

}
