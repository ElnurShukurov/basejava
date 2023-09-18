package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamSerializationStrategy;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new ObjectStreamFileStorage(STORAGE_DIR, new ObjectStreamSerializationStrategy()));
    }
}
