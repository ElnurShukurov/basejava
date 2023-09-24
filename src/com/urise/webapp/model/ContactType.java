package com.urise.webapp.model;

public enum ContactType {
    PHONE("Тел.: "),
    SKYPE("Skype: "),
    EMAIL("Почта: "),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOverFlow"),
    HOME_PAGE("Домашняя страница");

    private String value;

    ContactType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
