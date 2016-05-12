package team17.sheet08;

public class TextMessage implements IMessage {

    private final int recipient;
    private final String msg;

    public  TextMessage(int recipient, String msg){
        this.recipient = recipient;
        this.msg = msg;
    }

    public int getRecipient() {
        return recipient;
    }

    public String getMsg() {
        return msg;
    }
}
