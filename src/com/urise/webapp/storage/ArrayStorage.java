package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    protected Object searchKey(String uuid) {
        for (int i = 0; i < arraySize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume r, int insertIndex) {
        storage[arraySize] = r;
    }

    @Override
    protected void fillDeletedElement(int index) {
        storage[index] = storage[arraySize - 1];
    }
}
