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
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("There is no " + r.getUuid() + " resume in the storage to update");
        } else {
            storage[index] = new Resume();
            storage[index].setUuid("Updated resume " + r.getUuid());
        }
    }

    public void save(Resume r) {
        if (arraySize == STORAGE_LIMIT) {
            System.out.println("Storage if full");
        } else if (getIndex(r.getUuid()) >= 0) {
            System.out.println("Storage already contains resume " + r.getUuid());
        } else {
            storage[arraySize] = r;
            arraySize++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            Resume resume = storage[index];
            return resume;
        } else {
            System.out.println("There is no " + uuid + " resume in the storage to get");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[arraySize - 1];
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
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
