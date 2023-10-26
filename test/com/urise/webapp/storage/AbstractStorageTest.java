package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.ResumeTestData.*;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();
    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        List<Resume> emptyList = new ArrayList<>();
        assertSize(0);
        assertArrayEquals(storage.getAllSorted().toArray(), emptyList.toArray());
    }

    @Test
    public void updateExistingResume() throws Exception {
        Resume resumeToUpdate = new Resume(UUID_1, "New Name");
        resumeToUpdate.addContact(ContactType.EMAIL, "elnur@list.ru");
        resumeToUpdate.addContact(ContactType.SKYPE, "@new_skype");
        resumeToUpdate.addContact(ContactType.PHONE, "789-98-77");
        storage.update(resumeToUpdate);
        assertTrue(resumeToUpdate.equals(storage.get(UUID_1)));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNonExistingResume() throws Exception {
        R4.addContact(ContactType.PHONE, "+123456");
        storage.update(R4);
    }

    @Test
    public void saveNotExistingResume() throws Exception {
        int initialSize = storage.size();
        storage.save(R4);

        assertSize(initialSize + 1);
        assertGet(R4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistingResume() throws Exception {
        storage.save(R1);
    }

    @Test
    public void getExistingResume() throws Exception {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNonExistingResume() throws Exception {
        storage.get(UUID_4);
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
    public void getAllSorted() throws Exception {
        List<Resume> actualStorage = storage.getAllSorted();
        List<Resume> expectedStorage = new ArrayList<>(Arrays.asList(R1, R2, R3));
        assertArrayEquals(expectedStorage.toArray(), actualStorage.toArray());
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume expectedResume) {
        Resume actualResume = storage.get(expectedResume.getUuid());
        assertEquals(expectedResume, actualResume);
    }
}