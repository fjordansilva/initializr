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

package io.spring.initializr.metadata;

import io.spring.initializr.generator.version.Version;
import io.spring.initializr.generator.version.VersionParser;
import io.spring.initializr.generator.version.VersionProperty;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Meta-data used to generate a project.
 *
 * @author Stephane Nicoll
 * @see ServiceCapability
 */
public class InitializrMetadata {

    private final InitializrConfiguration configuration;

    private final DependenciesCapability dependencies = new DependenciesCapability();

    private final TypeCapability types = new TypeCapability();

    private final SingleSelectCapability bootVersions = new SingleSelectCapability(
            "bootVersion", "Spring Boot Version", "spring boot version");

    private final SingleSelectCapability packagings = new SingleSelectCapability(
            "packaging", "Packaging", "project packaging");

    private final SingleSelectCapability javaVersions = new SingleSelectCapability(
            "javaVersion", "Java Version", "language level");

    private final SingleSelectCapability languages = new SingleSelectCapability(
            "language", "Language", "programming language");

    private final SingleSelectCapability ci = new SingleSelectCapability(
            "ci", "Continuous Integration", "Continuous Integration Type");

    private final SingleSelectCapability container = new SingleSelectCapability(
            "container", "Container System", "Container System");

    private final TextCapability name = new TextCapability("name", "Name",
            "project name (infer application name)");

    private final TextCapability irn = new RenaultIRNCapability("IRN-XXXXX");
    private final TextCapability sia = new RenaultSIACapability("AAA");

    private final TextCapability description = new TextCapability("description",
            "Description", "project description");

    private final TextCapability groupId = new TextCapability("groupId", "Group",
            "project coordinates");

    private final TextCapability artifactId = new ArtifactIdCapability(name);

    private final TextCapability version = new TextCapability("version", "Version",
            "project version");

    private final TextCapability packageName = new PackageCapability(groupId,
            artifactId);

    public InitializrMetadata() {
        this(new InitializrConfiguration());
    }

    protected InitializrMetadata(InitializrConfiguration configuration) {
        this.configuration = configuration;
    }

    public InitializrConfiguration getConfiguration() {
        return configuration;
    }

    public DependenciesCapability getDependencies() {
        return dependencies;
    }

    public TypeCapability getTypes() {
        return types;
    }

    public SingleSelectCapability getBootVersions() {
        return bootVersions;
    }

    public SingleSelectCapability getPackagings() {
        return packagings;
    }

    public SingleSelectCapability getJavaVersions() {
        return javaVersions;
    }

    public SingleSelectCapability getLanguages() {
        return languages;
    }

    public SingleSelectCapability getCi() {
        return ci;
    }

    public SingleSelectCapability getContainer() {
        return container;
    }

    public TextCapability getName() {
        return name;
    }

    public TextCapability getDescription() {
        return description;
    }

    public TextCapability getIrn() {
        return irn;
    }

    public TextCapability getSia() {
        return sia;
    }


    public TextCapability getGroupId() {
        return groupId;
    }

    public TextCapability getArtifactId() {
        return artifactId;
    }

    public TextCapability getVersion() {
        return version;
    }

    public TextCapability getPackageName() {
        return packageName;
    }

    /**
     * Merge this instance with the specified argument.
     *
     * @param other
     *         the other instance
     */
    public void merge(InitializrMetadata other) {
        configuration.merge(other.configuration);
        dependencies.merge(other.dependencies);
        types.merge(other.types);
        bootVersions.merge(other.bootVersions);
        packagings.merge(other.packagings);
        javaVersions.merge(other.javaVersions);
        languages.merge(other.languages);
        name.merge(other.name);
        description.merge(other.description);
        ci.merge(other.ci);
        container.merge(other.container);
        irn.merge(other.irn);
        sia.merge(other.sia);
        groupId.merge(other.groupId);
        artifactId.merge(other.artifactId);
        version.merge(other.version);
        packageName.merge(other.packageName);
    }

    /**
     * Validate the metadata.
     */
    public void validate() {
        configuration.validate();
        dependencies.validate();

        Map<String, Repository> repositories = configuration.getEnv()
                .getRepositories();
        Map<String, BillOfMaterials> boms = configuration.getEnv().getBoms();
        for (Dependency dependency : dependencies.getAll()) {
            if (dependency.getBom() != null && !boms.containsKey(dependency.getBom())) {
                throw new InvalidInitializrMetadataException(
                        "Dependency " + dependency + "defines an invalid BOM id "
                                + dependency.getBom() + ", available boms " + boms);
            }

            if (dependency.getRepository() != null
                    && !repositories.containsKey(dependency.getRepository())) {
                throw new InvalidInitializrMetadataException("Dependency " + dependency
                        + "defines an invalid repository id " + dependency.getRepository()
                        + ", available repositories " + repositories);
            }
        }
        for (BillOfMaterials bom : boms.values()) {
            for (String r : bom.getRepositories()) {
                if (!repositories.containsKey(r)) {
                    throw new InvalidInitializrMetadataException(
                            bom + "defines an invalid repository id " + r
                                    + ", available repositories " + repositories);
                }
            }
            for (String b : bom.getAdditionalBoms()) {
                if (!boms.containsKey(b)) {
                    throw new InvalidInitializrMetadataException(
                            bom + " defines an invalid " + "additional bom id " + b
                                    + ", available boms " + boms);
                }
            }
            for (BillOfMaterials.Mapping m : bom.getMappings()) {
                for (String r : m.getRepositories()) {
                    if (!repositories.containsKey(r)) {
                        throw new InvalidInitializrMetadataException(
                                m + " of " + bom + "defines an invalid repository id " + r
                                        + ", available repositories " + repositories);
                    }

                }
                for (String b : m.getAdditionalBoms()) {
                    if (!boms.containsKey(b)) {
                        throw new InvalidInitializrMetadataException(m + " of " + bom
                                + " defines " + "an invalid additional bom id " + b
                                + ", available boms " + boms);
                    }
                }
            }
        }
    }

