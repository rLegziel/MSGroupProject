package Simulation;

public class CsaAgent extends Machine{

    int rate;
    int[] shift= new int[2];
    String typeofcsa = "";


    public CsaAgent(int[] shift, String typeofcsa){
        this.typeofcsa = typeofcsa;
        this.shift = shift;
        if (this.typeofcsa.toLowerCase().contains("consumer"))
            rate = 35;

        else if (this.typeofcsa.toLowerCase().contains("corporate"))
            rate = 60;
    }

    public void setShift(int[] shift){
        this.shift = shift;
    }

    public int getRate(){
        return this.rate;
    }

    public int[] getShift(){
        return this.shift;
    }

}
