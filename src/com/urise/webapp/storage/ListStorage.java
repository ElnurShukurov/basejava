package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> list = new ArrayList<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void doClear() {
        list.clear();
    }

    @Override
    public void doUpdate(Resume r, Object searchKey) {
        list.set((Integer) searchKey, r);
    }

    @Override
    public void doSave(Resume r, Object searchKey) {
        list.add(r);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return list.get((int) searchKey);
    }

    @Override
    public void doDelete(Object searchKey) {
        list.remove(((Integer) searchKey).intValue());
    }

    @Override
    public Resume[] doGetAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public int doSize() {
        return list.size();
    }

}
