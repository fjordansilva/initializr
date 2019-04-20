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
 * Tests for {@link GradleBuildSystemHelpDocumentCustomizer}.
 *
 * @author Jenn Strater
 * @author Andy Wilkinson
 */
class GradleBuildSystemHelpDocumentCustomizerTests extends AbstractExtensionTests {

    @Test
    void linksAddedToHelpDocumentForGradleBuild() {
        assertThat(helpMdLinesForProject("gradle-build")).contains(
                "* [Official Gradle documentation](https://docs.gradle.org)",
                "* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)");
    }

    @Test
    void linksNotAddedToHelpDocumentForMavenBuild() {
        assertThat(helpMdLinesForProject("maven-build")).doesNotContain(
                "* [Official Gradle documentation](https://docs.gradle.org)",
                "* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)");
    }

    private List<String> helpMdLinesForProject(String type) {
        ProjectRequest request = createProjectRequest("web");
        request.setType(type);
        return generateProject(request).readAllLines("HELP.md");
    }

}
