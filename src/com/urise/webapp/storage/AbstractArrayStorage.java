package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 100000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int arraySize = 0;

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertElement(Resume r, int insertIndex);

    protected abstract void fillDeletedElement(int index);

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    public void doClear() {
        Arrays.fill(storage, 0, arraySize, null);
        arraySize = 0;
    }

    @Override
    public void doUpdate(Resume r, Object index) {
        storage[(Integer) index] = r;
    }

    @Override
    public void doSave(Resume r, Object index) {
        if (arraySize == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", r.getUuid());
        } else {
            insertElement(r, (int) index);
            arraySize++;
        }
    }

    @Override
    public Resume doGet(Object index) {
        return storage[(int) index];
    }

    @Override
    public void doDelete(Object index) {
        fillDeletedElement((int) index);
        storage[arraySize - 1] = null;
        arraySize--;
    }

    @Override
    public int doSize() {
        return arraySize;
    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.stream(storage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
