package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object searchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, arraySize, searchKey);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, arraySize - insertionIndex);
        storage[insertionIndex] = r;
    }

    @Override
    protected void fillDeletedElement(int index) {
        int elementsToShift = arraySize - index - 1;
        if (elementsToShift > 0) {
            System.arraycopy(storage, index + 1, storage, index, elementsToShift);
        }
    }
}
