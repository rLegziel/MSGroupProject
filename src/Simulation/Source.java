package Simulation;

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
	
	public static double drawRandomNormal(double mean,double stdev)
	{
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a random number from the given normal distribution
		double res = stdev*u+ mean;
		return res;
	}
	// Calculate the rate based on the sinusoid
	// hour needs to be in a 24hr time, so 3PM =15
	public static double calculatueSinusoid(double hour) {
		if (hour >= 9) {
			if (hour <= 15) {
				double minusNine = hour - 9;
				double currentRate = 2 + minusNine * 0.3;
				return currentRate;
			} else if (hour <= 21) {
				double minusFifteen = hour - 15;
				double currentRate = 3.8 - minusFifteen * 0.3;
				return currentRate;
			} else if (hour <= 24) {
				double minusTwentyOne = hour - 21;
				double currentRate = 2 - minusTwentyOne * 0.3;
				return currentRate;
			}
		else if(hour <=3){
			double threeMinus  = 3-hour;
			double currentRate = 0.2 + threeMinus*0.3;
			return currentRate;
			}
		}
		else{
		double nineMinus = 9 - hour;
		double currentRate = 0.2 + nineMinus*0.3;
		return currentRate;
		}
	return 1; // if this returns we have an issue
	}
	// Compute arrival rate based on sinusoid
	// hour needs to be in a 24hr time, so 3:30PM = 15:30
	public static double getArrivalRate(double time){
		//convert the minutes to decimal value : 15h30 -> 15.5
		double hour = (int)time;
		double minutes = (time-hour)*(10/6);
		time = hour + minutes;

		double rate = 1.8*Math.sin((Math.PI/12)*(time-9))+2;
		return rate;
	}
	
}
