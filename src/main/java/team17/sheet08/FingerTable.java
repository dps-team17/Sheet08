package team17.sheet08;


import java.security.InvalidParameterException;

public class FingerTable {

    private INode parent;
    private INode[] fingers;

    public FingerTable(INode parrent, int m) {
        this.parent = parrent;
        fingers = new INode[m];

        // Initialize table
        for (int i = 0; i < fingers.length; i++) {
            fingers[i] = parrent;
        }
    }


    public INode get(int id) {

        int ndx = getEntryIndex(id);

        return fingers[ndx];
    }

    private int getEntryIndex(int id) {
        int ownId = parent.getId();
        int otherId = id;

        // Check self reference
        if (ownId == otherId) throw new InvalidParameterException("Do not have a reference to myself");

        // Check overflow
        otherId = ownId > otherId ? otherId + (int) Math.pow(2, fingers.length) : otherId;
        int offset = otherId - ownId;

        // Calculate target range
        return ld(offset);
    }


    public void initialize(INode contact) {

        int parentId = parent.getId();
        INode successor = contact;
        int targetId;

        for (int i = 0; i < fingers.length; i++) {

            //Get target ndx
            targetId = (parentId + (int) Math.pow(2, i)) % (int) Math.pow(2, fingers.length);

            // Find successor
            boolean found = false;
            while (!found) {
                INode response = successor.lookup(targetId);
                if (successor.getId() == response.getId()) {
                    found = true;
                } else {
                    successor = response;
                }
            }

            fingers[i] = successor;
        }
    }


    public void update(INode newNode) {

        INode current = get(newNode.getId());
        int offsetCurrent = parent.getNetworkInfo().getOffset(parent.getId(), current.getId());
        int offsetJoined = parent.getNetworkInfo().getOffset(parent.getId(), newNode.getId());

        // Nothing to update
        if(offsetCurrent < offsetJoined) return;

        int ndx = getEntryIndex(newNode.getId());
        fingers[ndx] = newNode;

        for(int i = ndx - 1; i >= 0; i--){
            offsetCurrent = parent.getNetworkInfo().getOffset(parent.getId(), fingers[i].getId());

            if(offsetCurrent < offsetJoined) return;

            fingers[i] = newNode;
        }



    }

    public INode getSuccessor() {
        return fingers[0];
    }

    public void display() {

        System.out.println("\nFingers of " + parent.getId());

        for (int i = 0; i < fingers.length; i++) {

            int start = (int) ((parent.getId() + Math.pow(2, i)) % Math.pow(2, fingers.length));
            int end = (int) ((parent.getId() + Math.pow(2, i + 1)) % Math.pow(2, fingers.length));

            System.out.printf("%2d | [%2d, %2d) | %2d\n", start, start, end, fingers[i].getId());
        }
    }

    private static int ld(int x) {

        return (int) (Math.log(x) / Math.log(2));
    }


    public static void main(String[] args) {

        int me = 25;
        int size = 5;
        int offset, other;
        int pos;
        int totalSize = (int) Math.pow(2, size);


        for (int x = 1; x < totalSize; x++) {

            other = (me + x) % totalSize;
            pos = me > other ? other + (int) Math.pow(2, size) : other;
            offset = pos - me;
            int ndx = ld(offset);
            System.out.printf("%d: %d\n", other, ndx);
        }
    }


}
