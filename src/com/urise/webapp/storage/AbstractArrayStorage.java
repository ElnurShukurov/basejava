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

    public final void doUpdate(Resume r) {
        int index = getIndex(r.getUuid());
        storage[index] = r;
    }

    public final void doSave(Resume r) {
        int insertIndex = getIndex(r.getUuid());
        if (arraySize == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", r.getUuid());
        } else {
            insertElement(r, insertIndex);
            arraySize++;
        }
    }

    public final Resume doGet(String uuid) {
        int index = getIndex(uuid);
        return storage[index];
    }

    public final void doDelete(String uuid) {
        int index = getIndex(uuid);
        fillDeletedElement(index);
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
        int index = getIndex(searchKey.toString());
        return index >= 0;
    }

    protected abstract void insertElement(Resume r, int insertIndex);

    protected abstract void fillDeletedElement(int index);
}
