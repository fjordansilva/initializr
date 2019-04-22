package io.spring.initializr.generator.buildsystem.maven;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class MavenLicense {

    private String name;

    private String url;

    private String distribution;

    public MavenLicense() {
        this(null, null, null);
    }

    public MavenLicense(String name, String url, String distribution) {
        this.name = name;
        this.url = url;
        this.distribution = distribution;
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

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }
}
