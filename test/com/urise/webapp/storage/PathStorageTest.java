package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamSerializationStrategy;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializationStrategy()));
    }
}
