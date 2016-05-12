# Sheet08

Distributed Systems Lab 

The goal of this lab assignment is to implement a DHT based on Chord. To simplify the task, the actual communication  may  just be  simulated  within  a  single  JVM – hence  no  real  network communication has to be involved. This should also provide you an idea how to implement a network simulator you might need for conducting experiments / research. NOTE: read the whole assignment first and plan for the following steps before starting with the implementation  of 1a).  This  could  save  some  time  especially  when  extending  the  chord implementation to realize a hash table.

## Assignments:

## 1. Chord Implementation
Implement a class representing a Chord node. The class should implement an interface, which summarizes the methods offered to other peers (just like a remote interface). Allow multiple instances of the class to be present within the same JVM. The ID of the represented node should be passed to the constructor and a method “connect(...)” should allow the node to join a “local, simulated” network. Leaving / failing nodes do not have to be supported (hence, no periodic updates necessary).

a.
Implement the interface and the Chord node class, including the successor / predecessor references and the finger table. Make “m” (number of bits within the address of a node) a variable within your setup. The node should offer the operations join(...) and sendMSG(...).

b.
Demonstrate the proper operation of your network by simulating an example Chord network as described in 1a) of the last assignment. Show that all finger tables, successor and predecessor references are properly set up. Send a message from node 25 to 8. List all nodes passed (and the index of the finger table entries followed).Are you using an iterative or recursive approach?

c.
Simulate a network m=12 (4096 addresses) containing 100 randomly distributed nodes. Let each node send a message to each other node and record the number of hops for delivering the message. What is the min / average /maximum number of nodes passed for delivering a message? How would this value change if you use 500 nodes (no actual experiment required)?

## 2. DHT Implementation
The Chord node implementation should be extended to store key – value pairs. The key type should (actually has to) be the same as for the ID of the peer nodes. Value should be instances of the MarshalledObject class. This class allows wrapping any serializable data object, thereby avoiding the expensive serialization / deserialization and dynamic code loading when shipping instances between JVMs.

a.
Add DHT functionality to your Chord node implementation by supporting the operations put, get, contains and remove.

b.
Write a wrapper for your peer class implementing the Map<K,T> interface. Internally, the key should be hashed (using Object#hashCode()) and the values of type should be encapsulated into a MarshalledObject. The resulting hashCode/value pair should then be stored within the DHT. Methods required by the Map<K,T> interface that cannot efficiently be supported by a DHT (e.g. equals, isEmpty(), ...) you can implement by throwing an UnsupportedOperationException. Which methods are those and what is their common characteristic?

c.
Implement a test application using your DHT via the Map<String,Person> interface to index Persons (a simple class including some data) using their names. Demonstrate your setup by adding / searching / removing a few dozen entries using 5 Chord Nodes (all locally)
