package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("There is no " + uuid + " resume in the storage to update", uuid);
    }
}
