package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

public class Company {
    private final Link homepage;
    private List<Period> periods = new ArrayList<>();

    public Company(String name, String url, Period... periods) {
        this(new Link(name, url), Arrays.asList(periods));
    }

    public Company(Link homepage, List<Period> periods) {
        this.homepage = homepage;
        this.periods = periods;
    }

    @Override
    public String toString() {
        return "Company(" + homepage + "," + periods + ')';
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

    public static class Period {
        private final String title;
        private final String description;
        private final LocalDate startDate;
        private final LocalDate endDate;

        public Period(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(title, "title must not be null");
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return "Period{" + startDate + ',' + endDate + ',' + title + ',' + description + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return title.equals(period.title) && Objects.equals(description, period.description) && startDate.equals(period.startDate) && endDate.equals(period.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, description, startDate, endDate);
        }
    }
}
