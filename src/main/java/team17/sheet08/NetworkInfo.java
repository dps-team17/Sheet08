package team17.sheet08;

public class NetworkInfo {

    private int n;
    private int m;

    public NetworkInfo(int exponent){
        this.n = (int) Math.pow(2, exponent);
        this.m = exponent;
    }

    public int getSize(){
        return n;
    }

    public int getExponent(){
        return m;
    }

    public int getOffset(int from, int to){

        if(from == to) return n;
        return Math.floorMod(to-from, n);
    }
}
