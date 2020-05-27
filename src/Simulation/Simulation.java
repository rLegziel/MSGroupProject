/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import java.util.ArrayList;

public class Simulation {

    public CEventList list;
    public Queue queue;
    public Source source;
    public Sink sink;
    public Machine mach;
	

        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//    	// Create an eventlist
	CEventList l = new CEventList();
        CEventList l2 = new CEventList();

	// A queue for the machine
        Queue consumer = new Queue();
        Queue corp = new Queue();
        // A source
        SourceConsumer sourcCon = new SourceConsumer(consumer, l, "Source consumer");
        SourceCorporate sourcCorporate = new SourceCorporate(corp, l2, "Source corporate");
//	// A sink
        Sink si = new Sink("Sink 1");
//	// A machine
        CSACorporate corpAgent = new CSACorporate(l2, corp, consumer, si, "Corp agent");
        CSACorporate corp2Agent = new CSACorporate(l2, corp, consumer, si, "Corp2 agent");
        CSACorporate corp3Agent = new CSACorporate(l2, corp, consumer, si, "Corp3 agent");
        CSAConsumer consAgent = new CSAConsumer(l2, consumer, si, "consumer agent");
//	// start the eventlist
        l.start(2000); // 2000 is maximum time
        l2.start(2000);

        String[] ev = si.getEvents();
        String[] stats = si.getStations();
        double[] tim = si.getTimes();


        for (int i = 0; i < ev.length; i++) {
            System.out.println(ev[i]);
            System.out.println(stats[i]);
            System.out.println(tim[i]);

    }

}


    }
    

