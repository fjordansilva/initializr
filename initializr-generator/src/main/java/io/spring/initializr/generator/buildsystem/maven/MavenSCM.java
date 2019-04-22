package io.spring.initializr.generator.buildsystem.maven;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class MavenSCM {

    private String connection;

    private String developerConnection;

    private String url;

    public MavenSCM() {
    }

    public String getConnection() {
        return connection;
    }

    public MavenSCM connection(String connection) {
        this.connection = connection;
        return this;
    }

    public String getDeveloperConnection() {
        return developerConnection;
    }

    public MavenSCM developerConnection(String developerConnection) {
        this.developerConnection = developerConnection;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public MavenSCM url(String url) {
        this.url = url;
        return this;
    }
}
