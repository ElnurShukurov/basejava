package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResumeTestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final String NAME_1 = "Name1";
    public static final String NAME_2 = "Name2";
    public static final String NAME_3 = "Name3";
    public static final String NAME_4 = "Name4";

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = generateResume(UUID_1, NAME_1);
        R2 = generateResume(UUID_2, NAME_2);
        R3 = generateResume(UUID_3, NAME_3);
        R4 = generateResume(UUID_4, NAME_4);
        R1.addContact(ContactType.EMAIL, "elnur@mail.ru");
        R1.addContact(ContactType.PHONE, "123-45-66");
        R2.addContact(ContactType.SKYPE, "@skype");
        R2.addContact(ContactType.PHONE, "654-32-11");
    }

    public static Resume generateResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "");
        resume.addContact(ContactType.GITHUB, "");
        resume.addContact(ContactType.STACKOVERFLOW, "");
        resume.addContact(ContactType.HOME_PAGE, "");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и " +
                "корпоративного обучения по Java Web и Enterprise технологиям."));

        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад " +
                "ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет", "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA). Организация онлайн стажировок и ведение проектов. Более 3500 выпускников."));

        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", "Version control: Subversion, Git, Mercury, ClearCase, Perforce", "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB"));

        List<Company> experiences = new ArrayList<>();
        Company javaOnlineProjects = new Company("Java Online Projects", "https://javaops.ru/", new Company.Period(LocalDate.of(2013, 10, 1), LocalDate.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        Company wrike = new Company("Wrike", "https://www.wrike.com/", new Company.Period(LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        experiences.add(javaOnlineProjects);
        experiences.add(wrike);
        resume.addSection(SectionType.EXPERIENCE, new CompanySection(experiences));

        List<Company> educations = new ArrayList<>();
        Company coursera = new Company("Coursera", "https://www.coursera.org/course/progfun", new Company.Period(LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1), "'Functional Programming Principles in Scala' by Martin Odersky", null));
        Company university = new Company("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "https://itmo.ru/", new Company.Period(LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1), "Инженер (программист Fortran, C)", null), new Company.Period(LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1), "Аспирантура (программист С, С++)", null));
        educations.add(coursera);
        educations.add(university);
        resume.addSection(SectionType.EDUCATION, new CompanySection(educations));

        return resume;
    }
}
