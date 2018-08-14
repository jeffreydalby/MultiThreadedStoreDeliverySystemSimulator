package edu.bu.met.cs665.simulator.clockticker;

//Clock Ticker so we can control timestamps for the simulation
public class ClockTicker implements Runnable {

    private static ClockTicker clockTickerInstance; //singleton instance
    private Thread clockTickerThread; //Thread pointer so we can shut it down gracefully

    //Use the to speed up or slow down the simulated clock
    private static final int CLOCK_SPEED = 1000;

    private ClockTicker(){} //prevent instantiation

    /**
     * Get the singleton instance of the clockticker
     * @return singleton instance
     */
    public static synchronized ClockTicker getClockTickerInstance(){
        if(clockTickerInstance == null) clockTickerInstance = new ClockTicker();
        return clockTickerInstance;
    }

    /**
     * Get the thread holding the ticker
     * @return thread with ticker
     */
    public Thread getClockTickerThread() {
        return clockTickerThread;
    }

    /**
     * Start the clock in it's own thread
     */
    public void startClock(){
        this.clockTickerThread = new Thread(ClockTicker.getClockTickerInstance());
        this.clockTickerThread.start();

    }



    public int getSimulatorClock() {
        return this.simulatorClock;
    }

    //Instead of going off of real time for the simulation (which would take forever)
  //just using an int ticker system clock;
  private int simulatorClock;




  @Override
  public void run() {
    while (true) {
      if(Thread.currentThread().isInterrupted()) break;
      this.simulatorClock++;

      try {
        Thread.sleep(CLOCK_SPEED);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
        //exit our loop cause something went wrong
        break;
      }
    }
  }
}
