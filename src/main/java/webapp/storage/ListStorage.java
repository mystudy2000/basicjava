package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
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
    protected boolean isExist(Object index) { return (Integer)index!=-1;}

    @Override
    protected void doUpdate(Object SK, Resume r) {
        resumeList.set((Integer)SK, r);
    }

    @Override
    protected void doSave(Resume r, Object SK) {
        resumeList.add(r);
    }

    @Override
    protected Resume doGet(Object SK) {
        return resumeList.get((Integer)SK);
    }

    @Override
    protected void doDelete(Object SK) {
        int index = (Integer) SK;
        resumeList.remove(index);
        resumeList.trimToSize();
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public List<Resume> doGetAll() {
        return new ArrayList<>(resumeList);
    }

    @Override
    public int size() {
        return resumeList.size();
    }
}