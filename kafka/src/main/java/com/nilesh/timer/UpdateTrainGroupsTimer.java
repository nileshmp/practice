package com.nilesh.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateTrainGroupsTimer
{
  public UpdateTrainGroupsTimer()
  {
    long interval = 10 * 1000;
    long delay = computeDelay();
    TimerTask timerTask = new UpdateTrainGroupsTask();
    // running timer task as daemon thread
    Timer timer = new Timer(true);
    System.out.println("Timer start delayed by : " + delay);
    timer.scheduleAtFixedRate(timerTask, delay, interval);
    System.out.println("TimerTask started");
  }

  private long computeDelay()
  {
    long secondsPassed = ((System.currentTimeMillis() / 1000) % 60);
    System.out.println(secondsPassed);
    System.out.println(60 - secondsPassed);
    return ((60 - secondsPassed) * 1000);
  }

  public static void main(String[] args) throws InterruptedException
  {
    long timeNow = System.currentTimeMillis();
    long timeNowSeconds = timeNow / 1000;
    System.out.println(timeNowSeconds % 60);
    Date currentDate = new Date();
    String format = new SimpleDateFormat("ss")
        .format(currentDate);
    System.out.println(format);
    // new UpdateTrainGroupsTimer();
    // sleep(300000);
  }
}
