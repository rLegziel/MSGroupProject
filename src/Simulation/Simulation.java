/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

public class Simulation {

    public CEventList list;
    public Queue queue;
    public Source source;
    public Sink sink;
    public Machine mach;
	

        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        ArrayList<Double> consumers_times = new ArrayList<Double>();
        ArrayList<Double> corp_times = new ArrayList<Double>();

//    	// Create an eventlist
	    //CEventList l = new CEventList();
        CEventList l2 = new CEventList();

	// A queue for the machine
        Queue consumer = new Queue();
        Queue corp = new Queue();
        // A source
        SourceConsumer sourcCon = new SourceConsumer(consumer, l2, "Source consumer");
        SourceCorporate sourcCorporate = new SourceCorporate(corp, l2, "Source corporate");

        Sink si = new Sink("Sink 1");
//	// A machine
        CSACorporate corpAgent = new CSACorporate(l2, corp, consumer, si, "Corp agent");
        CSACorporate corpAgent1 = new CSACorporate(l2, corp, consumer, si, "Corp agent");
        CSACorporate corpAgent2 = new CSACorporate(l2, corp, consumer, si, "Corp agent");
        CSACorporate corp2Agent = new CSACorporate(l2, corp, consumer, si, "Corp2 agent");
        CSACorporate corp3Agent = new CSACorporate(l2, corp, consumer, si, "Corp3 agent");
        CSAConsumer consAgent = new CSAConsumer(l2, consumer, si, "consumer agent");
        CSAConsumer consAgent2 = new CSAConsumer(l2, consumer, si, "consumer agent");
        CSAConsumer consAgent3 = new CSAConsumer(l2, consumer, si, "consumer agent");
//	// start the eventlist
        //l.start(24*3600); // 2000 is maximum time

        l2.start(2000);
        System.out.println(si.getNumber());

        String[] ev = si.getEvents();
        String[] stats = si.getStations();
        double[] tim = si.getTimes();


        for (int i = 0; i < ev.length; i++) {
            System.out.println(ev[i]);
            System.out.println(stats[i]);
            System.out.println(tim[i]);

            if (stats[i].contains("Source consumer")){
                if (i+2 < tim.length) {
                    consumers_times.add(tim[i + 2] - tim[i]);
                }
            } else {
                if (i+2 < tim.length) {
                    corp_times.add(tim[i + 2] - tim[i]);
                }
            }

    }
        File cons_file = new File("consumers.csv");
        FileWriter cons_fw = new FileWriter(cons_file);
        BufferedWriter cons_bw = new BufferedWriter(cons_fw);

        for(int i=0;i<consumers_times.size();i++)
        {
            cons_bw.write(String.valueOf(consumers_times.get(i)));
            cons_bw.newLine();
        }

        cons_bw.close();
        cons_fw.close();

        File corp_file = new File("corporate.csv");
        FileWriter corp_fw = new FileWriter(corp_file);
        BufferedWriter corp_bw = new BufferedWriter(corp_fw);

        for(int i=0;i<corp_times.size();i++)
        {
            corp_bw.write(String.valueOf(corp_times.get(i)));
            corp_bw.newLine();
        }

        corp_bw.close();
        corp_fw.close();



}


    }
    

