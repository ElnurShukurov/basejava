package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void clear() {
        doClear();
    }

    public void update(Resume r) {
        getNotExistingSearchKey(r.getUuid());
        doUpdate(r);
    }

    public void save(Resume r) {
        getExistingSearchKey(r.getUuid());
        doSave(r);
    }

    public Resume get(String uuid) {
        getNotExistingSearchKey(uuid);
        return doGet(uuid);
    }

    public void delete(String uuid) {
        getNotExistingSearchKey(uuid);
        doDelete(uuid);
    }

    public Resume[] getAll() {
        return doGetAll();
    }

    public int size() {
        return doSize();
    }

    private Object getExistingSearchKey(String uuid) {
        if (isExist(new Resume(uuid))) {
            throw new ExistStorageException(uuid);
        } else {
            Object searchKey = new Resume(uuid);
            return searchKey;
        }
    }

    private Object getNotExistingSearchKey(String uuid) {
        if (!isExist(new Resume(uuid))) {
            throw new NotExistStorageException(uuid);
        } else {
            Object searchKey = new Resume(uuid);
            return searchKey;
        }
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object searchKey(String uuid);

    protected abstract void doClear();

    protected abstract void doUpdate(Resume r);

    protected abstract void doSave(Resume r);

    protected abstract Resume doGet(String uuid);

    protected abstract void doDelete(String uuid);

    protected abstract Resume[] doGetAll();

    protected abstract int doSize();
}
