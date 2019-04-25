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

package io.spring.initializr.web.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.spring.initializr.metadata.*;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link InitializrMetadataJsonMapper} handling the metadata format for v2.
 *
 * @author Stephane Nicoll
 */
public class InitializrMetadataV2JsonMapper implements InitializrMetadataJsonMapper {

    private static final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

    private final TemplateVariables templateVariables;

    public InitializrMetadataV2JsonMapper() {
        templateVariables = new TemplateVariables(
                new TemplateVariable("dependencies",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("packaging",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("javaVersion",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("language",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("bootVersion",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("groupId",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("artifactId",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("version",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("name",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("description",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("packageName",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("irn",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sia",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("ci",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("container",
                        TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("environment",
                        TemplateVariable.VariableType.REQUEST_PARAM));
    }

    protected JsonNodeFactory nodeFactory() {
        return InitializrMetadataV2JsonMapper.nodeFactory;
    }

    @Override
    public String write(InitializrMetadata metadata, String appUrl) {
        ObjectNode delegate = InitializrMetadataV2JsonMapper.nodeFactory.objectNode();
        links(delegate, metadata.getTypes().getContent(), appUrl);
        dependencies(delegate, metadata.getDependencies());
        type(delegate, metadata.getTypes());
        singleSelect(delegate, metadata.getPackagings());
        singleSelect(delegate, metadata.getJavaVersions());
        singleSelect(delegate, metadata.getLanguages());
        singleSelect(delegate, metadata.getBootVersions());
        singleSelect(delegate, metadata.getCis());
        singleSelect(delegate, metadata.getContainers());
        singleSelect(delegate, metadata.getEnvironments());
        text(delegate, metadata.getGroupId());
        text(delegate, metadata.getArtifactId());
        text(delegate, metadata.getVersion());
        text(delegate, metadata.getName());
        text(delegate, metadata.getDescription());
        text(delegate, metadata.getPackageName());
        text(delegate, metadata.getIrn());
        text(delegate, metadata.getSia());
        return delegate.toString();
    }

    protected ObjectNode links(ObjectNode parent, List<Type> types, String appUrl) {
        ObjectNode content = InitializrMetadataV2JsonMapper.nodeFactory.objectNode();
        types.forEach((it) -> content.set(it.getId(), link(appUrl, it)));
        parent.set("_links", content);
        return content;
    }

    protected ObjectNode link(String appUrl, Type type) {
        ObjectNode result = InitializrMetadataV2JsonMapper.nodeFactory.objectNode();
        result.put("href", generateTemplatedUri(appUrl, type));
        result.put("templated", true);
        return result;
    }

    private String generateTemplatedUri(String appUrl, Type type) {
        String uri = (appUrl != null) ? appUrl + type.getAction() : type.getAction();
        uri = uri + "?type=" + type.getId();
        UriTemplate uriTemplate = new UriTemplate(uri, templateVariables);
        return uriTemplate.toString();
    }

    protected void dependencies(ObjectNode parent, DependenciesCapability capability) {
        ObjectNode dependencies = InitializrMetadataV2JsonMapper.nodeFactory.objectNode();
        dependencies.put("type", capability.getType().getName());
        ArrayNode values = InitializrMetadataV2JsonMapper.nodeFactory.arrayNode();
        values.addAll(capability.getContent().stream().map(this::mapDependencyGroup)
                .collect(Collectors.toList()));
        dependencies.set("values", values);
        parent.set(capability.getId(), dependencies);
    }

    protected void type(ObjectNode parent, TypeCapability capability) {
        ObjectNode type = InitializrMetadataV2JsonMapper.nodeFactory.objectNode();
        type.put("type", "action");
        Type defaultType = capability.getDefault();
        if (defaultType != null) {
            type.put("default", defaultType.getId());
        }
        ArrayNode values = InitializrMetadataV2JsonMapper.nodeFactory.arrayNode();
        values.addAll(capability.getContent().stream().map(this::mapType)
                .collect(Collectors.toList()));
        type.set("values", values);
        parent.set("type", type);
    }

    protected void singleSelect(ObjectNode parent, SingleSelectCapability capability) {
        ObjectNode single = InitializrMetadataV2JsonMapper.nodeFactory.objectNode();
        single.put("type", capability.getType().getName());
        DefaultMetadataElement defaultType = capability.getDefault();
        if (defaultType != null) {
            single.put("default", defaultType.getId());
        }
        ArrayNode values = InitializrMetadataV2JsonMapper.nodeFactory.arrayNode();
        values.addAll(capability.getContent().stream().map(this::mapValue)
                .collect(Collectors.toList()));
        single.set("values", values);
        parent.set(capability.getId(), single);
    }

    protected void text(ObjectNode parent, TextCapability capability) {
        ObjectNode text = InitializrMetadataV2JsonMapper.nodeFactory.objectNode();
        text.put("type", capability.getType().getName());
        String defaultValue = capability.getContent();
        if (StringUtils.hasText(defaultValue)) {
            text.put("default", defaultValue);
        }
        parent.set(capability.getId(), text);
    }

    protected ObjectNode mapDependencyGroup(DependencyGroup group) {
        ObjectNode result = InitializrMetadataV2JsonMapper.nodeFactory.objectNode();
        result.put("name", group.getName());
        if ((group instanceof Describable)
                && ((Describable) group).getDescription() != null) {
            result.put("description", ((Describable) group).getDescription());
        }
        ArrayNode items = InitializrMetadataV2JsonMapper.nodeFactory.arrayNode();
        group.getContent().forEach((it) -> {
            JsonNode dependency = mapDependency(it);
            if (dependency != null) {
                items.add(dependency);
            }
        });
        result.set("values", items);
        return result;
    }

    protected ObjectNode mapDependency(Dependency dependency) {
        if (dependency.getVersionRange() == null) {
            // only map the dependency if no versionRange is set
            return mapValue(dependency);
        }
        return null;
    }

    protected ObjectNode mapType(Type type) {
        ObjectNode result = mapValue(type);
        result.put("action", type.getAction());
        ObjectNode tags = InitializrMetadataV2JsonMapper.nodeFactory.objectNode();
        type.getTags().forEach(tags::put);
        result.set("tags", tags);
        return result;
    }

    protected ObjectNode mapValue(MetadataElement value) {
        ObjectNode result = InitializrMetadataV2JsonMapper.nodeFactory.objectNode();
        result.put("id", value.getId());
        result.put("name", value.getName());
        if ((value instanceof Describable)
                && ((Describable) value).getDescription() != null) {
            result.put("description", ((Describable) value).getDescription());
        }
        return result;
    }

}
