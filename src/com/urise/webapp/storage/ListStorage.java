package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    public void doClear() {
        storage.clear();
    }

    @Override
    public void doUpdate(Resume r) {
        int index = (int) searchKey(r.getUuid());
        storage.set(index, r);
    }

    @Override
    public void doSave(Resume r) {
        storage.add(r);
    }

    @Override
    public Resume doGet(String uuid) {
        int index = (int) searchKey(uuid);
        return storage.get(index);
    }

    @Override
    public void doDelete(String uuid) {
        int index = (int) searchKey(uuid);
        if (index >= 0) {
            storage.remove(index);
        }
    }

    @Override
    public Resume[] doGetAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int doSize() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.contains(searchKey);
    }

    @Override
    protected Object searchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
