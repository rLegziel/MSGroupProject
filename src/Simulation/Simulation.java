/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Simulation {

    public static CEventList list;
    public static Queue consumer;
    public static Queue corp;
    public static Source sourcCon;
    public static Source sourcCorporate;
    public static Sink sink;

    public static CSACorporate corpAgent1;
    public static CSACorporate corpAgent2;
    public static CSACorporate corpAgent3;
    public static CSACorporate corpAgent4;
    public static CSACorporate corpAgent5;
    public static CSACorporate corpAgent6;

    public static CSAConsumer consAgent1;
    public static CSAConsumer consAgent2;
    public static CSAConsumer consAgent3;
    public static CSAConsumer consAgent4;
    public static CSAConsumer consAgent5;
    public static CSAConsumer consAgent6;


    public Simulation(){
        this.list = new CEventList();
        this.consumer = new Queue();
        this.corp = new Queue();
        this.sourcCon = new SourceConsumer(consumer, list, "Source consumer");
        this.sourcCorporate = new SourceCorporate(corp, list, "Source corporate");
        this.sink = new Sink("Sink 1");
        // starting agents:
        this.corpAgent1 = new CSACorporate(list, consumer, corp, sink, "shift 1 corp 1 agent");
        this.corpAgent2 = new CSACorporate(list, consumer, corp, sink, "shift 1 corp 2 agent");
        this.corpAgent3 = new CSACorporate(list, consumer, corp, sink, "shift 1 corp 3 agent");
        this.corpAgent4 = new CSACorporate(list, consumer, corp, sink, "shift 1 corp 4 agent");
        this.corpAgent5 = new CSACorporate(list, consumer, corp, sink, "shift 1 corp 5 agent");
        this.corpAgent6 = new CSACorporate(list, consumer, corp, sink, "shift 1 corp 6 agent");


        this.consAgent1 = new CSAConsumer(list, consumer, sink, "shift 1 cons 1 agent");
        this.consAgent2 = new CSAConsumer(list, consumer, sink, "shift 1 cons 2 agent");
        this.consAgent3 = new CSAConsumer(list, consumer, sink, "shift 1 cons 3 agent");
        this.consAgent4 = new CSAConsumer(list, consumer, sink, "shift 1 cons 4 agent");
        this.consAgent5 = new CSAConsumer(list, consumer, sink, "shift 1 cons 5 agent");
        this.consAgent6 = new CSAConsumer(list, consumer, sink, "shift 1 cons 6 agent");





    }

    public CEventList getList() {
        return list;
    }

    public static void resetAgents() {
        corpAgent1.setActive(false);
        corpAgent2.setActive(false);
        corpAgent3.setActive(false);
        corpAgent4.setActive(false);
        corpAgent5.setActive(false);
        corpAgent6.setActive(false);

        consAgent1.setActive(false);
        consAgent2.setActive(false);
        consAgent3.setActive(false);
        consAgent4.setActive(false);
        consAgent5.setActive(false);
        consAgent6.setActive(false);
    }


	

        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Simulation sim  = new Simulation();
        sim.list.start(24*3600);


        String[] ev = sim.sink.getEvents();
        String[] stats = sim.sink.getStations();
        double[] tim = sim.sink.getTimes();


        for (int i = 0; i < ev.length; i++) {
            System.out.println(ev[i]);
            System.out.println(stats[i]);
            System.out.println(tim[i]);
        }

        System.out.println(ev.length/3);


    }

    public static void rosterChange(int i){
        if(i==0){
            resetAgents();
            corpAgent1 = new CSACorporate(list, consumer, corp, sink, "shift 2 corp agent 1");
            corpAgent2 = new CSACorporate(list, consumer, corp, sink, "shift 2 corp agent 2");
            corpAgent3 = new CSACorporate(list, consumer, corp, sink, "shift 2 corp agent 3");

            consAgent1 = new CSAConsumer(list, consumer, sink, " shift 2 cons agent 1");
            consAgent2 = new CSAConsumer(list, consumer, sink, " shift 2 cons agent 2");
            consAgent3 = new CSAConsumer(list, consumer, sink, " shift 2 cons agent 3");

        }else if(i==1){
            resetAgents();
            corpAgent1 = new CSACorporate(list, consumer, corp, sink, "shift 3 corp agent 1");
            corpAgent2 = new CSACorporate(list, consumer, corp, sink, "shift 3 corp agent 2");
            corpAgent3 = new CSACorporate(list, consumer, corp, sink, "shift 3 corp agent 3");

            consAgent1 = new CSAConsumer(list, consumer, sink, " shift 3 cons agent 1");
            consAgent2 = new CSAConsumer(list, consumer, sink, " shift 3 cons agent 2");
            consAgent3 = new CSAConsumer(list, consumer, sink, " shift 3 cons agent 3");

        }else if(i==2){
            resetAgents();
            corpAgent1 = new CSACorporate(list, consumer, corp, sink, "shift 4 corp agent 1");
            corpAgent2 = new CSACorporate(list, consumer, corp, sink, "shift 4 corp agent 2");
            corpAgent3 = new CSACorporate(list, consumer, corp, sink, "shift 4 corp agent 3");

            consAgent1 = new CSAConsumer(list, consumer, sink, " shift 4 cons agent 1");
            consAgent2 = new CSAConsumer(list, consumer, sink, " shift 4 cons agent 2");
            consAgent3 = new CSAConsumer(list, consumer, sink, " shift 4 cons agent 3");



        }


    }


}


    

