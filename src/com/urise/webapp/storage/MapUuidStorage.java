package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    protected final Map<String, Resume> map = new TreeMap<>();

    @Override
    protected boolean isExist(Object uuid) {
        return map.containsKey((String) uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doClear() {
        map.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object uuid) {
        map.put((String) uuid, r);
    }

    @Override
    protected void doSave(Resume r, Object uuid) {
        map.put((String) uuid, r);
    }

    @Override
    protected Resume doGet(Object uuid) {
        return map.get((String) uuid);
    }

    @Override
    protected void doDelete(Object uuid) {
        map.remove((String) uuid);
    }

    @Override
    protected int doSize() {
        return map.size();
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(map.values());
    }
}
