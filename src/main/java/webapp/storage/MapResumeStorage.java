package webapp.storage;

import webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private Map<UUID, Resume> resumeMap = new HashMap<>();

    @Override
    protected Resume getSearchKey(UUID uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected boolean isExist(Resume r) {
        return r != null;
    }

    @Override
    protected void doUpdate(Resume rToMap, Resume rFromMap) {
        resumeMap.put(rToMap.getUuid(), rFromMap);
    }

    @Override
    protected void doSave(Resume r, Resume SK) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume r) {
        return r;
    }

    @Override
    protected void doDelete(Resume r) {
        resumeMap.remove(r.getUuid());
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public List<Resume> doGetAll() {
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public int size() {
        return resumeMap.size();
    }

}