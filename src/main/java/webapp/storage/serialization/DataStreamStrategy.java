package webapp.storage.serialization;

import webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class DataStreamStrategy implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(String.valueOf(r.getUuid()));
            dos.writeUTF(r.getFullName());
            Map<TypeOfContact, String> contacts = r.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeCollection(dos, r.getSections().entrySet(), entry -> {
                TypeOfSection type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((StringSection) section).getStringSection());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListSection) section).getListSection(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((OrganizationSection) section).getOrganizationSection(), org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());
                            writeCollection(dos, org.getPositions(), position -> {
                                WriteLocalDateToString(dos, position.getStartDate());
                                WriteLocalDateToString(dos, position.getEndDate());
                                dos.writeUTF(position.getPositionName());
                                dos.writeUTF(position.getDescription());
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
            UUID uuid = UUID.fromString(dis.readUTF());
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readItems(dis, () -> {resume.setContact(TypeOfContact.valueOf(dis.readUTF()), dis.readUTF());
            return true;});
            readItems(dis, () -> {
                TypeOfSection sectionType = TypeOfSection.valueOf(dis.readUTF());
                resume.setSection(sectionType, readSection(dis, sectionType));
                return true;
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, TypeOfSection sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new StringSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(
                        readList(dis, () -> new Organization(
                                new Link(dis.readUTF(), dis.readUTF()),
                                readList(dis, () -> new Position(
                                        ReadStringToLocalDate(dis), ReadStringToLocalDate(dis), dis.readUTF(), dis.readUTF()
                                ))
                        )));
            default:
                throw new IllegalStateException();
        }
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Consumer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    private void readItems(DataInputStream dis, Provider reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private <T> List<T> readList(DataInputStream dis, Provider<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private interface Consumer<T> {
        void write(T t) throws IOException;
    }

    private interface Provider<T> {
        T read() throws IOException;
    }

    private void WriteLocalDateToString(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeUTF(localDate.format(ISO_LOCAL_DATE));}

    private LocalDate ReadStringToLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.parse(dis.readUTF(),ISO_LOCAL_DATE); }


}