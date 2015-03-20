package connector;

import java.io.IOException;

import Global.Global;

public class Timer extends Thread {
	private int in_rate=100;//0.1 s
	private int due_time;
	private int elapsed_time;
	
	public Timer (int length)
	{
		due_time=length;elapsed_time=0;
	}
	public synchronized void reset()
	{
		elapsed_time = 0;
	}
	public void run()
	{
		// Keep looping
				for (;;)
				{
					// Put the timer to sleep
					try
					{ 
						Thread.sleep(in_rate);
					}
					catch (InterruptedException ioe) 
					{
						continue;
					}

					// Use 'synchronized' to prevent conflicts
					synchronized ( this )
					{
						// Increment time remaining
						elapsed_time += in_rate;

						// Check to see if the time has been exceeded
						if (elapsed_time > due_time)
						{
							// Trigger a timeout
							timeout();
						}
					}

				}

	}
	public void timeout()
	{
		try {
			Global.c.mySend(Global.lastSend);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
