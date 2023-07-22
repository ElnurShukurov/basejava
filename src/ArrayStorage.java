import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int arraySize = 0;

    void clear() {
        Arrays.fill(storage, 0, arraySize, null);
        arraySize = 0;
    }

    void save(Resume r) {
        storage[arraySize] = r;
        arraySize++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < arraySize; i++) {
            Resume resume = storage[i];
            if (resume.toString().equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < arraySize; i++) {
            if (storage[i].toString().equals(uuid)) {
                storage[i] = storage[arraySize - 1];
                arraySize--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, arraySize);
    }

    int size() {
        return arraySize;
    }
}
