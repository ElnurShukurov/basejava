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

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, arraySize);
    }

    public int size() {
        return arraySize;
    }

    protected abstract int getIndex(String uuid);
}
