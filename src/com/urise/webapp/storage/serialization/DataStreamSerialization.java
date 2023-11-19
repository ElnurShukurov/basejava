package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

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
                                dos.writeUTF(period.getDescription());
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

            Collection<Map.Entry<ContactType, String>> contacts = readCollection(dis, () -> {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String contactValue = dis.readUTF();
                return new AbstractMap.SimpleEntry<>(contactType, contactValue);
            });
            contacts.forEach(entry -> resume.setContact(entry.getKey(), entry.getValue()));

            Collection<Map.Entry<SectionType, Section>> sections = readCollection(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        String text = dis.readUTF();
                        return new AbstractMap.SimpleEntry<>(sectionType, new TextSection(text));
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> items = new ArrayList<>(readCollection(dis, dis::readUTF));
                        return new AbstractMap.SimpleEntry<>(sectionType, new ListSection(items));
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Company> companies = (List<Company>) readCollection(dis, () -> {
                            String companyName = dis.readUTF();
                            String companyUrl = dis.readUTF();
                            List<Company.Period> periods = (List<Company.Period>) readCollection(dis, () -> {
                                String periodTitle = dis.readUTF();
                                String periodDescription = dis.readUTF();
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                return new Company.Period(startDate, endDate, periodTitle, periodDescription);
                            });
                            return new Company(new Link(companyName, companyUrl), periods);
                        });
                        return new AbstractMap.SimpleEntry<>(sectionType, new CompanySection(companies));
                    default:
                        throw new IllegalStateException("Unsupported section type: " + sectionType);
                }
            });
            sections.forEach(entry -> resume.setSection(entry.getKey(), entry.getValue()));
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

    private <T> Collection<T> readCollection(DataInputStream dis, ReaderAction<T> action) throws IOException {
        int size = dis.readInt();
        List<T> collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T item = action.read();
            collection.add(item);
        }
        return collection;
    }

    @FunctionalInterface
    private interface ReaderAction<T> {
        T read() throws IOException;
    }

}