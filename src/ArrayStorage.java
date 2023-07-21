import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume searchedResume = null;
        for (Resume resume : storage) {
            if (resume != null && resume.toString().equals(uuid)) {
                searchedResume = resume;
            }
        }
        return searchedResume;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].toString().equals(uuid)) {
                storage[i] = null;
                break;
            }
        }
        for (int i = storage.length - 1; i >= 0; i--) {
            if (storage[i] != null) {
                for (int j = i; j > 0; j--) {
                    if (storage[j] == null) {
                        storage[j] = storage[j + 1];
                    }
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] nonNullStorage = new Resume[size()];
        int index = 0;
        for (Resume resume : storage) {
            if (resume != null) {
                nonNullStorage[index] = resume;
                index++;
            }
        }
        return nonNullStorage;
    }

    int size() {
        int arraySize = 0;
        for (Resume resume : storage) {
            if (resume != null) {
                arraySize++;
            }
        }
        return arraySize;
    }
}
