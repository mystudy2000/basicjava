package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

public class SortedArrayStorage extends AbstractArrayStorage{


    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);


    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }

    @Override
    protected void insertElement(Resume r, int index) {
        storage[index] = r;
        Arrays.sort(storage,0,size+1, RESUME_COMPARATOR);
    }

    @Override
    protected Integer getSearchKey(UUID uuid) {
        Resume Sk = new Resume(uuid,"dummy");
        return Arrays.binarySearch(storage, 0, size, Sk, RESUME_COMPARATOR);

    }
}