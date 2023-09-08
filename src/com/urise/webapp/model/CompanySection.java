package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {

    private final List<Company> companies;

    public CompanySection(SectionType type, List<Company> companies) {
        super(type);
        Objects.requireNonNull(companies, "companies must not be null");
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public String getContent() {
        StringBuilder sb = new StringBuilder();
        for (Company company : companies) {
            sb.append("- ").append(company.getWebsite()).append("\n");
            for (Period period : company.getPeriods()) {
                sb.append(period);
            }
        }
        return sb.toString();
    }

    @Override
    public void addContent(String content) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companies);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "companies=" + companies +
                '}';
    }
}
