package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void clear() {
        doClear();
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            doUpdate(r);
        }
    }

    public void save(Resume r) {
        int insertIndex = getIndex(r.getUuid());
        if (insertIndex >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(r);
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return doGet(uuid);
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
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

    protected abstract int getIndex(String uuid);

    protected abstract void doClear();

    protected abstract void doUpdate(Resume r);

    protected abstract void doSave(Resume r);

    protected abstract Resume doGet(String uuid);

    protected abstract void doDelete(String uuid);

    protected abstract Resume[] doGetAll();

    protected abstract int doSize();
}
