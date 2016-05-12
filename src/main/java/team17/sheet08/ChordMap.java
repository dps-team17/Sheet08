package team17.sheet08;


import java.io.IOException;
import java.rmi.MarshalledObject;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ChordMap<K, T> implements Map<K, T> {

    IDHTNode<T> nodes[];
    NetworkInfo networkInfo;

    public ChordMap(int m, int nodeCount) {

        networkInfo = new NetworkInfo(m);
        nodes = new IDHTNode[(int) Math.pow(2, m)];
        nodes[0] = new Node(0, networkInfo);

        Random random = new Random(0);
        for (int i = 1; i < nodeCount; i++) {
            int index = random.nextInt(networkInfo.getSize());
            while (nodes[index] != null) {
                index = (index + 1) % networkInfo.getSize();
            }
            nodes[index] = new Node(index, networkInfo);
            nodes[index].join(nodes[0]);
        }
    }

    private int getNodeId(int hash) {
        return hash % networkInfo.getSize();
    }

    /**
     * Gets the node responsible for the given key
     *
     * @param key
     * @return
     */
    private IDHTNode getResponsibleNode(Object key) {
        int id = getNodeId(key.hashCode());
        IDHTNode nOld = nodes[0];
        IDHTNode nNew;

        while ((nNew = (IDHTNode) nOld.lookup(id)).getId() != nOld.getId()) {
            nOld = nNew;
        }
        return nNew;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        IDHTNode responsibleNode = getResponsibleNode(key);
        return responsibleNode.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public T get(Object key) {
        IDHTNode responsibleNode = getResponsibleNode(key);
        try {
            return (T) responsibleNode.get(key).get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public T put(K key, T value) {
        IDHTNode responsibleNode = getResponsibleNode(key);
        T result = null;
        try {
            result = (T) responsibleNode.put(key, new MarshalledObject<>(value)).get();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public T remove(Object key) {

        T result = null;
        IDHTNode responsibleNode = getResponsibleNode(key);
        try {
            result = (T) responsibleNode.remove(key).get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends T> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<T> values() {
        return null;
    }

    @Override
    public Set<Entry<K, T>> entrySet() {
        return null;
    }
}