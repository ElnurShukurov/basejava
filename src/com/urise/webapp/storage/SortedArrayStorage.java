package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, arraySize, searchKey);
    }

    @Override
    protected void insertElement(Resume r, int insertIndex) {
        insertIndex = -insertIndex - 1;
        if (arraySize - insertIndex >= 0) {
            System.arraycopy(storage, insertIndex, storage, insertIndex + 1, arraySize - insertIndex);
        }
        storage[insertIndex] = r;
        arraySize++;
    }

    @Override
    protected void removeElement(int index) {
        if (arraySize - index - 1 >= 0) {
            System.arraycopy(storage, index + 1, storage, index, arraySize - index - 1);
        }
        storage[arraySize - 1] = null;
        arraySize--;
    }
}
