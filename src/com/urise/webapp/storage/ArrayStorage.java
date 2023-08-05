package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int arraySize = 0;

    public void clear() {
        Arrays.fill(storage, 0, arraySize, null);
        arraySize = 0;
    }

    public void update(Resume r) {
        if (getIndex(r.getUuid()) < 0) {
            System.out.println("There is no " + r.getUuid() + " resume in the storage to update");
        } else {
            r.setUuid("Updated resume " + r.getUuid());
        }
    }

    public void save(Resume r) {
        if (arraySize == 10000) {
            System.out.println("Storage if full");
        } else if (getIndex(r.getUuid()) >= 0) {
            System.out.println("Storage already contains resume " + r.getUuid());
        } else {
            storage[arraySize] = r;
            arraySize++;
        }
    }

    public Resume get(String uuid) {
        if (getIndex(uuid) >= 0) {
            Resume resume = storage[getIndex(uuid)];
            return resume;
        } else {
            System.out.println("There is no " + uuid + " resume in the storage to get");
        }
        return null;
    }

    public void delete(String uuid) {
        if (getIndex(uuid) >= 0) {
            storage[getIndex(uuid)] = storage[arraySize - 1];
            arraySize--;
        } else {
            System.out.println("There is no " + uuid + " resume in the storage to delete");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, arraySize);
    }

    public int size() {
        return arraySize;
    }

    public int getIndex(String uuid) {
        for (int i = 0; i < arraySize; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
