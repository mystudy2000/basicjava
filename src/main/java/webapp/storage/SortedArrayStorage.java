package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;
import java.util.UUID;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    protected void doSave(Resume r) {
        insertElement(r, getSearchKey(r.getUuid()));
        size++;
    }

    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }

    @Override
    protected void insertElement(Resume r, int index) {
//      http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        if (index<0) {
            int insertIdx = -index - 1;
            System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
            storage[insertIdx] = r;
        }
    }

    @Override
    protected Integer getSearchKey(UUID uuid) {
        Resume Sk = new Resume(uuid,"dummy");
        return Arrays.binarySearch(storage, 0, size, Sk);
    }
}