package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.ResumeTestData;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();
    protected final Storage storage;
    static ResumeTestData resumeTestData = new ResumeTestData();

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final String NAME_1 = "Name1";
    private static final String NAME_2 = "Name2";
    private static final String NAME_3 = "Name3";
    private static final String NAME_4 = "Name4";

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R4;

    static {
        R1 = resumeTestData.generateResume(UUID_1, NAME_1);
        R2 = resumeTestData.generateResume(UUID_2, NAME_2);
        R3 = resumeTestData.generateResume(UUID_3, NAME_3);
        R4 = resumeTestData.generateResume(UUID_4, NAME_4);
    }

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
        storage.update(resumeToUpdate);
        assertTrue(resumeToUpdate.equals(storage.get(UUID_1)));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNonExistingResume() throws Exception {
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
        assertArrayEquals(actualStorage.toArray(), expectedStorage.toArray());
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