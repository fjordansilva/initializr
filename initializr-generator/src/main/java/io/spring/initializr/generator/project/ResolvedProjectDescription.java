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

package io.spring.initializr.generator.project;

import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.ci.ContinuousIntegration;
import io.spring.initializr.generator.container.Container;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.version.Version;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An immutable description of a project that is being generated.
 *
 * @author Madhura Bhave
 */
public final class ResolvedProjectDescription {

    private final Map<String, Dependency> requestedDependencies;

    private final Version platformVersion;

    private final BuildSystem buildSystem;

    private final Packaging packaging;

    private final Language language;

    private final ContinuousIntegration ci;

    private final Container container;

    private final String irn;

    private final String sia;

    private final String groupId;

    private final String artifactId;

    private final String version;

    private final String name;

    private final String description;

    private final String applicationName;

    private final String packageName;

    private final String baseDirectory;

    public ResolvedProjectDescription(ProjectDescription description) {
        platformVersion = description.getPlatformVersion();
        buildSystem = description.getBuildSystem();
        packaging = description.getPackaging();
        language = description.getLanguage();
        ci = description.getCi();
        container = description.getContainer();
        irn = description.getIrn();
        sia = description.getSia();
        groupId = description.getGroupId();
        artifactId = description.getArtifactId();
        version = description.getVersion();
        name = description.getName();
        this.description = description.getDescription();
        applicationName = description.getApplicationName();
        packageName = getPackageName(description);
        baseDirectory = description.getBaseDirectory();
        Map<String, Dependency> requestedDependencies = new LinkedHashMap<>(description.getRequestedDependencies());
        this.requestedDependencies = Collections.unmodifiableMap(requestedDependencies);
    }

    private String getPackageName(ProjectDescription description) {
        if (StringUtils.hasText(description.getPackageName())) {
            return description.getPackageName();
        }
        if (StringUtils.hasText(description.getGroupId()) && StringUtils.hasText(description.getArtifactId())) {
            return description.getGroupId() + "." + description.getArtifactId();
        }
        return null;
    }

    public Map<String, Dependency> getRequestedDependencies() {
        return requestedDependencies;
    }

    public Version getPlatformVersion() {
        return platformVersion;
    }

    public BuildSystem getBuildSystem() {
        return buildSystem;
    }

    public Packaging getPackaging() {
        return packaging;
    }

    public Language getLanguage() {
        return language;
    }

    public ContinuousIntegration getCi() {
        return ci;
    }

    public Container getContainer() {
        return container;
    }

    public String getIrn() {
        return irn;
    }

    public String getSia() {
        return sia;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

}
