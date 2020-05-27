/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Simulation {

    public CEventList list;
    public Queue consumer;
    public Queue corp;
    public Source sourcCon;
    public Source sourcCorporate;
    public Sink sink;

    public static CSACorporate corpAgent1;
    public static CSACorporate corpAgent2;
    public static CSACorporate corpAgent3;
    public static CSACorporate corpAgent4;
    public static CSACorporate corpAgent5;
    public static CSACorporate corpAgent6;

    public static CSAConsumer  consAgent1;
    public static CSAConsumer  consAgent2;
    public static CSAConsumer  consAgent3;
    public static CSAConsumer  consAgent4;
    public static CSAConsumer  consAgent5;
    public static CSAConsumer  consAgent6;


    public Simulation(){
        this.list = new CEventList();
        this.consumer = new Queue();
        this.corp = new Queue();
        this.sourcCon = new SourceConsumer(consumer, list, "Source consumer");
        this.sourcCorporate = new SourceCorporate(corp, list, "Source corporate");
        this.sink = new Sink("Sink 1");
        this.corpAgent1 = new CSACorporate(list, corp, consumer, sink, "Corp1 agent");
        this.corpAgent2 = new CSACorporate(list, corp, consumer, sink, "Corp2 agent");
        this.corpAgent3 = new CSACorporate(list, corp, consumer, sink, "Corp3 agent");
        this.corpAgent4 = new CSACorporate(list, corp, consumer, sink, "Corp4 agent");
        this.corpAgent5 = new CSACorporate(list, corp, consumer, sink, "Corp5 agent");
        this.corpAgent6 = new CSACorporate(list, corp, consumer, sink, "Corp6 agent");

        this.consAgent1 = new CSAConsumer(list, consumer, sink, "consumer1 agent");
        this.consAgent2 = new CSAConsumer(list, consumer, sink, "consumer2 agent");
        this.consAgent3 = new CSAConsumer(list, consumer, sink, "consumer3 agent");
        this.consAgent4 = new CSAConsumer(list, consumer, sink, "consumer4 agent");
        this.consAgent5 = new CSAConsumer(list, consumer, sink, "consumer5 agent");
        this.consAgent6 = new CSAConsumer(list, consumer, sink, "consumer6 agent");


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
            corpAgent1.setActive(true);
            corpAgent2.setActive(true);
            corpAgent3.setActive(true);
            corpAgent4.setActive(false);
            corpAgent5.setActive(false);
            corpAgent6.setActive(false);

            consAgent1.setActive(true);
            consAgent2.setActive(true);
            consAgent3.setActive(true);
            consAgent4.setActive(false);
            consAgent5.setActive(false);
            consAgent6.setActive(false);
        }else if(i==1){
            corpAgent1.setActive(true);
            corpAgent2.setActive(true);
            corpAgent3.setActive(false);
            corpAgent4.setActive(false);
            corpAgent5.setActive(false);
            corpAgent6.setActive(false);

            consAgent1.setActive(true);
            consAgent2.setActive(true);
            consAgent3.setActive(true);
            consAgent4.setActive(true);
            consAgent5.setActive(false);
            consAgent6.setActive(false);
        }else if(i==2){
            corpAgent1.setActive(true);
            corpAgent2.setActive(false);
            corpAgent3.setActive(false);
            corpAgent4.setActive(false);
            corpAgent5.setActive(false);
            corpAgent6.setActive(false);

            consAgent1.setActive(true);
            consAgent2.setActive(false);
            consAgent3.setActive(false);
            consAgent4.setActive(false);
            consAgent5.setActive(false);
            consAgent6.setActive(false);


        }
    }





}


    

