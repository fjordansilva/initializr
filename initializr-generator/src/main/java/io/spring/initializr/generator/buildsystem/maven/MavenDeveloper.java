package io.spring.initializr.generator.buildsystem.maven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class MavenDeveloper {

    private String id;

    private String name;

    private String email;

    private final List<String> roles = new ArrayList<>();

    public MavenDeveloper() {
        this(null, null, null);
    }

    public MavenDeveloper(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public void role(String role) {
        roles.add(role);
    }
}
