package webapp.storage;

import webapp.model.Resume;

import java.util.*;

public class MapUUIDStorage extends AbstractStorage <UUID> {

    private Map<UUID, Resume> resumeMap = new HashMap<>();

    @Override
    protected UUID getSearchKey(UUID uuid) { return uuid;}

    @Override
    protected boolean isExist(UUID uuid) {
        return resumeMap.containsKey(uuid);
    }

    @Override
    protected void doUpdate(UUID SK, Resume r) {
        resumeMap.put(SK,r);
    }

    @Override
    protected void doSave(Resume r, UUID SK) {
        resumeMap.put(SK,r);
    }

    @Override
    protected Resume doGet(UUID uuid) {return resumeMap.get(uuid); }

    @Override
    protected void doDelete(UUID uuid) {resumeMap.remove(uuid); }

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