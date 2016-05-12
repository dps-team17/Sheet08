package team17.sheet08;

public interface INode {

    void sendMessage(int targetId, IMessage msg);
    void sendBroadcast(IMessage msg);

    void receive(IMessage msg);
    INode lookup(int id);

    void join(INode initial);

    int getId();

    void printTable();

    NetworkInfo getNetworkInfo();

    INode getPredecessor();
}
