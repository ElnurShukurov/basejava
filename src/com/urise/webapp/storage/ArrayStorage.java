package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int arraySize = 0;

    public void clear() {
        Arrays.fill(storage, 0, arraySize, null);
        arraySize = 0;
    }

    public void update(Resume r) {
        boolean present = false;
        for (int i = 0; i < arraySize; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                present = true;
            }
        }

        if (present == false) {
            System.out.println("ERROR");
        } else if (arraySize > 0) {
            r.setUuid("Updated resume " + r.getUuid());
        }
    }

    public void save(Resume r) {
        boolean present = false;
        if (arraySize > 0) {
            for (int i = 0; i < arraySize; i++) {
                if (storage[i].getUuid().equals(r.getUuid())) {
                    present = true;
                }
            }
        }
        if (present == true) {
            System.out.println("ERROR");
        } else {
            storage[arraySize] = r;
            arraySize++;
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < arraySize; i++) {
            Resume resume = storage[i];
            if (resume.toString().equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < arraySize; i++) {
            if (storage[i].toString().equals(uuid)) {
                storage[i] = storage[arraySize - 1];
                arraySize--;
                break;
            }
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
}
