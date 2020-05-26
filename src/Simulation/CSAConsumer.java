package Simulation;


public class CSAConsumer extends Machine {




    public CSAConsumer(CEventList evList, Queue cons, ProductAcceptor si, String n) {
        super(cons, si, evList, n);
    }



    public boolean giveProduct(Product p) {
        // Only accept something if the machine is idle
        if (status == 'i') {
            // accept the product
            product = p;
            // mark starting time
            product.stamp(eventlist.getTime(), "Production started", name);
            // start production
            startProduction();
            // Flag that the product has arrived
            return true;
        }
        // Flag that the product has been rejected
        else return false;
    }

    private void startProduction() {
        // if its corporate call, draw the time from the corporate normal distribution
        String nameOfProd = this.product.getName();
        double duration = drawRandomNormalConsumer();
        // if its a consumer call, draw from the normal for consumers
        // Create a new event in the eventlist
        double tme = eventlist.getTime();
        eventlist.add(this, 0, tme + duration); //target,type,time
        // set status to busy
        status = 'b';
    }


    public static double drawRandomNormalConsumer() {
        // draw a [0,1] uniform distributed number
        double u = Math.random();
        double mean = 72;
        double stdev = 35;

        // Convert it into a random number from the given normal distribution
        double res = stdev * u + mean;

        if (res < 25) { // rejection sampling
            return drawRandomNormalConsumer();
        } else {
            return res;
        }
    }


}

