package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {
    private final Link homepage;
    private final List<Period> periods = new ArrayList<>();

    public Company(String name, String url, List<Period> periods) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(url, "url must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.homepage = new Link(name, url);
        this.periods.addAll(periods);
    }

    @Override
    public String toString() {
        return "Company{" +
                "homepage=" + homepage +
                ", periods=" + periods +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return homepage.equals(company.homepage) && periods.equals(company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, periods);
    }
}
