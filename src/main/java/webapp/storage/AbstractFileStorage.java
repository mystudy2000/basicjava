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
    public int size() throws StorageException {
        String[] list = dir.list();
        if (list == null) {
            throw new StorageException("Directory does not exist", 1100);
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
    protected void doUpdate(File file, Resume r) throws StorageException {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("Error for file write " + file.getName(), 1200);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) throws StorageException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), 1300);
        }
        doUpdate(file, r);
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected Resume doGet(File file) throws StorageException {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("File read error " + file.getName(), 1400);
        }
    }

    @Override
    protected void doDelete(File file) throws StorageException {
        if (!file.delete()) {
            throw new StorageException("File delete error " + file.getName(), 1500);
        }
    }

    protected List<Resume> doCopyAll() throws StorageException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new StorageException("Directory is not exist", 1600);
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }
}