package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 100000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int arraySize = 0;

    public void doClear() {
        Arrays.fill(storage, 0, arraySize, null);
        arraySize = 0;
    }

    public final void doUpdate(Resume r, Object searchKey) {
        storage[(Integer) searchKey] = r;
    }

    public final void doSave(Resume r, Object searchKey) {
        if (arraySize == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", r.getUuid());
        } else {
            insertElement(r, (int) searchKey);
            arraySize++;
        }
    }

    public final Resume doGet(String uuid, Object searchKey) {
        return storage[(int) searchKey];
    }

    public final void doDelete(String uuid, Object searchKey) {
        fillDeletedElement((int) searchKey);
        storage[arraySize - 1] = null;
        arraySize--;
    }

    public Resume[] doGetAll() {
        return Arrays.copyOfRange(storage, 0, arraySize);
    }

    public int doSize() {
        return arraySize;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    protected abstract void insertElement(Resume r, int insertIndex);

    protected abstract void fillDeletedElement(int index);
}
