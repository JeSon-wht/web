package org.xij.web.module.base;

import java.io.Serializable;

public class Entity implements Serializable {
    protected String id;

    public Entity() {
    }

    public Entity(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}