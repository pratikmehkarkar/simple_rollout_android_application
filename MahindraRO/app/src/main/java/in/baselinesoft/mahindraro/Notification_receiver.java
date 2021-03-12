package in.baselinesoft.mahindraro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Pratik on 21/03/2018.
 */

public class Notification_receiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context,MainNavigation.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent1 = PendingIntent.getActivity(context,101,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmsound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.blood);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent1)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("M-Ro")
                .setSound(alarmsound)
                .setContentText("Check your Roll-out !")
                .setAutoCancel(true);
        builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(100,builder.build());




    }
}
