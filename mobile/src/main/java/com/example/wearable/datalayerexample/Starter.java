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

                   context.startService(i);
               }
         }
}


