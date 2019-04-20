package io.spring.initializr.generator.spring.container;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.io.text.MustacheSection;
import io.spring.initializr.generator.io.text.Section;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class ContainerDocument {

    private final MustacheTemplateRenderer templateRenderer;

    private final LinkedList<Section> sections = new LinkedList<>();

    public ContainerDocument(MustacheTemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    public ContainerDocument addSection(Section section) {
        sections.add(section);
        return this;
    }

    /**
     * Add a section rendered by the specified mustache template and model.
     *
     * @param templateName
     *         the name of the mustache template to render
     * @param model
     *         the model that should be used for the rendering
     *
     * @return this document
     */
    public ContainerDocument addSection(String templateName, Map<String, Object> model) {
        return addSection(new MustacheSection(templateRenderer, templateName, model));
    }

    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    public void write(PrintWriter writer) throws IOException {
        LinkedList<Section> allSections = new LinkedList<>(sections);
        // allSections.addFirst(this.gettingStarted);
        // allSections.addLast(this.nextSteps);
        for (Section section : allSections) {
            section.write(writer);
        }
    }
}
