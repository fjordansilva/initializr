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

package io.spring.initializr.generator.spring.documentation;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.io.text.BulletedSection;
import io.spring.initializr.generator.io.text.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Section that provides links and other important references to get started.
 *
 * @author Madhura Bhave
 * @author Stephane Nicoll
 */
public final class GettingStartedSection extends PreDefinedSection {

    private final BulletedSection<Link> referenceDocs;

    private final BulletedSection<Link> guides;

    private final BulletedSection<Link> additionalLinks;

    GettingStartedSection(MustacheTemplateRenderer templateRenderer) {
        super("Getting Started");
        referenceDocs = new BulletedSection<>(templateRenderer, "documentation/reference-documentation");
        guides = new BulletedSection<>(templateRenderer, "documentation/guides");
        additionalLinks = new BulletedSection<>(templateRenderer, "documentation/additional-links");
    }

    @Override
    public boolean isEmpty() {
        return referenceDocs().isEmpty() && guides().isEmpty() && additionalLinks().isEmpty() && super.isEmpty();
    }

    @Override
    protected List<Section> resolveSubSections(List<Section> sections) {
        List<Section> allSections = new ArrayList<>();
        allSections.add(referenceDocs);
        allSections.add(guides);
        allSections.add(additionalLinks);
        allSections.addAll(sections);
        return allSections;
    }

    public GettingStartedSection addReferenceDocLink(String href, String description) {
        referenceDocs.addItem(new Link(href, description));
        return this;
    }

    public BulletedSection<Link> referenceDocs() {
        return referenceDocs;
    }

    public GettingStartedSection addGuideLink(String href, String description) {
        guides.addItem(new Link(href, description));
        return this;
    }

    public BulletedSection<Link> guides() {
        return guides;
    }

    public GettingStartedSection addAdditionalLink(String href, String description) {
        additionalLinks.addItem(new Link(href, description));
        return this;
    }

    public BulletedSection<Link> additionalLinks() {
        return additionalLinks;
    }

    /**
     * Internal representation of a link.
     */
    public static class Link {

        private final String href;

        private final String description;

        Link(String href, String description) {
            this.href = href;
            this.description = description;
        }

        public String getHref() {
            return href;
        }

        public String getDescription() {
            return description;
        }

    }

}