    /**
     * Update the available Spring Boot versions with the specified capabilities.
     *
     * @param versionsMetadata
     *         the Spring Boot boot versions metadata to use
     */
    public void updateSpringBootVersions(List<DefaultMetadataElement> versionsMetadata) {
        bootVersions.getContent().clear();
        bootVersions.getContent().addAll(versionsMetadata);
        List<Version> bootVersions = this.bootVersions.getContent().stream().map((it) -> Version.parse(it.getId())).collect(Collectors.toList());
        VersionParser parser       = new VersionParser(bootVersions);
        dependencies.updateVersionRange(parser);
        configuration.getEnv().getBoms().values().forEach((it) -> it.updateVersionRange(parser));
        configuration.getEnv().getKotlin().updateVersionRange(parser);
    }

    /**
     * Create an URL suitable to download Spring Boot cli for the specified version and
     * extension.
     *
     * @param extension
     *         the required extension
     *
     * @return the download URL
     */
    public String createCliDistributionURl(String extension) {
        String bootVersion = InitializrMetadata.defaultId(bootVersions);
        return configuration.getEnv().getArtifactRepository()
                + "org/springframework/boot/spring-boot-cli/" + bootVersion
                + "/spring-boot-cli-" + bootVersion + "-bin." + extension;
    }

    /**
     * Create a {@link BillOfMaterials} for the spring boot BOM.
     *
     * @param bootVersion
     *         the Spring Boot version
     * @param versionProperty
     *         the property that contains the version
     *
     * @return a new {@link BillOfMaterials} instance
     */
    public BillOfMaterials createSpringBootBom(String bootVersion,
                                               String versionProperty) {
        BillOfMaterials bom = BillOfMaterials.create("org.springframework.boot",
                "spring-boot-dependencies", bootVersion);
        bom.setVersionProperty(VersionProperty.of(versionProperty));
        bom.setOrder(100);
        return bom;
    }

    /**
     * Return the defaults for the capabilities defined on this instance.
     *
     * @return the default capabilities
     */
    public Map<String, Object> defaults() {
        Map<String, Object> defaults = new LinkedHashMap<>();
        defaults.put("type", InitializrMetadata.defaultId(types));
        defaults.put("bootVersion", InitializrMetadata.defaultId(bootVersions));
        defaults.put("packaging", InitializrMetadata.defaultId(packagings));
        defaults.put("javaVersion", InitializrMetadata.defaultId(javaVersions));
        defaults.put("language", InitializrMetadata.defaultId(languages));
        defaults.put("ci", InitializrMetadata.defaultId(ci));
        defaults.put("container", InitializrMetadata.defaultId(container));
        defaults.put("irn", irn.getContent());
        defaults.put("sia", sia.getContent());
        defaults.put("groupId", groupId.getContent());
        defaults.put("artifactId", artifactId.getContent());
        defaults.put("version", version.getContent());
        defaults.put("name", name.getContent());
        defaults.put("description", description.getContent());
        defaults.put("packageName", packageName.getContent());
        return defaults;
    }

    private static String defaultId(Defaultable<? extends DefaultMetadataElement> element) {
        DefaultMetadataElement defaultValue = element.getDefault();
        return (defaultValue != null) ? defaultValue.getId() : null;
    }

    private static class RenaultIRNCapability extends TextCapability {

        private final String defaultValue;

        RenaultIRNCapability(String defaultValue) {
            super("irn", "IRN", "IRN of the project");
            this.defaultValue = defaultValue;
        }

        @Override
        public String getContent() {
            String value = super.getContent();
            return (value == null) ? defaultValue : value;
        }

    }

    private static class RenaultSIACapability extends TextCapability {

        private final String defaultValue;

        RenaultSIACapability(String defaultValue) {
            super("sia", "SIA", "SIA of the project");
            this.defaultValue = defaultValue;
        }

        @Override
        public String getContent() {
            String value = super.getContent();
            return (value == null) ? defaultValue : value;
        }

    }

    private static class ArtifactIdCapability extends TextCapability {

        private final TextCapability nameCapability;

        ArtifactIdCapability(TextCapability nameCapability) {
            super("artifactId", "Artifact", "project coordinates (infer archive name)");
            this.nameCapability = nameCapability;
        }

        @Override
        public String getContent() {
            String value = super.getContent();
            return (value != null) ? value : nameCapability.getContent();
        }

    }

    private static class PackageCapability extends TextCapability {

        private final TextCapability groupId;

        private final TextCapability artifactId;

        PackageCapability(TextCapability groupId, TextCapability artifactId) {
            super("packageName", "Package Name", "root package");
            this.groupId = groupId;
            this.artifactId = artifactId;
        }

        @Override
        public String getContent() {
            String value = super.getContent();
            if (value != null) {
                return value;
            } else if (groupId.getContent() != null
                    && artifactId.getContent() != null) {
                return InitializrConfiguration.cleanPackageName(
                        groupId.getContent() + "." + artifactId.getContent());
            }
            return null;
        }

    }

}
