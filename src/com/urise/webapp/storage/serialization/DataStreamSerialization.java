package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerialization implements StreamSerializationStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeCollection(r.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeCollection(r.getSections().entrySet(), dos, entry -> {
                Section section = entry.getValue();
                SectionType sectionType = entry.getKey();

                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(((ListSection) section).getItems(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(((CompanySection) section).getCompanies(), dos, company -> {
                            dos.writeUTF(company.getHomepage().getName());
                            dos.writeUTF(company.getHomepage().getUrl());
                            writeCollection(company.getPeriods(), dos, period -> {
                                dos.writeUTF(period.getTitle());
                                String description = period.getDescription();
                                dos.writeBoolean(description != null);
                                if (description != null) {
                                    dos.writeUTF(description);
                                }
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String contactValue = dis.readUTF();
                resume.addContact(contactType, contactValue);
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        String text = dis.readUTF();
                        resume.addSection(sectionType, new TextSection(text));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int itemsSize = dis.readInt();
                        List<String> items = new ArrayList<>();
                        for (int j = 0; j < itemsSize; j++) {
                            items.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(items));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int companiesSize = dis.readInt();
                        List<Company> companies = new ArrayList<>();
                        for (int j = 0; j < companiesSize; j++) {
                            String companyName = dis.readUTF();
                            String companyUrl = dis.readUTF();
                            int periodsSize = dis.readInt();
                            List<Company.Period> periods = new ArrayList<>();
                            for (int k = 0; k < periodsSize; k++) {
                                String periodTitle = dis.readUTF();
                                boolean hasValue = dis.readBoolean();
                                String periodDescription = hasValue ? dis.readUTF() : null;
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                periods.add(new Company.Period(startDate, endDate, periodTitle, periodDescription));
                            }
                            companies.add(new Company(new Link(companyName, companyUrl), periods));
                        }
                        resume.addSection(sectionType, new CompanySection(companies));
                        break;
                }
            }

            return resume;
        }
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dos, WriterAction<T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            action.write(item);
        }
    }

    @FunctionalInterface
    private interface WriterAction<T> {
        void write(T t) throws IOException;
    }

}