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
import io.spring.initializr.generator.environment.Environment;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.version.Version;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description of a project to generate.
 *
 * @author Andy Wilkinson
 */
public class ProjectDescription {

    private Version platformVersion;

    private BuildSystem buildSystem;

    private Packaging packaging;

    private Language language;

    private ContinuousIntegration ci;

    private Container container;

    private Environment environment;

    private final Map<String, Dependency> requestedDependencies = new LinkedHashMap<>();

    private String irn;

    private String sia;

    private String groupId;

    private String artifactId;

    private String version;

    private String name;

    private String description;

    private String applicationName;

    private String packageName;

    private String baseDirectory;

    /**
     * Resolve the state of this instance to a {@link ResolvedProjectDescription}.
     *
     * @return an immutable description.
     */
    public ResolvedProjectDescription resolve() {
        return new ResolvedProjectDescription(this);
    }

    public Version getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(Version platformVersion) {
        this.platformVersion = platformVersion;
    }

    public BuildSystem getBuildSystem() {
        return buildSystem;
    }

    public void setBuildSystem(BuildSystem buildSystem) {
        this.buildSystem = buildSystem;
    }

    public Packaging getPackaging() {
        return packaging;
    }

    public void setPackaging(Packaging packaging) {
        this.packaging = packaging;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public ContinuousIntegration getCi() {
        return ci;
    }

    public void setCi(ContinuousIntegration ci) {
        this.ci = ci;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Dependency addDependency(String id, Dependency dependency) {
        return requestedDependencies.put(id, dependency);
    }

    public Map<String, Dependency> getRequestedDependencies() {
        return Collections.unmodifiableMap(requestedDependencies);
    }

    public String getIrn() {
        return irn;
    }

    public void setIrn(String irn) {
        this.irn = irn;
    }

    public String getSia() {
        return sia;
    }

    public void setSia(String sia) {
        this.sia = sia;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

}
