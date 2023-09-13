package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public Resume generateResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "");
        resume.addContact(ContactType.GITHUB, "");
        resume.addContact(ContactType.STACKOVERFLOW, "");
        resume.addContact(ContactType.HOME_PAGE, "");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и " +
                "корпоративного обучения по Java Web и Enterprise технологиям"));

        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад " +
                "ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA). Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce\n");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB\n");
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        List<Company> experiences = new ArrayList<>();
        Period experiencePeriod1 = new Period("Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.", LocalDate.of(2013, 10, 1), LocalDate.now());
        List<Period> allExperiences = new ArrayList<>();
        allExperiences.add(experiencePeriod1);
        Company javaOnlineProjects = new Company("Java Online Projects", "https://javaops.ru/", allExperiences);
        experiences.add(javaOnlineProjects);
        resume.addSection(SectionType.EXPERIENCE, new CompanySection(SectionType.EXPERIENCE, experiences));

        List<Company> educations = new ArrayList<>();

        Period courseraPeriod = new Period("'Functional Programming Principles in Scala' by Martin Odersky", null, LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1));
        List<Period> allCourseraPeriods = new ArrayList<>();
        allCourseraPeriods.add(courseraPeriod);
        Company coursera = new Company("Coursera", "https://www.coursera.org/course/progfun", allCourseraPeriods);
        educations.add(coursera);

        Period universityPeriod1 = new Period("Инженер (программист Fortran, C)\n", null, LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1));
        Period universityPeriod2 = new Period("Аспирантура (программист С, С++)", null, LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1));
        List<Period> allUniversityPeriods = new ArrayList<>();
        allUniversityPeriods.add(universityPeriod1);
        allUniversityPeriods.add(universityPeriod2);
        Company university = new Company("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "https://itmo.ru/", allUniversityPeriods);
        educations.add(university);

        resume.addSection(SectionType.EDUCATION, new CompanySection(SectionType.EDUCATION, educations));

        System.out.println(resume.getFullName());
        for (ContactType type : ContactType.values()) {
            if (resume.getContact(type) != null) {
                System.out.println(type.getValue() + resume.getContact(type));
            }
        }

        System.out.println("\n" + resume.getSection(SectionType.OBJECTIVE).toString() + "\n");
        System.out.println(resume.getSection(SectionType.PERSONAL).toString() + "\n");
        System.out.println(resume.getSection(SectionType.ACHIEVEMENT).toString() + "\n");
        System.out.println(resume.getSection(SectionType.QUALIFICATIONS).toString() + "\n");

        System.out.println(resume.getSection(SectionType.EXPERIENCE).toString());
        System.out.println(resume.getSection(SectionType.EDUCATION).toString());

        return resume;
    }
}
