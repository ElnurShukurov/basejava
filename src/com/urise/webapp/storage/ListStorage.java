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
        int index = getIndex(r.getUuid());
        storage.set(index, r);
    }

    @Override
    public void doSave(Resume r) {
        storage.add(r);
    }

    @Override
    public Resume doGet(String uuid) {
        int index = getIndex(uuid);
        return storage.get(index);
    }

    @Override
    public void doDelete(String uuid) {
        storage.remove(new Resume(uuid));
    }

    @Override
    public Resume[] doGetAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int doSize() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
