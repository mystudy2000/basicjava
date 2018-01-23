package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.UUID;

public class ListStorage extends AbstractStorage {

    private ArrayList <Resume> resumeList = new ArrayList<>();

    @Override
    protected Integer getSearchKey(UUID uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(UUID uuid) { return getSearchKey(uuid) != -1;}

    @Override
    protected void doUpdate(Resume r, UUID uuid) {
        resumeList.set(getSearchKey(uuid), r);
    }

    @Override
    protected void doSave(Resume r) {
        resumeList.add(r);
    }

    @Override
    protected Resume doGet(UUID uuid) {
        return resumeList.get(getSearchKey(uuid));
    }

    @Override
    protected void doDelete(UUID uuid) {
        resumeList.remove(getSearchKey(uuid).intValue());
        resumeList.trimToSize();
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(new Resume[resumeList.size()]);
    }

    @Override
    public int size() {
        return resumeList.size();
    }
}