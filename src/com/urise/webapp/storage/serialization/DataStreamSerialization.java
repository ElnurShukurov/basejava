package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerialization implements StreamSerializationStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section section = entry.getValue();
                dos.writeUTF(section.getClass().getSimpleName());

                if (section instanceof TextSection) {
                    dos.writeUTF(((TextSection) section).getContent());
                } else if (section instanceof ListSection) {
                    List<String> items = ((ListSection) section).getItems();
                    dos.writeInt(items.size());
                    for (String item : items) {
                        dos.writeUTF(item);
                    }
                } else if (section instanceof CompanySection) {
                    List<Company> companies = ((CompanySection) section).getCompanies();
                    dos.writeInt(companies.size());
                    for (Company company : companies) {
                        dos.writeUTF(company.getHomepage().getName());
                        dos.writeUTF(company.getHomepage().getUrl());
                        dos.writeInt(company.getPeriods().size());
                        for (Company.Period period : company.getPeriods()) {
                            dos.writeUTF(period.getTitle());
                            String description = period.getDescription();
                            dos.writeBoolean(description != null);
                            if (description != null) {
                                dos.writeUTF(description);
                            }
                            dos.writeUTF(period.getStartDate().toString());
                            dos.writeUTF(period.getEndDate().toString());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int numSections = dis.readInt();
            for (int i = 0; i < numSections; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                String sectionClassName = dis.readUTF();
                Section section = null;

                if (TextSection.class.getSimpleName().equals(sectionClassName)) {
                    section = new TextSection(dis.readUTF());
                } else if (ListSection.class.getSimpleName().equals(sectionClassName)) {
                    int numItems = dis.readInt();
                    List<String> items = new ArrayList<>();
                    for (int j = 0; j < numItems; j++) {
                        items.add(dis.readUTF());
                    }
                    section = new ListSection(items);
                } else if (CompanySection.class.getSimpleName().equals(sectionClassName)) {
                    int numCompanies = dis.readInt();
                    List<Company> companies = new ArrayList<>();
                    for (int j = 0; j < numCompanies; j++) {
                        String companyName = dis.readUTF();
                        String companyUrl = dis.readUTF();
                        int numPeriods = dis.readInt();
                        List<Company.Period> periods = new ArrayList<>();
                        for (int k = 0; k < numPeriods; k++) {
                            String periodTitle = dis.readUTF();
                            boolean hasValue = dis.readBoolean();
                            String periodDescription = hasValue ? dis.readUTF() : null;
                            LocalDate startDate = LocalDate.parse(dis.readUTF());
                            LocalDate endDate = LocalDate.parse(dis.readUTF());
                            periods.add(new Company.Period(startDate, endDate, periodTitle, periodDescription));
                        }
                        companies.add(new Company(new Link(companyName, companyUrl), periods));
                    }
                    section = new CompanySection(companies);
                }
                resume.addSection(sectionType, section);
            }
            return resume;
        }
    }

}