package com.urise.webapp.model;

import java.util.Objects;

public abstract class Section {
    protected final SectionType type;

    public Section(SectionType type) {
        Objects.requireNonNull(type, "type must not be null");
        this.type = type;
    }

    public SectionType getType() {
        return type;
    }

    public abstract String getContent();

    public abstract void addContent(String content);

    @Override
    public String toString() {
        return type.getTitle() + ": " + getContent();
    }
}
