package webapp.storage;

import webapp.model.Resume;

import java.util.*;

public class MapUUIDStorage extends AbstractStorage {

    private Map<UUID, Resume> resumeMap = new HashMap<>();

    @Override
    protected UUID getSearchKey(UUID uuid) { return uuid;}

    @Override
    protected boolean isExist(Object uuid) {
        return resumeMap.containsKey(uuid);
    }

    @Override
    protected void doUpdate(Object SK, Resume r) {
        resumeMap.put((UUID)SK,r);
    }

    @Override
    protected void doSave(Resume r) {
        resumeMap.put(r.uuid,r);
    }

    @Override
    protected Resume doGet(Object uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected void doDelete(Object uuid) {
        resumeMap.remove(uuid);
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public List<Resume> doGetAll() {return new ArrayList<>(resumeMap.values());}

    @Override
    public int size() {
        return resumeMap.size();
    }

}