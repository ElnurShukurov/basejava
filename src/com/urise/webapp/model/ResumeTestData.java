package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        ContactType[] contactTypes = ContactType.values();
        for (ContactType type : contactTypes) {
            switch (type) {
                case PHONE:
                    resume.addContact(type, new Contact(type, "+7(921) 855-0482"));
                    break;
                case SKYPE:
                    resume.addContact(type, new Contact(type, "skype:grigory.kislin"));
                    break;
                case EMAIL:
                    resume.addContact(type, new Contact(type, "gkislin@yandex.ru"));
                    break;
                case LINKEDIN:
                case GITHUB:
                case STACKOVERFLOW:
                    resume.addContact(type, new Contact(type, ""));
                    break;
                default:
                    break;
            }
        }

        SectionType[] sectionTypes = SectionType.values();
        for (SectionType type : sectionTypes) {
            switch (type) {
                case OBJECTIVE:
                    resume.addSection(type, new TextSection(type, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
                    break;
                case PERSONAL:
                    resume.addSection(type, new TextSection(type, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.\n"));
                    break;
                case ACHIEVEMENT:
                    resume.addSection(type, new ListSection(type, Arrays.asList(
                            "Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
                            "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников."
                    )));
                    break;
                case QUALIFICATIONS:
                    resume.addSection(type, new ListSection(type, Arrays.asList(
                            "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                            "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                            "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB"
                    )));
                    break;
                case EXPERIENCE:
                    List<Company> companies = new ArrayList<>();
                    List<Period> periods = new ArrayList<>();

                    periods.add(new Period(
                            YearMonth.of(2013, 3),
                            YearMonth.now(),
                            "Автор проекта.",
                            "Создание, организация и проведение Java онлайн проектов и стажировок."
                    ));
                    Company company = new Company("Java Online Projects", "https://javaops.ru/", periods);
                    companies.add(company);

                    resume.addSection(type, new CompanySection(type, companies));
                    break;
                case EDUCATION:
                    List<Period> educations = new ArrayList<>();
                    educations.add(new Period(
                            YearMonth.of(2018, 9),
                            YearMonth.of(2021, 6),
                            "Coursera",
                            "\n" +
                                    "'Functional Programming Principles in Scala' by Martin Odersky\n"
                    ));
                    educations.add(new Period(
                            YearMonth.of(2021, 9),
                            YearMonth.now(),
                            "Luxoft",
                            "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'\n"
                    ));
                    resume.addSection(type, new CompanySection(type, Arrays.asList(new Company("", "", educations))));
                    break;
                default:
                    break;
            }
        }

        System.out.println(resume.getFullName());
        ContactType[] allContacts = ContactType.values();
        for (ContactType type : allContacts) {
            Contact contact = resume.getContact(type);
            if (contact != null) {
                System.out.println(contact.getType().getValue() + contact.getValue());
            }
        }
        System.out.println();
        SectionType[] allSections = SectionType.values();
        for (SectionType type : allSections) {
            Section section = resume.getSection(type);
            System.out.println(section.getType().getTitle() + ":");
            System.out.println(section.getContent() + "\n");
        }
    }
}

