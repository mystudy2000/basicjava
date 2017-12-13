package webapp.storage;
import webapp.model.Resume;
import java.util.Arrays;

/*** Array based storage for Resumes */
    public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        for (int i = 0; i < size; i++) storage[i] = null;
        size = 0;

    }

    public void update(Resume r) {
                int index = getIndex(r.getUuid());
                if (index == -1) {
                        System.out.println("Resume " + r.getUuid() + " not exist");
                    } else {
                        storage[index] = r;
                    }
            }

    public void save(Resume r) {
        if (getIndex(r.getUuid()) != -1) {
                        System.out.println("Resume " + r.getUuid() + " already exist");
                    } else if (size >= STORAGE_LIMIT) {
                        System.out.println("Storage overflow");
                    } else {
                        storage[size] = r;
                        size++;
                    }

    }

        public void delete (String uuid){
            int index = getIndex (uuid);
            if (index == -1) {
                System.out.println("Resume " + uuid + " not exist");
            } else {
                storage[index] = storage[size - 1];
                storage[size - 1] = null;
                size--;
            }
        }

        /**
         * @return array, contains only Resumes in storage (without null)
         */

        public Resume[] getAll () {
            return Arrays.copyOfRange(storage, 0, size);

        }

        public int size () {
            return size;
        }

        protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
                }
            }
        return -1;
        }

}


