package Simulation;

import java.util.ArrayList;

public class SourceCorporate extends Source {

    //    /** Eventlist that will be requested to construct events */
//    private CEventList list;
//    /** Queue that buffers products for the machine */
//    private ProductAcceptor queue;
//    /** Name of the source */
//    private String name;
//    /** Mean interarrival time */
//    private double meanArrTime;
    /**
     * Interarrival times (in case pre-specified)
     */
    private double[] interarrivalTimes;
    /**
     * Interarrival time iterator
     */
    private int interArrCnt;


    public SourceCorporate(ProductAcceptor q, CEventList l, String n) {
        super(q, l, n, false);
    }

//    @Override
//    public void execute(int type, double tme) {
//        // show arrival
//        System.out.println("Arrival at time = " + tme);
//        // give arrived product to queue
//        Product p = new Product("corporate client");
//        p.stamp(tme, "Creation", name);
//        queue.giveProduct(p);
//        // generate duration
//        if (meanArrTime > 0) {
//            double duration = drawRandomExponential(meanArrTime);
//            // Create a new event in the eventlist
//            list.add(this, 0, tme + duration); //target,type,time
//        } else {
//            interArrCnt++;
//            if (interarrivalTimes.length > interArrCnt) {
//                list.add(this, 0, tme + interarrivalTimes[interArrCnt]); //target,type,time
//            } else {
//                list.stop();
//            }
//        }
//
//    }

    //return the next arrival time of corporate customer call, given the previous one
    public static double getNextTimeCorporate(double t_1) {
        double u = Math.random();// draw a [0,1] uniform distributed number
        double currentRate = getArrivalRateCorporate(t_1);
        double t = t_1 - (1 / currentRate) * Math.log(u);

        return t;
    }

    public static double getArrivalRateCorporate(double time) {
        double rate = 0;

        if (time >= 8*3600 && time < 18*3600) { //rate between 8am-6pm
            rate = 1;
        } else { //rate between 6pm-8am  if((time >= 0 && time < 8) || (time >= 18 && time < 24))
            rate = 0.2;
        }
        return rate/(60) ;//get rate per second
    }

    public static double[] getArrivalTimes() {
        ArrayList<Double> arrivalTimes = new ArrayList<>();
        arrivalTimes.add(getNextTimeCorporate(0)); //get first arrival time
        double currentT = arrivalTimes.get(0);
        int i = 1;
        while (currentT < 24*3600) {
            arrivalTimes.add(getNextTimeCorporate(currentT));
            currentT = arrivalTimes.get(i);
            i++;
        }
        for (int j = 0; j < arrivalTimes.size(); j++) {
            if (arrivalTimes.get(j) > 24*3600) {
                arrivalTimes.remove(j);
            }
        }
        double[] arr = arrivalTimes.stream().mapToDouble(Double::doubleValue).toArray();
        return arr;
    }
}
