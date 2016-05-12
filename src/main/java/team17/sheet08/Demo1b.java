package team17.sheet08;

public class Demo1b {

    public static void main(String[] args) {

        int m = 5;
        int size = (int) Math.pow(2, m);

        NetworkInfo net = new NetworkInfo(m);
        INode[] nodes = new INode[size];

        nodes[1] = new Node(1, net);
        nodes[3] = new Node(3, net);
        nodes[7] = new Node(7, net);
        nodes[8] = new Node(8, net);
        nodes[12] = new Node(12, net);
        nodes[15] = new Node(15, net);
        nodes[19] = new Node(19, net);
        nodes[25] = new Node(25, net);
        nodes[27] = new Node(27, net);
        //nodes[21] = new Node(21, net);
        //nodes[0] = new Node(0, net);
        //nodes[31] = new Node(31, net);

        for (int i = 2; i < size; i++) {
            if (nodes[i] != null)
                nodes[i].join(nodes[1]);
        }

        int sender = 25;
        int receiver = 19;
        System.out.printf("\nSending message form %d to %d...\n", sender, receiver);
        nodes[sender].sendMessage(receiver, new TextMessage(receiver, "Hello!"));
    }
}
