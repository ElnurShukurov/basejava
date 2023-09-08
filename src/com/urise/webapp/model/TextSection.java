package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends Section {
    private String content;

    public TextSection(SectionType type, String content) {
        super(type);
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void addContent(String content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }
}
