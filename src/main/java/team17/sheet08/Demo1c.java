package team17.sheet08;

import java.util.Random;

public class Demo1c {

    static int m = 12;
    static int size = (int) Math.pow(2, m);

    static NetworkInfo net;
    static INode[] nodes;
    static INode root;


    static Random random;


    public static void main(String[] args) {

        net = new NetworkInfo(m);
        random = new Random(System.currentTimeMillis());

        simulate(100);
        simulate(500);
        simulate(4096);

    }

    private static void simulate(int nodeCount) {

        nodes = new INode[net.getSize()];
        root = new Node(0, net);
        nodes[0] = root;

        for (int i = 1; i < nodeCount; i++) {
            int index = random.nextInt(size);
            while (nodes[index] != null) {
                index = (index + 1) % size;
            }
            nodes[index] = new Node(index, net);
        }

        for (int i = 1; i < size; i++) {
            if (nodes[i] != null)
                nodes[i].join(root);
        }

        for (INode node : nodes) {
            if (node == null) continue;
            for (int i = 0; i < size; i++) {
                if(nodes[i] != null)
                    node.sendMessage(i, new DiagnosticMessage(i));
            }
        }
        System.out.printf("Nodes: %d\t", nodeCount);
        DiagnosticMessage.display();
    }
}
