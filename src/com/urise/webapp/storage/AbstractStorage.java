package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected Object searchKey;

    public void clear() {
        doClear();
    }

    public void update(Resume r) {
        searchKey = getIndex(r.getUuid());
        if (!isExist(r)) {
            getNotExistingSearchKey(searchKey, r.getUuid());
        } else {
            doUpdate(r);
        }
    }

    public void save(Resume r) {
        searchKey = getIndex(r.getUuid());
        if (isExist(r)) {
            getExistingSearchKey(searchKey, r.getUuid());
        } else {
            doSave(r);
        }
    }

    public Resume get(String uuid) {
        searchKey = getIndex(uuid);
        if (!isExist(new Resume(uuid))) {
            getNotExistingSearchKey(searchKey, uuid);
        }
        return doGet(uuid);
    }

    public void delete(String uuid) {
        searchKey = getIndex(uuid);
        if (!isExist(new Resume(uuid))) {
            getNotExistingSearchKey(searchKey, uuid);
        } else {
            doDelete(uuid);
        }
    }

    public Resume[] getAll() {
        return doGetAll();
    }

    public int size() {
        return doSize();
    }

    private Object getExistingSearchKey(Object searchKey, String uuid) {
        if (isExist(new Resume(uuid))) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    private Object getNotExistingSearchKey(Object searchKey, String uuid) {
        if (!isExist(new Resume(uuid))) {
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract int getIndex(String uuid);

    protected abstract void doClear();

    protected abstract void doUpdate(Resume r);

    protected abstract void doSave(Resume r);

    protected abstract Resume doGet(String uuid);

    protected abstract void doDelete(String uuid);

    protected abstract Resume[] doGetAll();

    protected abstract int doSize();
}
