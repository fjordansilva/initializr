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

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.spring.initializr.generator.version.InvalidVersionException;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.generator.version.VersionParser;
import io.spring.initializr.generator.version.VersionRange;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Various configuration options used by the service.
 *
 * @author Stephane Nicoll
 */
public class InitializrConfiguration {

    @NestedConfigurationProperty
    private final Proxy proxy = new Proxy();

    public Proxy getProxy() {
        return proxy;
    }

    /**
     * Environment options.
     */
    @NestedConfigurationProperty
    private final Env env = new Env();

    public Env getEnv() {
        return env;
    }

    public void validate() {
        env.validate();
        proxy.validate();
    }

    public void merge(InitializrConfiguration other) {
        env.merge(other.env);
        proxy.merge(other.proxy);
    }

    /**
     * Generate a suitable application name based on the specified name. If no suitable
     * application name can be generated from the specified {@code name}, the
     * {@link Env#getFallbackApplicationName()} is used instead.
     * <p>
     * No suitable application name can be generated if the name is {@code null} or if it
     * contains an invalid character for a class identifier.
     *
     * @param name
     *         the the source name
     *
     * @return the generated application name
     *
     * @see Env#getFallbackApplicationName()
     * @see Env#getInvalidApplicationNames()
     */
    public String generateApplicationName(String name) {
        if (!StringUtils.hasText(name)) {
            return env.fallbackApplicationName;
        }
        String text = InitializrConfiguration.splitCamelCase(name.trim());
        // TODO: fix this
        String result = InitializrConfiguration.unsplitWords(text);
        if (!result.endsWith("Application")) {
            result = result + "Application";
        }
        String candidate = StringUtils.capitalize(result);
        if (InitializrConfiguration.hasInvalidChar(candidate)
                || env.invalidApplicationNames.contains(candidate)) {
            return env.fallbackApplicationName;
        } else {
            return candidate;
        }
    }

    /**
     * Clean the specified package name if necessary. If the package name cannot be
     * transformed to a valid package name, the {@code defaultPackageName} is used
     * instead.
     * <p>
     * The package name cannot be cleaned if the specified {@code packageName} is
     * {@code null} or if it contains an invalid character for a class identifier.
     *
     * @param packageName
     *         the package name
     * @param defaultPackageName
     *         the default package name
     *
     * @return the cleaned package name
     *
     * @see Env#getInvalidPackageNames()
     */
    public String cleanPackageName(String packageName, String defaultPackageName) {
        if (!StringUtils.hasText(packageName)) {
            return defaultPackageName;
        }
        String candidate = InitializrConfiguration.cleanPackageName(packageName);
        if (!StringUtils.hasText(candidate)) {
            return defaultPackageName;
        }
        if (InitializrConfiguration.hasInvalidChar(candidate.replace(".", ""))
                || env.invalidPackageNames.contains(candidate)) {
            return defaultPackageName;
        } else {
            return candidate;
        }
    }

    static String cleanPackageName(String packageName) {
        String[]      elements = packageName.trim().replaceAll("-", "").split("\\W+");
        StringBuilder sb       = new StringBuilder();
        for (String element : elements) {
            element = element.replaceFirst("^[0-9]+(?!$)", "");
            if (!element.matches("[0-9]+") && sb.length() > 0) {
                sb.append(".");
            }
            sb.append(element);
        }
        return sb.toString();
    }

    private static String unsplitWords(String text) {
        return String.join("", Arrays.stream(text.split("(_|-| |:)+"))
                .map(StringUtils::capitalize).toArray(String[]::new));
    }

