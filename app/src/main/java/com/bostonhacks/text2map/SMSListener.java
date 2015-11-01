package com.bostonhacks.text2map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by David Liu on 10/31/2015.
 */
public class SMSListener extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            String[] identifier = new String[2];
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    String msgBody ="";
                    long id = -1;
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        if(msg_from.contains(context.getString(R.string.twilio_number))) {
                            if (i == 0) {
                                String a = msgs[i].getMessageBody();
                                int x =  a.indexOf("\n");
                                identifier = a.substring(0,x).split(" ");
                                id = Long.parseLong(identifier[1]);
                                msgBody += a.substring(a.indexOf("\n")+1);
                            } else {
                                msgBody += msgs[i].getMessageBody();
                            }
                        }
                    }
                    Direction direction = new Direction();
                    direction.response = msgBody;
                    switch (identifier[0].charAt(0)){
                        case 'r':
                            direction.type = Direction.TYPE.R;
                            break;
                        case 'f':
                            direction.type = Direction.TYPE.F;
                            break;
                    }
                    direction.setID(id);
                    direction.record(context);

                }catch(Exception e){
                            Log.d("Exception caught", e.getMessage());
                }
            }
        }
    }
}
