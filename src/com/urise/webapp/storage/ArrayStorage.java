package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[arraySize - 1];
            storage[arraySize - 1] = null;
            arraySize--;
        } else {
            System.out.println("There is no " + uuid + " resume in the storage to delete");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    protected int getIndex(String uuid) {
        for (int i = 0; i < arraySize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
