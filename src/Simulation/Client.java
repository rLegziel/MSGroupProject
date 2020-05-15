package Simulation;

public class Client extends Product {

    double arrivaltime;
    double waitingtime;
    double calltime;
    String typeofclient = "";

    public Client(String typeofclient, double arrivaltime){
        this.typeofclient = typeofclient;
        this.arrivaltime = arrivaltime;
    }

    public double getArrivaltime() {
        return arrivaltime;
    }

    public double getCalltime() {
        return calltime;
    }

    public double getWaitingtime() {
        return waitingtime;
    }

    public String getTypeofclient() {
        return typeofclient;
    }
}
