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
	// A queue for the machine
	Queue q = new Queue();
	// A source
//	Source s = new Source(q,l,"Source 1");
//	// A sink
//	Sink si = new Sink("Sink 1");
//	// A machine
//	Machine m = new Machine(q,si,l,"Machine 1");
//	// start the eventlist
//	l.start(2000); // 2000 is maximum time
    Source s2 = new Source(q,l,"source 1");

    ArrayList<Double> arrTCons = s2.getArrivalTimesConsumers();
    ArrayList<Double> arrTCorp = s2.getArrivalTimesCorporate();
    System.out.println("consumer times");
    for(int i=0; i<arrTCons.size(); i++) {
        System.out.println(arrTCons.get(i));
    }
    System.out.println("-----");
    System.out.println("corporate times");
    for(int i=0; i<arrTCorp.size(); i++) {
        System.out.println(arrTCorp.get(i));
    }

    System.out.println("number of arrivals of consumers "+arrTCons.size());
    System.out.println("-----");
    System.out.println("number of arrivales of corporate custommers "+arrTCorp.size());
}


    }
    

