/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public static ArrayList<Double> consumers_times = new ArrayList<Double>();
    public static ArrayList<Double> consumers_times_arr = new ArrayList<Double>();
    public static ArrayList<Double> corp_times = new ArrayList<Double>();
    public static ArrayList<Double> corp_times_arr = new ArrayList<Double>();

    public Simulation() {


        this.list = new CEventList();
        this.consumer = new Queue();
        this.corp = new Queue();
        this.sourcCon = new SourceConsumer(consumer, list, "Source consumer");
        this.sourcCorporate = new SourceCorporate(corp, list, "Source corporate");
        this.sink = new Sink("Sink 1");

        // starting agents:
        this.corpAgent1 = new CSACorporate(list, consumer, corp, sink, "shift 1 corp 1 agent");
//        this.corpAgent2 = new CSACorporate(list, consumer, corp, sink, "shift 1 corp 2 agent");
//        this.corpAgent3 = new CSACorporate(list, consumer, corp, sink, "shift 1 corp 3 agent");



        this.consAgent1 = new CSAConsumer(list, consumer, sink, "shift 1 cons 1 agent");
        this.consAgent2 = new CSAConsumer(list, consumer, sink, "shift 1 cons 2 agent");
        this.consAgent3 = new CSAConsumer(list, consumer, sink, "shift 1 cons 3 agent");


    }

    public CEventList getList() {
        return list;
    }

    public static void resetAgents() {
        corpAgent1.setActive(false);
        corpAgent2.setActive(false);
//        corpAgent3.setActive(false);


        consAgent1.setActive(false);
        consAgent2.setActive(false);
        consAgent3.setActive(false);

    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Simulation sim = new Simulation();
        sim.list.start(24 * 3600 + 1500);


        String[] ev = sim.sink.getEvents();
        String[] stats = sim.sink.getStations();
        double[] tim = sim.sink.getTimes();


        for (int i = 0; i < ev.length; i++) {
            System.out.println(ev[i]);
            System.out.println(stats[i]);
            System.out.println(tim[i]);
            if (stats[i].contains("Source consumer")) {
                if (i + 1 < tim.length) {
                    consumers_times.add(tim[i + 1] - tim[i]);
                    consumers_times_arr.add(tim[i]);
                }
            } else if (stats[i].contains("Source corporate")) {
                if (i + 1 < tim.length) {
                    corp_times.add(tim[i + 1] - tim[i]);
                    corp_times_arr.add(tim[i]);
                }
            }


        }
        System.out.println(ev.length / 3);
        File cons_file = new File("consumers.csv");
        FileWriter cons_fw = new FileWriter(cons_file);
        BufferedWriter cons_bw = new BufferedWriter(cons_fw);

        for (int i = 0; i < consumers_times.size(); i++) {
            cons_bw.write(String.valueOf(consumers_times.get(i) / 60));
            cons_bw.newLine();
        }

        cons_bw.close();
        cons_fw.close();

        File corp_file = new File("corporate.csv");
        FileWriter corp_fw = new FileWriter(corp_file);
        BufferedWriter corp_bw = new BufferedWriter(corp_fw);

        for (int i = 0; i < corp_times.size(); i++) {
            corp_bw.write(String.valueOf(corp_times.get(i) / 60));
            corp_bw.newLine();
        }

        corp_bw.close();
        corp_fw.close();


        File cons_file_arr = new File("consumers_arr.csv");
        FileWriter cons_fw_arr = new FileWriter(cons_file_arr);
        BufferedWriter cons_bw_arr = new BufferedWriter(cons_fw_arr);

        for (int i = 0; i < consumers_times_arr.size(); i++) {
            cons_bw_arr.write(String.valueOf(consumers_times_arr.get(i) / 60));
            cons_bw_arr.newLine();
        }

        cons_bw_arr.close();
        cons_fw_arr.close();

        File corp_file_arr = new File("corporate_arr.csv");
        FileWriter corp_fw_arr = new FileWriter(corp_file_arr);
        BufferedWriter corp_bw_arr = new BufferedWriter(corp_fw_arr);

        for (int i = 0; i < corp_times_arr.size(); i++) {
            corp_bw_arr.write(String.valueOf(corp_times_arr.get(i) / 60));
            corp_bw_arr.newLine();
        }

        corp_bw_arr.close();
        corp_fw_arr.close();
    }


    public static void rosterChange(int i) {
        if (i == 0) {
//            resetAgents();
            corpAgent1 = new CSACorporate(list, consumer, corp, sink, "shift 2 corp agent 1");
            corpAgent2 = new CSACorporate(list, consumer, corp, sink, "shift 2 corp agent 2");

            consAgent1 = new CSAConsumer(list, consumer, sink, " shift 2 cons agent 1");
            consAgent2 = new CSAConsumer(list, consumer, sink, " shift 2 cons agent 2");
//            consAgent3 = new CSAConsumer(list, consumer, sink, " shift 2 cons agent 3");
            consAgent3.setActive(false);

        } else if (i == 1) {
            resetAgents();
            corpAgent1 = new CSACorporate(list, consumer, corp, sink, "shift 3 corp agent 1");

            consAgent1 = new CSAConsumer(list, consumer, sink, " shift 3 cons agent 1");
            consAgent2 = new CSAConsumer(list, consumer, sink, " shift 3 cons agent 2");
            consAgent3 = new CSAConsumer(list, consumer, sink, " shift 3 cons agent 3");

        } else if (i == 2) {
            resetAgents();
            corpAgent1 = new CSACorporate(list, consumer, corp, sink, "shift 4 corp agent 1");

            consAgent1 = new CSAConsumer(list, consumer, sink, " shift 4 cons agent 1");
            consAgent2 = new CSAConsumer(list, consumer, sink, " shift 4 cons agent 2");
            consAgent3 = new CSAConsumer(list, consumer, sink, " shift 4 cons agent 3");


        }


    }
}


    

