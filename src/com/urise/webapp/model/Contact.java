package com.urise.webapp.model;

import java.util.Objects;

public class Contact {
    ContactType type;
    String value;

    public Contact(ContactType type, String value) {
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(value, "value must not be null");
        this.type = type;
        this.value = value;
    }

    public ContactType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Contact contact = (Contact) o;
        return type == contact.type && Objects.equals(value, contact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, value);
    }

    @Override
    public String toString() {
        return type + ": " + value;
    }
}
