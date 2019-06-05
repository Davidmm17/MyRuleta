package com.example.myruleta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class call_reciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String number = "";
        Bundle bundle = intent.getExtras();


        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            // Phone is ringing
            number = bundle.getString("incoming_number");


        } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            // Call received

        } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            // Call Dropped or rejected


        }

    }
}
