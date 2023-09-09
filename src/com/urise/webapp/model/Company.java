package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Company {
    private final Link homepage;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Company(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.homepage = new Link(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Company{" +
                "homepage=" + homepage +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(homepage, company.homepage) && startDate.equals(company.startDate) && endDate.equals(company.endDate) && title.equals(company.title) && Objects.equals(description, company.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, startDate, endDate, title, description);
    }
}
