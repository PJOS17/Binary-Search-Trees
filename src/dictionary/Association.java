package dictionary;

/**
 * Represents a key-value association (pair).
 * Used to store English-Spanish word pairs in the dictionary.
 *
 * @param <K> the type of the key (English word)
 * @param <V> the type of the value (Spanish translation)
 */
public class Association<K extends Comparable<K>, V> implements Comparable<Association<K, V>> {

    private K key;
    private V value;

    /**
     * Creates a new Association with the given key and value.
     *
     * @param key   the key (English word)
     * @param value the associated value (Spanish word)
     */
    public Association(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key of this association.
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns the value of this association.
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets the value of this association.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Compares this association to another based on their keys.
     */
    @Override
    public int compareTo(Association<K, V> other) {
        return this.key.compareTo(other.key);
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
