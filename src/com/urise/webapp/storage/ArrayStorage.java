package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int arraySize = 0;
    boolean present = false;

    public void clear() {
        Arrays.fill(storage, 0, arraySize, null);
        arraySize = 0;
    }

    public void update(Resume r) {
        for (int i = 0; i < arraySize; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                present = true;
            }
        }
        if (present == false) {
            System.out.println("There is no " + r.getUuid() + " resume in the storage update");
        } else if (arraySize > 0) {
            r.setUuid("Updated resume " + r.getUuid());
            present = false;
        }
    }

    public void save(Resume r) {
        if (arraySize > 0) {
            for (int i = 0; i < arraySize; i++) {
                if (storage[i].getUuid().equals(r.getUuid())) {
                    present = true;
                }
            }
        }
        if (present == true) {
            System.out.println("Storage already contains resume " + r.getUuid());
            present = false;
        } else {
            storage[arraySize] = r;
            arraySize++;
        }
        if (arraySize == 10000) {
            System.out.println("Storage if full");
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < arraySize; i++) {
            Resume resume = storage[i];
            if (resume.toString().equals(uuid)) {
                return resume;
            }

        }
        if (present == false) {
            System.out.println("There is no " + uuid + " resume in the storage get");
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < arraySize; i++) {
            if (storage[i].toString().equals(uuid)) {
                present = true;
                storage[i] = storage[arraySize - 1];
                arraySize--;
                break;
            }
        }
        if (present == false) {
            System.out.println("There is no " + uuid + " resume in the storage to delete");
        }
        present = false;
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
}
