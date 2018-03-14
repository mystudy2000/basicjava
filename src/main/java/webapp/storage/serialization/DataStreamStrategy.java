package webapp.storage.serialization;

import webapp.model.Resume;
import webapp.model.TypeOfContact;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class DataStreamStrategy implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid().toString());
            dos.writeUTF(r.getFullName());
            Map<TypeOfContact, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<TypeOfContact, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            // TODO implements sections
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            UUID uuid = UUID.fromString(dis.readUTF());
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContact(TypeOfContact.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections
            return resume;
        }
    }
}