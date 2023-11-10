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
import java.util.List;
import java.util.UUID;

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
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
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
                        r.addSection(type, new ListSection(value.split("\\n")));
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
        storage.update(r);
        System.out.println("Resume after update: " + storage.get(uuid).getSections());
        response.sendRedirect("resume");
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
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                String resumeUuid = UUID.randomUUID().toString();
                r = new Resume(resumeUuid, "");
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
                            r.addSection(type, new CompanySection(new Company("", "", new Company.Period(LocalDate.now(), LocalDate.now(), "", ""))));
                            break;
                    }
                }
                storage.save(r);
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

