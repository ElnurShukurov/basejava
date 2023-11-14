package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getCredentials();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        final boolean isExist = (uuid == null || uuid.length() == 0);
        Resume r;
        if (isExist) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String[] values = request.getParameterValues(type.name());
            String value = request.getParameter(type.name());
            if (values != null) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        String[] items = value.split("\\n");
                        if (items.length > 0) {
                            List<String> nonBlankValues = Arrays.stream(items)
                                    .filter(val -> !isEmpty(val))
                                    .collect(Collectors.toList());
                            if (!nonBlankValues.isEmpty()) {
                                r.addSection(type, new ListSection(nonBlankValues));
                            } else {
                                r.getSections().remove(type);
                            }
                        } else {
                            r.getSections().remove(type);
                        }
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Company> companies = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String companyName = values[i];
                            if (!isEmpty(companyName)) {
                                List<Company.Period> periodList = new ArrayList<>();
                                String[] startDates = request.getParameterValues(type.name() + i + "startDate");
                                String[] endDates = request.getParameterValues(type.name() + i + "endDate");
                                String[] titles = request.getParameterValues(type.name() + i + "title");
                                String[] descriptions = request.getParameterValues(type.name() + i + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!isEmpty(titles[j])) {
                                        periodList.add(new Company.Period(
                                                LocalDate.parse(startDates[j], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                                LocalDate.parse(endDates[j], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                                titles[j],
                                                descriptions[j])
                                        );
                                    }
                                }
                                companies.add(new Company(new Link(companyName, urls[i]), periodList));
                            }
                        }
                        r.addSection(type, new CompanySection(companies));
                        break;
                }
            }
        }
        if (fullName == null || fullName.trim().length() == 0) {
            response.sendRedirect("/resumes");
        } else {
            if (isExist) {
                storage.save(r);
            } else {
                storage.update(r);
            }
            response.sendRedirect("resume");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = new TextSection("");
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = new ListSection("");
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            CompanySection companySection = (CompanySection) section;
                            List<Company> emptyCompanies = new ArrayList<>();
                            emptyCompanies.add(new Company("", "", new Company.Period()));
                            if (companySection != null) {
                                for (Company company : companySection.getCompanies()) {
                                    List<Company.Period> emptyPeriods = new ArrayList<>();
                                    if (company.getPeriods() != null && !company.getPeriods().isEmpty()) {
                                        emptyPeriods.addAll(company.getPeriods());
                                    } else {
                                        emptyPeriods.add(new Company.Period());
                                    }
                                    emptyCompanies.add(new Company(company.getHomepage(), emptyPeriods));
                                }
                            }
                            section = new CompanySection(emptyCompanies);
                            break;
                    }
                    r.addSection(type, section);
                }
                break;
            case "add":
                r = new Resume();
                for (SectionType type : SectionType.values()) {
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            r.addSection(type, new TextSection(""));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            r.addSection(type, new ListSection(""));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            r.addSection(type, new CompanySection(new Company("", "", new Company.Period())));
                            break;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
