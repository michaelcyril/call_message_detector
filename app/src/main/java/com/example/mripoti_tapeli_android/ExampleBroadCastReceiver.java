package com.example.mripoti_tapeli_android;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class ExampleBroadCastReceiver extends BroadcastReceiver {
//    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
//        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
//            Toast.makeText(context, "Boot completed", Toast.LENGTH_SHORT).show();
//        }
//        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
//            Toast.makeText(context, "Connectivity changed", Toast.LENGTH_SHORT).show();
//        }

        // ******** This check if the message is received by the phone ********
        if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
            boolean phone_received = false;
            String phoneN = "";
            String message = "";

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String phoneNumber = smsMessage.getDisplayOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();
                        phoneN = phoneNumber;
                        message = messageBody;

                        // Process the phone number and message body
                        // For example, log them or display them in a toast
                    }
                    phone_received = true;
                }
            }

            // ******* This to check if phone number is in your contact ********
            boolean isInContacts = ContactUtils.isPhoneNumberInContacts(context, phoneN);
            if (isInContacts){

                //  ****** This is for permission *********

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }
                if(phone_received){

                    //  ****** This is for permission *********
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {

                        // ***** This is to create the contents of the notification ********
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Angalizo"+phoneN.toString())
                                .setContentText("Ujumbe huu unaweza kuwa wa utapeli \n namba: 0693331836\n ujumbe: Mimi mama mwenye nyumba ile kodi tuma humu mwanangu.")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        // ******* This is to direct the screen when the notification is clicked *******
                        Intent newIntent = new Intent(context, MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                        builder.setContentIntent(pendingIntent);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                        notificationManager.notify(1,builder.build());
                    } else {
                        Toast.makeText(context, "permission errors", Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                    Toast.makeText(context, "Phone and message not receive ", Toast.LENGTH_SHORT).show();
                }

            }else {

            }
        }







        // ****************  For incoming calls ***************************
        if(action.equals("android.intent.action.PHONE_STATE")){

            String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (phoneNumber != null) {
                System.out.println("HEY -----------------------------");
                Toast.makeText(context, phoneNumber, Toast.LENGTH_SHORT).show();
                //  ****** This is for permission *********

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }

                //  ****** This is for permission *********
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {

                    // ***** This is to create the contents of the notification ********
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("Angalizo")
                            .setContentText("Simu hii inaweza kuwa ya utapeli \nnamba: 0693331836" )
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    // ******* This is to direct the screen when the notification is clicked *******
                    Intent newIntent = new Intent(context, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                    builder.setContentIntent(pendingIntent);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(1,builder.build());
                } else {
                    Toast.makeText(context, "permission errors", Toast.LENGTH_SHORT).show();
                }
            }




            }

//        }
    }





}
