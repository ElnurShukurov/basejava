package com.urise.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Storage already contains resume " + uuid, uuid);
    }
}
