package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Period {
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String title;
    private final String description;

    public Period(YearMonth start, YearMonth endDate, String title, String description) {
        Objects.requireNonNull(start, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDateDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(description, "description must not be null");
        this.startDate = start;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Period period = (Period) o;
        return Objects.equals(startDate, period.startDate) &&
                Objects.equals(endDate, period.endDate) &&
                Objects.equals(title, period.title) &&
                Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return "Period{" +
                "start=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
