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

package io.spring.initializr.generator.buildsystem.maven;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;

import java.util.*;

/**
 * Maven build for a project.
 *
 * @author Andy Wilkinson
 */
public class MavenBuild extends Build {

    private MavenParent parent;

    private String name;

    private String description;

    private String url;

    private String sourceDirectory;

    private String testSourceDirectory;

    private MavenOrganization organization;

    private MavenSCM scm;

    private final List<MavenLicense> licenses = new ArrayList<>();

    private final Map<String, MavenDeveloper> developers = new LinkedHashMap<>();

    private final Map<String, String> properties = new TreeMap<>();

    private final Map<String, MavenPlugin> plugins = new LinkedHashMap<>();

    private String packaging;

    public MavenBuild(BuildItemResolver buildItemResolver) {
        super(buildItemResolver);
    }

    public MavenBuild() {
        this(null);
    }

    public MavenParent parent(String groupId, String artifactId, String version) {
        parent = new MavenParent(groupId, artifactId, version);
        return parent;
    }

    public MavenParent getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public MavenOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(String name, String url) {
        setOrganization(new MavenOrganization(name, url));
    }

    public void setOrganization(MavenOrganization organization) {
        this.organization = organization;
    }

    public MavenSCM getScm() {
        return scm;
    }

    public void setScm(MavenSCM scm) {
        this.scm = scm;
    }

    public void setLicense(String name, String url, String distribution) {
        licenses.add(new MavenLicense(name, url, distribution));
    }

    public List<MavenLicense> getLicenses() {
        return Collections.unmodifiableList(licenses);
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    public MavenDeveloper developer(String id, String name, String email) {
        return developers.computeIfAbsent(id, (key) -> new MavenDeveloper(id, name, email));
    }

    public List<MavenDeveloper> getDevelopers() {
        return Collections.unmodifiableList(new ArrayList<>(developers.values()));
    }

    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public String getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public String getTestSourceDirectory() {
        return testSourceDirectory;
    }

    public void setTestSourceDirectory(String testSourceDirectory) {
        this.testSourceDirectory = testSourceDirectory;
    }

    public MavenPlugin plugin(String groupId, String artifactId) {
        return plugins.computeIfAbsent(pluginKey(groupId, artifactId),
                (id) -> new MavenPlugin(groupId, artifactId));
    }

    public MavenPlugin plugin(String groupId, String artifactId, String version) {
        MavenPlugin mavenPlugin = plugins.computeIfAbsent(
                pluginKey(groupId, artifactId),
                (id) -> new MavenPlugin(groupId, artifactId));
        mavenPlugin.setVersion(version);
        return mavenPlugin;
    }

    private String pluginKey(String groupId, String artifactId) {
        return String.format("%s:%s", groupId, artifactId);
    }

    public List<MavenPlugin> getPlugins() {
        return Collections.unmodifiableList(new ArrayList<>(plugins.values()));
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getPackaging() {
        return packaging;
    }

}
