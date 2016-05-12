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
    }

    private void handleTextMessage(TextMessage msg) {

        if(msg.getRecipient() == nodeId){
            System.out.printf("Node %2d: Received: %s\n", nodeId, msg.getMsg());
            return;
        }

        int ndx = fingers.get(msg.getRecipient()).getId();
        System.out.printf("Node %2d: Forwarding message to %2d\n", nodeId, ndx);

        sendMessage(msg.getRecipient(), msg);
    }

    private void handleJoinMessage(JoinMessage msg){

        if(msg.getJoined() == this) {
            System.out.println("Node " + nodeId + " joined");
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

}
