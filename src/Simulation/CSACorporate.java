package Simulation;

public class CSACorporate extends Machine {

    /**
     * Product that is being handled
     */
    private Product product;
    /**
     * Eventlist that will manage events
     */
    private final CEventList eventlist;
    /**
     * Corporate queue
     */
    private Queue corQueue;
    /**
     * consumer queue
     */
    private Queue consQueue;
    /**
     * Sink to dump products
     */
    private ProductAcceptor sink;
    /**
     * Status of the machine (b=busy, i=idle)
     */
    private char status;
    /**
     * Machine name
     */
    private final String name;
    /**
     * Processing time iterator
     */
    private int procCnt;


    public CSACorporate(CEventList evList, Queue cons, Queue corporate, ProductAcceptor si, String n) {
        eventlist = evList;
        consQueue = cons;
        corQueue = corporate;
        sink = si;
        name = n;
    }

    /**
     * Method to have this object execute an event
     *
     * @param type The type of the event that has to be executed
     * @param tme  The current time
     */
    public void execute(int type, double tme) {

        // show arrival

        System.out.println("Product finished at time = " + tme);
        // Remove product from system

        product.stamp(tme, "Production complete", name);
        sink.giveProduct(product);
        product = null;
        // set machine status to idle
        status = 'i';


        boolean corAsk = corQueue.askProduct(this);
        if (corAsk == false) {
            consQueue.askProduct(this);
        }
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
        double duration = 0;
        if (nameOfProd == "corporate") {
            duration = drawRandomNormalCorporate();
        } else {
            duration = drawRandomNormalConsumer();
        }
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

    public static double drawRandomNormalCorporate() {
        // draw a [0,1] uniform distributed number
        double u = Math.random();
        double mean = 216;
        double stdev = 72;
        // Convert it into a random number from the given normal distribution
        double res = stdev * u + mean;

        if (res < 45) { // rejection sampling
            return drawRandomNormalCorporate();
        } else {
            return res;
        }
    }


}
