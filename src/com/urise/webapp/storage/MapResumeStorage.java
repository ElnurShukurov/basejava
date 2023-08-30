package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected boolean isExist(Object resume) {
        return resume != null && map.containsValue(resume);
    }

    @Override
    protected Resume getSearchKey(String resume) {
        return map.get(resume);
    }

    @Override
    protected void doClear() {
        map.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object resume) {
        map.put(resume.toString(), r);
    }

    @Override
    protected void doSave(Resume r, Object resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object resume) {
        return map.get(resume.toString());
    }

    @Override
    protected void doDelete(Object resume) {
        map.remove(resume.toString());
    }

    @Override
    protected List<Resume> doGetAllSorted() {
        List<Resume> sortedList = new ArrayList<>(map.values());
        return getSortedResumes(sortedList);
    }

    @Override
    protected int doSize() {
        return map.size();
    }
}
