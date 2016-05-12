package team17.sheet08;

/**
 * Created by Andreas on 12.05.2016.
 */
public class DiagnosticMessage implements IMessage {

    private static int messageCount = 0;
    private static int totalHops = 0;
    private static int minHops = 0;
    private static int maxHops = 0;

    private int recipient;
    private int hops;

    public DiagnosticMessage(int recipient) {
        this.recipient = recipient;
        messageCount++;
    }

    public int getRecipient() {
        return recipient;
    }

    public void increaseHops() {
        this.hops++;
    }

    public static void addMessage(DiagnosticMessage msg) {
        totalHops += msg.hops;
        minHops = Math.min(minHops, msg.hops);
        maxHops = Math.max(maxHops, msg.hops);
    }

    public static void display() {
        System.out.printf("MinHops: %d\tAverageHops: %f\tMaxHops: %d\n", minHops, (double) totalHops / messageCount, maxHops);
    }
}
