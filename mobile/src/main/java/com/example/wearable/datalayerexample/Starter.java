package com.example.wearable.datalayerexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class Starter extends BroadcastReceiver {

               @Override
       public void onReceive(Context context, Intent intent) {
               // TODO Auto-generated method stub
                   Log.d("abc", "onStarter");
               String action = intent.getAction();
              if(action.equals("android.intent.action.BOOT_COMPLETED")) {
                       Intent i = new Intent(context, ServiceClass.class);

          //      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startService(i);
              //   intent.setAction(Intent.ACTION_MAIN);
            //      intent.addCategory(Intent.CATEGORY_HOME);

               }
         }
}


