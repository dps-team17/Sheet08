package team17.sheet08;


public class JoinMessage implements IMessage{

    private final INode joined;

    public JoinMessage(INode joined){
        this.joined = joined;
    }

    public INode getJoined(){
        return joined;
    }
}
