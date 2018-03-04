package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File dir;

    protected AbstractFileStorage(File directory) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory format");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable or writable");
        }
        this.dir = directory;
    }

    @Override
    public int size() {
        String[] list = dir.list();
        if (list == null) {
            throw new StorageException("Directory " + list + " does not exist");
        }
        return list.length;
    }


    @Override
    public void clear() throws StorageException {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    protected File getSearchKey(UUID uuid) {
        return new File(dir, uuid.toString());
    }

    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            doWrite(r, file);
        } catch (RuntimeException e) {

        }
    }


    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), e);
        }
        doUpdate(file, r);
    }

    protected abstract void doWrite(Resume r, File file);

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("File read error " + file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error " + file.getName());
        }
    }

    protected List<Resume> doCopyAll() {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new StorageException("Directory " + files + " is not exist");
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }
}