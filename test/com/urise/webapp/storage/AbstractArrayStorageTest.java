package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void updateExistingResume() throws Exception {
        Resume resumeToUpdate = new Resume(UUID_1);
        storage.update(resumeToUpdate);

        Resume updatedResume = storage.get(UUID_1);
        assertEquals(resumeToUpdate, updatedResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNonExistingResume() throws Exception {
        Resume nonExistingResume = new Resume(UUID_4);
        storage.update(nonExistingResume);
    }

    @Test
    public void saveNotExistingResume() throws Exception {
        int initialSize = storage.size();
        Resume newResume = new Resume(UUID_4);
        storage.save(newResume);

        assertEquals(initialSize + 1, storage.size());
        assertEquals(newResume, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistingResume() throws Exception {
        Resume existingResume = new Resume(UUID_1);
        storage.save(existingResume);
    }

    @Test
    public void getExistingResume() throws Exception {
        Resume resumeToGet = new Resume(UUID_1);
        assertEquals(new Resume(UUID_1), resumeToGet);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNonExistingResume() throws Exception {
        storage.get("dummy");
    }

    @Test()
    public void deleteExistingResume() throws Exception {
        int initialSize = storage.size();
        storage.delete(UUID_1);
        assertEquals(initialSize - 1, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistingResume() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] storageToGet = storage.getAll();
        assertEquals(storage.size(), storageToGet.length);
        assertEquals(new Resume(UUID_1), storageToGet[0]);
        assertEquals(new Resume(UUID_2), storageToGet[1]);
        assertEquals(new Resume(UUID_3), storageToGet[2]);
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test(expected = StorageException.class)
    public void overflow() throws Exception {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Exception was thrown ahead of time");
        }
        storage.save(new Resume());
    }
}