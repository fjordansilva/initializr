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

package io.spring.initializr.site.extension;

import io.spring.initializr.web.project.ProjectRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MavenBuildSystemHelpDocumentCustomizer}.
 *
 * @author Jenn Strater
 * @author Andy Wilkinson
 */
class MavenBuildSystemHelpDocumentCustomizerTests extends AbstractExtensionTests {

    @Test
    void linksAddedToHelpDocumentForMavenBuild() {
        assertThat(helpMdLinesForProject("maven-build")).contains(
                "* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)");
    }

    @Test
    void linksNotAddedToHelpDocumentForGradleBuild() {
        assertThat(helpMdLinesForProject("gradle-build")).doesNotContain(
                "* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)");
    }

    private List<String> helpMdLinesForProject(String type) {
        ProjectRequest request = createProjectRequest("web");
        request.setType(type);
        return generateProject(request).readAllLines("HELP.md");
    }

}