    private static String splitCamelCase(String text) {
        return String.join("",
                Arrays.stream(text.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])"))
                        .map((it) -> StringUtils.capitalize(it.toLowerCase()))
                        .toArray(String[]::new));
    }

    private static boolean hasInvalidChar(String text) {
        if (!Character.isJavaIdentifierStart(text.charAt(0))) {
            return true;
        }
        if (text.length() > 1) {
            for (int i = 1; i < text.length(); i++) {
                if (!Character.isJavaIdentifierPart(text.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static class Proxy {
        private boolean enabled = false;
        private String  host;
        private int     port;
        private String  username;
        private String  password;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void merge(Proxy other) {
            enabled = other.enabled;
            host = other.host;
            port = other.port;
            username = other.username;
            password = other.password;
        }

        public void validate() {
            if (!isEnabled()) {
                return;
            }

            if (!StringUtils.hasText(host) && port == 0 && !StringUtils.hasText(username) && !StringUtils.hasText(password)) {
                throw new InvalidInitializrMetadataException("Invalid custom proxy configuration");
            }
        }
    }

    /**
     * Defines additional environment settings.
     */
    public static class Env {

        /**
         * The url of the repository servicing distribution bundle.
         */
        private String artifactRepository = "https://repo.spring.io/release/";

        /**
         * The metadata url of the Spring Boot project.
         */
        private String springBootMetadataUrl = "https://spring.io/project_metadata/spring-boot";

        /**
         * Tracking code for Google Analytics. Only enabled if a value is explicitly
         * provided.
         */
        private String googleAnalyticsTrackingCode;

        /**
         * The application name to use if none could be generated.
         */
        private String fallbackApplicationName = "Application";

        /**
         * The list of invalid application names. If such name is chosen or generated, the
         * "fallbackApplicationName" should be used instead.
         */
        private List<String> invalidApplicationNames = new ArrayList<>(
                Arrays.asList("SpringApplication", "SpringBootApplication"));

        /**
         * The list of invalid package names. If such name is chosen or generated, the the
         * default package name should be used instead.
         */
        private List<String> invalidPackageNames = new ArrayList<>(
                Collections.singletonList("org.springframework"));

        /**
         * Force SSL support. When enabled, any access using http generate https links and
         * browsers are redirected to https for html content.
         */
        private boolean forceSsl;

        /**
         * The "BillOfMaterials" that are referenced in this instance, identified by an
         * arbitrary identifier that can be used in the dependencies definition.
         */
        private final Map<String, BillOfMaterials> boms = new LinkedHashMap<>();

        /**
         * The "Repository" instances that are referenced in this instance, identified by
         * an arbitrary identifier that can be used in the dependencies definition.
         */
        private final Map<String, Repository> repositories = new LinkedHashMap<>();

        /**
         * The list of valid version tags of SpringBoot.
         */
        private List<String> springBootVersionTags = new ArrayList<>();

        /**
         * Gradle-specific settings.
         */
        @NestedConfigurationProperty
        private final Gradle gradle = new Gradle();

        /**
         * Kotlin-specific settings.
         */
        @NestedConfigurationProperty
        private final Kotlin kotlin = new Kotlin();

        /**
         * Maven-specific settings.
         */
        @NestedConfigurationProperty
        private final Maven maven = new Maven();

        public Env() {
            try {
                repositories.put("spring-snapshots",
                        new Repository("Spring Snapshots",
                                new URL("https://repo.spring.io/snapshot"), true));
                repositories.put("spring-milestones",
                        new Repository("Spring Milestones",
                                new URL("https://repo.spring.io/milestone"), false));
            } catch (MalformedURLException ex) {
                throw new IllegalStateException("Cannot parse URL", ex);
            }
        }

        public List<String> getSpringBootVersionTags() {
            return springBootVersionTags;
        }

        public void setSpringBootVersionTags(List<String> springBootVersionTags) {
            this.springBootVersionTags = springBootVersionTags;
        }

        public String getSpringBootMetadataUrl() {
            return springBootMetadataUrl;
        }

        public void setSpringBootMetadataUrl(String springBootMetadataUrl) {
            this.springBootMetadataUrl = springBootMetadataUrl;
        }

        public String getGoogleAnalyticsTrackingCode() {
            return googleAnalyticsTrackingCode;
        }

        public void setGoogleAnalyticsTrackingCode(String googleAnalyticsTrackingCode) {
            this.googleAnalyticsTrackingCode = googleAnalyticsTrackingCode;
        }

        public String getFallbackApplicationName() {
            return fallbackApplicationName;
        }

        public void setFallbackApplicationName(String fallbackApplicationName) {
            this.fallbackApplicationName = fallbackApplicationName;
        }

        public List<String> getInvalidApplicationNames() {
            return invalidApplicationNames;
        }

        public void setInvalidApplicationNames(List<String> invalidApplicationNames) {
            this.invalidApplicationNames = invalidApplicationNames;
        }

        public List<String> getInvalidPackageNames() {
            return invalidPackageNames;
        }

        public void setInvalidPackageNames(List<String> invalidPackageNames) {
            this.invalidPackageNames = invalidPackageNames;
        }

        public boolean isForceSsl() {
            return forceSsl;
        }

        public void setForceSsl(boolean forceSsl) {
            this.forceSsl = forceSsl;
        }

        public String getArtifactRepository() {
            return artifactRepository;
        }

        public Map<String, BillOfMaterials> getBoms() {
            return boms;
        }

        public Map<String, Repository> getRepositories() {
            return repositories;
        }

        public Gradle getGradle() {
            return gradle;
        }

        public Kotlin getKotlin() {
            return kotlin;
        }

        public Maven getMaven() {
            return maven;
        }

        public void setArtifactRepository(String artifactRepository) {
            if (!artifactRepository.endsWith("/")) {
                artifactRepository = artifactRepository + "/";
            }
            this.artifactRepository = artifactRepository;
        }

        public void validate() {
            maven.parent.validate();
            boms.forEach((k, v) -> v.validate());
            kotlin.validate();
        }

        public void merge(Env other) {
            artifactRepository = other.artifactRepository;
            springBootMetadataUrl = other.springBootMetadataUrl;
            googleAnalyticsTrackingCode = other.googleAnalyticsTrackingCode;
            fallbackApplicationName = other.fallbackApplicationName;
            invalidApplicationNames = other.invalidApplicationNames;
            springBootVersionTags = other.springBootVersionTags;
            forceSsl = other.forceSsl;
            gradle.merge(other.gradle);
            kotlin.merge(other.kotlin);
            maven.merge(other.maven);
            other.boms.forEach(boms::putIfAbsent);
            other.repositories.forEach(repositories::putIfAbsent);
        }

        /**
         * Gradle details.
         */
        public static class Gradle {

            /**
             * Version of the "dependency-management-plugin" to use.
             */
            private String dependencyManagementPluginVersion = "1.0.0.RELEASE";

            private void merge(Gradle other) {
                dependencyManagementPluginVersion = other.dependencyManagementPluginVersion;
            }

            public String getDependencyManagementPluginVersion() {
                return dependencyManagementPluginVersion;
            }

            public void setDependencyManagementPluginVersion(
                    String dependencyManagementPluginVersion) {
                this.dependencyManagementPluginVersion = dependencyManagementPluginVersion;
            }

        }

        /**
         * Kotlin details.
         */
        public static class Kotlin {

            /**
             * Default kotlin version.
             */
            private String defaultVersion;

            /**
             * Kotlin version mappings.
             */
            private final List<Mapping> mappings = new ArrayList<>();

            /**
             * Resolve the kotlin version to use based on the Spring Boot version.
             *
             * @param bootVersion
             *         the Spring Boot version
             *
             * @return the kotlin version to use
             */
            public String resolveKotlinVersion(Version bootVersion) {
                for (Mapping mapping : mappings) {
                    if (mapping.range.match(bootVersion)) {
                        return mapping.version;
                    }
                }
                if (defaultVersion == null) {
                    throw new InvalidInitializrMetadataException(
                            "No Kotlin version mapping available for " + bootVersion);
                }
                return defaultVersion;
            }

            public String getDefaultVersion() {
                return defaultVersion;
            }

            public void setDefaultVersion(String defaultVersion) {
                this.defaultVersion = defaultVersion;
            }

            public List<Mapping> getMappings() {
                return mappings;
            }

            public void validate() {
                VersionParser simpleParser = new VersionParser(Collections.emptyList());
                mappings.forEach((m) -> {
                    if (m.versionRange == null) {
                        throw new InvalidInitializrMetadataException(
                                "VersionRange is mandatory, invalid version mapping for "
                                        + this);
                    }
                    m.range = simpleParser.parseRange(m.versionRange);
                    if (m.version == null) {
                        throw new InvalidInitializrMetadataException(
                                "Version is mandatory, invalid version mapping for "
                                        + this);
                    }
                });
            }

            public void updateVersionRange(VersionParser versionParser) {
                mappings.forEach((it) -> {
                    try {
                        it.range = versionParser.parseRange(it.versionRange);
                    } catch (InvalidVersionException ex) {
                        throw new InvalidInitializrMetadataException(
                                "Invalid version range " + it.versionRange + " for "
                                        + this,
                                ex);
                    }
                });
            }

            private void merge(Kotlin other) {
                defaultVersion = other.defaultVersion;
                mappings.clear();
                mappings.addAll(other.mappings);
            }

            /**
             * Map several attribute of the dependency for a given version range.
             */
            public static class Mapping {

                /**
                 * The version range of this mapping.
                 */
                private String versionRange;

                /**
                 * The kotlin version for this mapping.
                 */
                private String version;

                @JsonIgnore
                private VersionRange range;

                public String getVersionRange() {
                    return versionRange;
                }

                public void setVersionRange(String versionRange) {
                    this.versionRange = versionRange;
                }

                public String getVersion() {
                    return version;
                }

                public void setVersion(String version) {
                    this.version = version;
                }

            }

        }

        /**
         * Maven details.
         */
        public static class Maven {

            private static final String DEFAULT_PARENT_GROUP_ID = "org.springframework.boot";

            private static final String DEFAULT_PARENT_ARTIFACT_ID = "spring-boot-starter-parent";

            /**
             * Custom parent pom to use for generated projects.
             */
            private final ParentPom parent = new ParentPom();

            public ParentPom getParent() {
                return parent;
            }

            private void merge(Maven other) {
                parent.groupId = other.parent.groupId;
                parent.artifactId = other.parent.artifactId;
                parent.version = other.parent.version;
                parent.includeSpringBootBom = other.parent.includeSpringBootBom;
            }

            /**
             * Resolve the parent pom to use. If no custom parent pom is set, the standard
             * spring boot parent pom with the specified {@code bootVersion} is used.
             *
             * @param bootVersion
             *         the Spring Boot version
             *
             * @return the parent POM
             */
            public ParentPom resolveParentPom(String bootVersion) {
                return (StringUtils.hasText(parent.groupId) ? parent
                        : new ParentPom(Maven.DEFAULT_PARENT_GROUP_ID,
                        Maven.DEFAULT_PARENT_ARTIFACT_ID, bootVersion));
            }

            /**
             * Check if the specified {@link ParentPom} is the default spring boot starter
             * parent.
             *
             * @param parentPom
             *         the parent pom to check
             *
             * @return {@code true} if the {@code parentPom} is the spring boot starter
             * parent
             */
            public boolean isSpringBootStarterParent(ParentPom parentPom) {
                return Maven.DEFAULT_PARENT_GROUP_ID.equals(parentPom.getGroupId())
                        && Maven.DEFAULT_PARENT_ARTIFACT_ID.equals(parentPom.getArtifactId());
            }

            /**
             * Parent POM details.
             */
            public static class ParentPom {

                /**
                 * Parent pom groupId.
                 */
                private String groupId;

                /**
                 * Parent pom artifactId.
                 */
                private String artifactId;

                /**
                 * Parent pom version.
                 */
                private String version;

                /**
                 * Add the "spring-boot-dependencies" BOM to the project.
                 */
                private boolean includeSpringBootBom;

                public ParentPom(String groupId, String artifactId, String version) {
                    this.groupId = groupId;
                    this.artifactId = artifactId;
                    this.version = version;
                }

                public ParentPom() {
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

                public boolean isIncludeSpringBootBom() {
                    return includeSpringBootBom;
                }

                public void setIncludeSpringBootBom(boolean includeSpringBootBom) {
                    this.includeSpringBootBom = includeSpringBootBom;
                }

                public void validate() {
                    if (!((!StringUtils.hasText(groupId)
                            && !StringUtils.hasText(artifactId)
                            && !StringUtils.hasText(version))
                            || (StringUtils.hasText(groupId)
                            && StringUtils.hasText(artifactId)
                            && StringUtils.hasText(version)))) {
                        throw new InvalidInitializrMetadataException("Custom maven pom "
                                + "requires groupId, artifactId and version");
                    }
                }

            }

        }

    }

}
