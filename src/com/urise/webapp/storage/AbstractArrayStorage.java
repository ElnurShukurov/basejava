package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int arraySize = 0;

    public void clear() {
        Arrays.fill(storage, 0, arraySize, null);
        arraySize = 0;
    }

    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("There is no " + r.getUuid() + " resume in the storage to update");
        } else {
            storage[index] = new Resume();
            storage[index].setUuid("Updated resume " + r.getUuid());
        }
    }

    public final void save(Resume r) {
        int insertIndex = getIndex(r.getUuid());
        if (arraySize == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (insertIndex >= 0) {
            System.out.println("Storage already contains resume " + r.getUuid());
        } else {
            insertElement(r, insertIndex);
            arraySize++;
        }
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("There is no " + uuid + " resume in the storage to get");
        }
        return null;
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("There is no " + uuid + " resume in the storage to delete");
        } else {
            fillDeletedElement(index);
            storage[arraySize - 1] = null;
            arraySize--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, arraySize);
    }

    public int size() {
        return arraySize;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume r, int insertIndex);

    protected abstract void fillDeletedElement(int index);
}
