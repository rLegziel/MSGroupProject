package Simulation;

import java.util.ArrayList;

/**
 *	A source of products
 *	This class implements CProcess so that it can execute events.
 *	By continuously creating new events, the source keeps busy.
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Source implements CProcess
{
	/** Eventlist that will be requested to construct events */
	private CEventList list;
	/** Queue that buffers products for the machine */
	private ProductAcceptor queue;
	/** Name of the source */
	private String name;
	/** Mean interarrival time */
	private double meanArrTime;
	/** Interarrival times (in case pre-specified) */
	private double[] interarrivalTimes;
	/** Interarrival time iterator */
	private int interArrCnt;

	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with mean 33
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*/
	public Source(ProductAcceptor q,CEventList l,String n)
	{
		list = l;
		queue = q;
		name = n;
		meanArrTime=33;
		// put first event in list for initialization
		list.add(this,0,drawRandomExponential(meanArrTime)); //target,type,time
	}

	public Source(ProductAcceptor q, CEventList l, String n, boolean consumer) {
		list = l;
		queue = q;
		name = n;
		meanArrTime = 0;
		if (consumer == true) {
			interarrivalTimes = SourceConsumer.getArrivalTimes();
		} else {
			interarrivalTimes = SourceCorporate.getArrivalTimes();
		}

		// put first event in list for initialization
		list.add(this, 0, interarrivalTimes[0]); //target,type,time
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with specified mean
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param m	Mean arrival time
	*/
	public Source(ProductAcceptor q,CEventList l,String n,double m)
	{
		list = l;
		queue = q;
		name = n;
		meanArrTime=m;
		// put first event in list for initialization
		list.add(this,0,drawRandomExponential(meanArrTime)); //target,type,time
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are prespecified
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param ia	interarrival times
	*/
	public Source(ProductAcceptor q,CEventList l,String n,double[] ia)
	{
		list = l;
		queue = q;
		name = n;
		meanArrTime=-1;
		interarrivalTimes=ia;
		interArrCnt=0;
		// put first event in list for initialization
		list.add(this,0,interarrivalTimes[0]); //target,type,time
	}
	
        @Override
	public void execute(int type, double tme)
	{
		// show arrival
		System.out.println("Arrival at time = " + tme);
		// give arrived product to queue
		Product p = new Product();
		p.stamp(tme,"Creation",name);
		queue.giveProduct(p);
		// generate duration
		if(meanArrTime>0)
		{
			double duration = drawRandomExponential(meanArrTime);
			// Create a new event in the eventlist
			list.add(this,0,tme+duration); //target,type,time
		}
		else
		{
			interArrCnt++;
			if(interarrivalTimes.length>interArrCnt)
			{
				list.add(this,0,tme+interarrivalTimes[interArrCnt]); //target,type,time
			}
			else
			{
				list.stop();
			}
		}
	}
	
	public static double drawRandomExponential(double mean)
	{
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with mean 33
		double res = -mean*Math.log(u);
		return res;
	}
	

	// Compute arrival rate of consumer call based on sinusoid
	// hour needs to be in a 24hr time, so 3:30PM = 15:30
	public static double getArrivalRateConsumer(double time){
		double rate = 1.8*Math.sin((Math.PI/12)*(time-9))+2;
		return 60*rate;//get rate per hour
	}

	// Compute arrival rate of corporate customer call based on time
	// hour needs to be in a 24hr time, so 3:30PM = 15:30
	public static double getArrivalRateCorporate(double time){
		double rate=0;

		if(time >= 8 && time < 18){ //rate between 8am-6pm
			rate = 1;
		}else{ //rate between 6pm-8am  if((time >= 0 && time < 8) || (time >= 18 && time < 24))
			rate = 0.2;
		}
		return rate*60;//get rate per hour
	}

	//return the next arrival time of consumer call, given the previous one
	public static double getNextTimeConsumer(double t_1){
		double maxRate = 3.8*60; //highest arrival rate, reached at 3pm
		double t=t_1;
		double u1 = 1;
		double u2 = 1;
		double currentRate = 1;
		boolean flag = true;

		while(flag) {
			u1 = Math.random(); // draw a [0,1] uniform distributed number
			u2 = Math.random(); // draw a [0,1] uniform distributed number, independent from u1
			t = t - (1/maxRate)*Math.log(u1);
			currentRate = getArrivalRateConsumer(t); //get current arrival rate
			if(u2*maxRate <= currentRate) {
				flag = false;
			}
		}

		return t;
	}

	//return the next arrival time of corporate customer call, given the previous one
	public static double getNextTimeCorporate(double t_1){
		double u = Math.random();// draw a [0,1] uniform distributed number
		double currentRate = getArrivalRateCorporate(t_1);
		double t = t_1-(1/currentRate)*Math.log(u);

		return t;
	}

//	//generate an arraylist containing all calling times of consumers over 24h
//	public static ArrayList<Double> getArrivalTimesConsumers(){
//		ArrayList<Double> arrivalTimes = new ArrayList<>();
//		arrivalTimes.add(getNextTimeConsumer(0)); //get first arrival time
//		double currentT = arrivalTimes.get(0);
//		int i=1;
//		while(currentT < 24){
//			arrivalTimes.add(getNextTimeConsumer(currentT));
//			currentT = arrivalTimes.get(i);
//			i++;
//		}
//		for(int j=0; j<arrivalTimes.size(); j++) {
//			if(arrivalTimes.get(j) > 24) {
//				arrivalTimes.remove(j);
//			}
//		}
//		return arrivalTimes;
//	}

	//generate an arraylist containing all calling times of corporate customers over 24h
	public static ArrayList<Double> getArrivalTimesCorporate(){
		ArrayList<Double> arrivalTimes = new ArrayList<>();
		arrivalTimes.add(getNextTimeCorporate(0)); //get first arrival time
		double currentT = arrivalTimes.get(0);
		int i=1;
		while(currentT < 24){
			arrivalTimes.add(getNextTimeCorporate(currentT));
			currentT = arrivalTimes.get(i);
			i++;
		}
		for(int j=0; j<arrivalTimes.size(); j++) {
			if(arrivalTimes.get(j) > 24) {
				arrivalTimes.remove(j);
			}
		}
		return arrivalTimes;
	}

	public void setIATimes(double[] interA) {
		interarrivalTimes = interA;
	}
	
}
