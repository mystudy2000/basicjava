package webapp.storage;

import webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private Map<UUID, Resume> resumeMap = new HashMap<>();

    @Override
    protected Resume getSearchKey(UUID uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected boolean isExist(Object r) {
        return r!=null;
    }

    @Override
    protected void doUpdate(Object rToMap, Resume rFromMap) {
        Resume rUpdate = (Resume) rToMap;
        resumeMap.put(rUpdate.getUuid(), rFromMap);
    }

    @Override
    protected void doSave(Resume r) {
        resumeMap.put(r.getUuid(),r);
    }

    @Override
    protected Resume doGet(Object r) {
        Resume rGet = (Resume) r;
        return resumeMap.get(rGet.getUuid());
    }

    @Override
    protected void doDelete(Object r) {
        Resume rDel = (Resume) r;
        resumeMap.remove(rDel.getUuid());
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