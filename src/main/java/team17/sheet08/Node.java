package team17.sheet08;


import java.io.IOException;
import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.Map;

public class Node<T> implements IDHTNode {

    private int nodeId;
    private FingerTable fingers;
    private INode predecessor;
    private NetworkInfo networkInfo;
    private Map<Object, MarshalledObject<T>> data;
    public Node(int id, NetworkInfo net){

        networkInfo = net;
        nodeId = id;
        fingers = new FingerTable(this, net.getExponent());
        predecessor = this;

        data = new HashMap<>();
    }

    @Override
    public void sendMessage(int targetId, IMessage msg) {

        if(targetId == nodeId) {
            this.receive(msg);
            return;
        }

        fingers.get(targetId).receive(msg);
    }

    @Override
    public void sendBroadcast(IMessage msg) {
        fingers.getSuccessor().receive(msg);
    }

    @Override
    public void receive(IMessage msg) {

        if(msg instanceof JoinMessage){
            handleJoinMessage((JoinMessage) msg);
        }
        else if(msg instanceof TextMessage){
            handleTextMessage((TextMessage) msg);
        }
        else if(msg instanceof DiagnosticMessage) {
            handleDiagnosticMessage((DiagnosticMessage) msg);
        }
    }

    private void handleDiagnosticMessage(DiagnosticMessage msg) {
        int offPredecessor = networkInfo.getOffset(predecessor.getId(), nodeId);
        int offTarget = networkInfo.getOffset(msg.getRecipient(), nodeId);

        if(msg.getRecipient() == nodeId || offTarget < offPredecessor) {
            DiagnosticMessage.addMessage(msg);
            return;
        }
        msg.increaseHops();
        sendMessage(msg.getRecipient(), msg);
    }

    private void handleTextMessage(TextMessage msg) {

        if(msg.getRecipient() == nodeId){
            System.out.printf("Node %2d: Received: %s\n", nodeId, msg.getMsg());
            return;
        }

        int ndx = fingers.getEntryIndex(msg.getRecipient());
        int succ = fingers.get(msg.getRecipient()).getId();
        System.out.printf("Node %2d: Forwarding message over finger %d to %2d\n", nodeId, ndx, succ);

        sendMessage(msg.getRecipient(), msg);
    }

    private void handleJoinMessage(JoinMessage msg){

        if(msg.getJoined() == this) {
            //System.out.println("Node " + nodeId + " joined");
            return;
        }

        fingers.update(msg.getJoined());

        updatePredeccessor(msg.getJoined());

        sendBroadcast(msg);
    }

    private void updatePredeccessor(INode newNode) {

        int offsetPredeccessor = networkInfo.getOffset(predecessor.getId(), nodeId);
        int offsetNewNode = networkInfo.getOffset(newNode.getId(), nodeId);

        if(offsetNewNode < offsetPredeccessor) predecessor = newNode;
    }


    @Override
    public INode lookup(int id) {

        // Check if i am the target
        if(id == nodeId) return this;

        // Check responsibility
        int offPredecessor = networkInfo.getOffset(predecessor.getId(), nodeId);
        int offTarget = networkInfo.getOffset(id, nodeId);

        // I'm responsible
        if(offTarget < offPredecessor) return this;

        // I'm not responsible
        return fingers.get(id);
    }

    @Override
    public void join(INode initial) {

        // Fill finger table
        fingers.initialize(initial);

        // Set predecessor
        predecessor = fingers.getSuccessor().getPredecessor();

        // Send join message
        JoinMessage msg = new JoinMessage(this);
        sendBroadcast(msg);
    }

    @Override
    public int getId() {
        return nodeId;
    }

    @Override
    public void printTable() {
        fingers.display();
    }

    @Override
    public NetworkInfo getNetworkInfo() {
        return networkInfo;
    }

    @Override
    public INode getPredecessor() {
        return predecessor;
    }

    @Override
    public String toString(){
        return "Node " + nodeId;
    }


    @Override
    public MarshalledObject put(Object key, MarshalledObject value) {
        MarshalledObject<T> result =  data.put(key, value);

        if( result == null){
            try {
                result = new MarshalledObject<T>(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public MarshalledObject get(Object key) {
        MarshalledObject<T> result = data.get(key);

        try {
            result =  result == null? new MarshalledObject<T>(null) : result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean contains(Object key) {
        return data.containsKey(key);
    }

    @Override
    public MarshalledObject remove(Object key) {
        return data.remove(key);
    }
}
