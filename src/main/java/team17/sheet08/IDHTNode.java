package team17.sheet08;

import java.rmi.MarshalledObject;

public interface IDHTNode<T> extends INode {

    MarshalledObject<T> put(Object key, MarshalledObject<T> value);
    MarshalledObject<T> get(Object key);
    boolean contains(Object key);
    MarshalledObject<T> remove(Object key);
}
