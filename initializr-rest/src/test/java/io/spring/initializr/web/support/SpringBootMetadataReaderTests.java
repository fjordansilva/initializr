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

package io.spring.initializr.web.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.initializr.metadata.DefaultMetadataElement;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author Stephane Nicoll
 * @author Dave Syer
 */
class SpringBootMetadataReaderTests {

    private final InitializrMetadata metadata = InitializrMetadataBuilder.create()
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RestTemplate restTemplate = new RestTemplate();

    private final MockRestServiceServer server = MockRestServiceServer
            .bindTo(restTemplate).build();

    @Test
    void readAvailableVersions() throws IOException {
        server.expect(requestTo("https://spring.io/project_metadata/spring-boot"))
                .andRespond(withSuccess(
                        new ClassPathResource("metadata/sagan/spring-boot.json"),
                        MediaType.APPLICATION_JSON));
        List<String> versionTags = new ArrayList<>();
        versionTags.add("GENERAL_AVAILABILITY");
        List<DefaultMetadataElement> versions = new SpringBootMetadataReader(
                objectMapper,
                restTemplate,
                metadata.getConfiguration().getEnv().getSpringBootMetadataUrl()).getBootVersions(versionTags);
        assertThat(versions).as("spring boot versions should not be null").isNotNull();
        AtomicBoolean defaultFound = new AtomicBoolean(false);
        versions.forEach((it) -> {
            assertThat(it.getId()).as("Id must be set").isNotNull();
            assertThat(it.getName()).as("Name must be set").isNotNull();
            if (it.isDefault()) {
                if (defaultFound.get()) {
                    fail("One default version was already found " + it.getId());
                }
                defaultFound.set(true);
            }
        });
        server.verify();
    }

}
