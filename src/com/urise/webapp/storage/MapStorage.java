package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey.toString());
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage.put(searchKey.toString(), r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.put(searchKey.toString(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected Resume[] doGetAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected int doSize() {
        return storage.size();
    }

}
