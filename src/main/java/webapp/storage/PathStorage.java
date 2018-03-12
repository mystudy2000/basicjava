package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;
import webapp.storage.serialization.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private Path dir;
    private SerializationStrategy serializationStrategy;

    PathStorage(String directory, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");

        this.dir = Paths.get(directory);
        this.serializationStrategy = serializationStrategy;

        if (!Files.isDirectory(dir) || !Files.isWritable(dir)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(dir).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Clear: Path delete error", e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(dir).count();
        } catch (IOException e) {
            throw new StorageException("Size: Directory read error", e);
        }
    }

    @Override
    protected Path getSearchKey(UUID uuid) {
        return dir.resolve(String.valueOf(uuid));
    }

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            serializationStrategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("doUpdate: Path write error " + r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("doSave: Couldn't create path " + path, e);
        }
        doUpdate(path, r);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("doGet: Path read error" + path, e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        if (!Files.exists(path)) {
            throw new StorageException("doDelete: File " + path + " is not exist");
        } else if (Files.isDirectory(path)) {
            throw new StorageException("doDelete: File " + path + " is directory");
        } else try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("doDelete: Path delete error" + path, e);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        try {
            return Files.list(dir).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("doGetAll: Directory not found ", e);
        }
    }

}
