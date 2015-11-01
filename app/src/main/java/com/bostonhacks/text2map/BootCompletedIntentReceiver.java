package com.bostonhacks.text2map;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class BootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent pushIntent = new Intent(context, SMSListener.class);
            context.startService(pushIntent);
            Toast.makeText(context,"Service Started for SMS Listener",Toast.LENGTH_SHORT);
        }
    }
}