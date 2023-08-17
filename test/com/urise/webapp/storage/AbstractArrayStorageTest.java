package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public AbstractArrayStorageTest(final Storage storage) {
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
        assertSize(0);
        assertArrayEquals(storage.getAll(), new Resume[0]);
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

        assertSize(initialSize + 1);
        assertGet(newResume);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistingResume() throws Exception {
        Resume existingResume = new Resume(UUID_1);
        storage.save(existingResume);
    }

    @Test
    public void getExistingResume() throws Exception {
        assertGet(new Resume(UUID_1));
        assertGet(new Resume(UUID_2));
        assertGet(new Resume(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNonExistingResume() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteExistingResume() throws Exception {
        int initialSize = storage.size();
        storage.delete(UUID_1);
        assertSize(initialSize - 1);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistingResume() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] actualStorage = storage.getAll();
        Resume[] expectedStorage = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        assertArrayEquals(actualStorage, expectedStorage);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test(expected = StorageException.class)
    public void overflow() throws Exception {
        storage.clear();
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Exception was thrown ahead of time");
        }
        storage.save(new Resume());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume expectedResume) {
        Resume actualResume = storage.get(expectedResume.getUuid());
        assertEquals(expectedResume, actualResume);
    }
}