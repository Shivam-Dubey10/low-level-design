public class MyHashMap<K, V> {

    private static final int INITIAL_SIZE = 1<<4;
    private static final int MAXIMUM_SIZE = 1<<16;
    Entry[] hashTable;

    public HashMap() {
        hashTable = new Entry[INITIAL_SIZE];
    }

    public HashMap(int capacity) {
        int tableCapacity = getTableCapacity(capacity);
        hashTable = new Entry[tableCapacity];
    }

    final int getTableCapacity(int capacity) {
        int n = capacity-1;
        n |= n>>1;
        n |= n>>2;
        n |= n>>4;
        n |= n>>8;
        n |= n>>16;
        return n<0 ? 1 : n>=MAXIMUM_SIZE ? MAXIMUM_SIZE : n+1;
    }

    public class Entry {
        K key;
        V value;
        Entry next;
        public Entry (K key, V value) {
            this.key = key;
            this.value = value;
        }
        // getters and setters
    }

    public void put(K key, V value) {
        int hashCode = key.getHashCode() % hashTable.length;
        Entry node = hashTable[hashCode];
        if (node == null) {
            Entry entry = new Entry(key, value);
            hashTable[hashCode] = entry;
        } else {
            Entry prev = null;
            while (node!=null) {
                if (node.key==key) {
                    node.value = value;
                    return;
                }
                prev = node;
                node = node.next;
            }
            prev.next = new Entry(key, value);
        }
    }

    public V get(K key) {
        int hashCode = key.hashCode() % hashTable.length;
        Entry node = hashTable[hashCode];
        while (node!=null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

}