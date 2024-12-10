import java.util.*;

class HashTableSeparateChaining {
    private List<Entry>[] table;
    private int size;
    private boolean useOldHash;

    public HashTableSeparateChaining(int capacity, boolean useOldHash) {
        this.table = new LinkedList[capacity];
        this.size = capacity;
        this.useOldHash = useOldHash;
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public void put(String key, int value) {
        int index = hash(key);
        table[index].add(new Entry(key, value));
    }

    public boolean contains(String key) {
        int index = hash(key);
        for (Entry entry : table[index]) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private int hash(String key) {
        int hash = 0;
        if (useOldHash) {
            int skip = Math.max(1, key.length() / 8);
            for (int i = 0; i < key.length(); i += skip) {
                hash = (hash * 37) + key.charAt(i);
            }
        } else {
            for (int i = 0; i < key.length(); i++) {
                hash = (hash * 31) + key.charAt(i);
            }
        }
        return Math.abs(hash % size);
    }

    private static class Entry {
        String key;
        int value;

        Entry(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
