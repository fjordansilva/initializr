package io.spring.initializr.generator.buildsystem.maven;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class MavenOrganization {

    private String name;

    private String url;

    public MavenOrganization() {
        this(null, null);
    }

    public MavenOrganization(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
