package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serialization.SerializationStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private SerializationStrategy serializationStrategy;
    private File directory;

    protected FileStorage(File directory, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");

        this.serializationStrategy = serializationStrategy;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doClear() {
        File[] files = getFilesList();
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            serializationStrategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Failed to delete file", file.getName());
        }
    }

    @Override
    protected int doSize() {
        File[] fileList = getFilesList();
        return fileList.length;
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = getFilesList();
        List<Resume> resumes = new ArrayList<>(files.length);
        for (File file : files) {
            resumes.add(doGet(file));
        }
        return resumes;
    }

    private File[] getFilesList() {
        File[] fileList = directory.listFiles();
        if (fileList == null) {
            throw new StorageException("Directory read error");
        }
        return fileList;
    }
}