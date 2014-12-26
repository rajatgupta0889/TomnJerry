package online.dating.onlinedating.Service;

import online.daing.onlinedating.MainActivity;
import online.daing.onlinedating.R;
import online.dating.onlinedating.broadcastReciever.GcmBroadcastReceiver;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.renderscript.Type;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

public class GcmIntentService extends IntentService {

	public GcmIntentService() {
		super("GcmIntentService");
		// TODO Auto-generated constructor stub
	}

	public static String TAG = "GCMIntentService";
	private NotificationManager mNotificationManager;
	public static final int NOTIFICATION_ID = 1;
	NotificationCompat.Builder builder;
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver

		String messageType = gcm.getMessageType(intent);

		if (extras.isEmpty()) {
			/*
			 * filter message based on message Type. Since it is likely that GCM
			 * will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send Error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messagess on server: "
						+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				for (int i = 0; i < 5; i++) {
					Log.i(TAG,
							"Working ... " + (i + 1) + "/5 @"
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException exp) {

					}

				}
				Log.i(TAG,"Completed work @ " + SystemClock.elapsedRealtime());
				sendNotification("Received:" + extras.toString());
				Log.i(TAG,"Recieved @ " + extras.toString());
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {
		// TODO Auto-generated method stub
		mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent contentIntent = PendingIntent.getActivity(this,0, new Intent(this,MainActivity.class), 0);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_settings)
				.setContentTitle("GCM Notificaiton")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);
		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

}
