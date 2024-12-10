class HashTableLinearProbing {
    private String[] keys;
    private int[] values;
    private int size;
    private boolean useOldHash;

    public HashTableLinearProbing(int capacity, boolean useOldHash) {
        this.keys = new String[capacity];
        this.values = new int[capacity];
        this.size = capacity;
        this.useOldHash = useOldHash;
    }

    public void put(String key, int value) {
        int index = hash(key);
        while (keys[index] != null) {
            index = (index + 1) % size;
        }
        keys[index] = key;
        values[index] = value;
    }

    public boolean contains(String key) {
        int index = hash(key);
        while (keys[index] != null) {
            if (keys[index].equals(key)) return true;
            index = (index + 1) % size;
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
}
