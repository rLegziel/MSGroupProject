package Simulation;

import java.util.ArrayList;

public class SourceConsumer extends Source {

//    /** Eventlist that will be requested to construct events */
//    private CEventList list;
//    /** Queue that buffers products for the machine */
//    private ProductAcceptor queue;
//    /** Name of the source */
//    private String name;
//    /** Mean interarrival time */
//    private double meanArrTime;

    public SourceConsumer(ProductAcceptor q, CEventList l, String n) {
        super(q, l, n, true);

    }


/*
    @Override
    public void execute(int type, double tme) {
        // show arrival
        System.out.println("Arrival at time = " + tme);
        // give arrived product to queue
        Product p = new Product("consumer client");
        p.stamp(tme, "Creation", name);
        queue.giveProduct(p);

            interArrCnt++;
            if (interarrivalTimes.length > interArrCnt) {
                list.add(this, 0, tme + interarrivalTimes[interArrCnt]); //target,type,time
            } else {
                list.stop();
            }


    }
 */

    // Compute arrival rate per second of consumer call based on sinusoid
    // hour needs to be in seconds
    public static double getArrivalRateConsumer(double time) {
        double rate = 1.8 * Math.sin((Math.PI / 12*3600) * (time - 9*3600)) + 2;
        return rate/(60);//get rate per second
    }


    //return the next arrival time of consumer call, given the previous one
    public static double getNextTimeConsumer(double t_1) {
        double maxRate = 3.8/60; //highest arrival rate, reached at 3pm = 54,000s
        double t = t_1;
        double u1 = 1;
        double u2 = 1;
        double currentRate = 1;
        boolean flag = true;

        while (flag) {
            u1 = Math.random(); // draw a [0,1] uniform distributed number
            u2 = Math.random(); // draw a [0,1] uniform distributed number, independent from u1
            t = t - (1 / maxRate) * Math.log(u1);
            currentRate = getArrivalRateConsumer(t); //get current arrival rate
            if (u2 * maxRate <= currentRate) {
                flag = false;
            }
        }

        return t;
    }

    //generate an array containing all calling times of consumers over 24h
    public static double[] getArrivalTimes() {
        ArrayList<Double> arrivalTimes = new ArrayList<>();
        arrivalTimes.add(getNextTimeConsumer(0)); //get first arrival time
        double currentT = arrivalTimes.get(0);
        int i = 1;
        while (currentT < 24*3600) {
            arrivalTimes.add(getNextTimeConsumer(currentT));
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
