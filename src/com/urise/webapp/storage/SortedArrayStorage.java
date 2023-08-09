package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        int insertIndex = getIndex(r.getUuid());
        if (arraySize == STORAGE_LIMIT) {
            System.out.println("Storage if full");
        } else if (insertIndex >= 0) {
            System.out.println("Storage already contains resume " + r.getUuid());
        } else {
            insertIndex = -insertIndex - 1;
            for (int i = arraySize - 1; i >= insertIndex; i--) {
                storage[i + 1] = storage[i];
            }
            storage[insertIndex] = r;
            arraySize++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            for (int i = index; i < arraySize - 1; i++) {
                storage[i] = storage[i + 1];
            }
            storage[arraySize - 1] = null;
            arraySize--;
        } else {
            System.out.println("There is no " + uuid + " resume in the storage to delete");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, arraySize, searchKey);
    }
}
