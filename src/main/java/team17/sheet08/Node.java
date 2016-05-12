package team17.sheet08;


public class Node implements INode {

    private int nodeId;
    private FingerTable fingers;
    private INode predecessor;
    private NetworkInfo networkInfo;

    public Node(int id, NetworkInfo net){

        networkInfo = net;
        nodeId = id;
        fingers = new FingerTable(this, net.getExponent());
        predecessor = this;
    }

    @Override
    public void sendMessage(int targetId, IMessage msg) {

    }

    @Override
    public void sendMessage(IMessage msg) {
        fingers.getSuccessor().receive(msg);
    }

    @Override
    public void receive(IMessage msg) {

        if(msg instanceof JoinMessage){
            handleJoinMessage((JoinMessage) msg);
        }

    }

    private void handleJoinMessage(JoinMessage msg){

        if(msg.getJoined() == this) {
            System.out.println("Node " + nodeId + " joined");
            return;
        }

        fingers.update(msg.getJoined());

        updatePredeccessor(msg.getJoined());

        sendMessage(msg);
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
        sendMessage(msg);
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

}
