package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListStorage extends AbstractStorage<Integer> {

    private ArrayList<Resume> resumeList = new ArrayList<>();

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
    protected boolean isExist(Integer index) {
        return index != -1;
    }

    @Override
    protected void doUpdate(Integer SK, Resume r) {
        resumeList.set(SK, r);
    }

    @Override
    protected void doSave(Resume r, Integer SK) {
        resumeList.add(r);
    }

    @Override
    protected Resume doGet(Integer SK) {
        return resumeList.get(SK);
    }

    @Override
    protected void doDelete(Integer SK) {
        resumeList.remove((int) SK);
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