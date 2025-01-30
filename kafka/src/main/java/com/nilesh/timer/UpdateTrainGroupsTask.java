package com.nilesh.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class UpdateTrainGroupsTask extends TimerTask
{
  @Override
  public void run()
  {
    Date currentDate = new Date();
    String format = new SimpleDateFormat("HH:mm:ss")
        .format(currentDate);
    System.out.println("Invoked inside the timer at - " + format);
    // this is where we call the Mongo Groups API call and
    // populate the cached object...
  }
}
