package online.daing.onlinedating;

import online.dating.onlinedating.Service.GetMatchService;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService implements OnTaskCompleted {

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
		Log.d("Intent", messageType);
		if (!extras.isEmpty()) {
			/*
			 * filter message based on message Type. Since it is likely that GCM
			 * will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send Error: " + extras.toString(), "");
				Log.d(TAG, messageType);
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification(
						"Deleted messagess on server: " + extras.toString(), "");
				Log.d(TAG, messageType);
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				Log.d(TAG, extras.getString("message"));
				// Log.d(TAG, extras.toString());

				GetMatchService match = new GetMatchService();
				String message = extras.getString("message");
				match.setListener(this);
				if (message.contains("match")) {
					match.execute(message.split(":")[1], "match");
				}
				if (message.contains("coffee")) {
					System.out.println("Inside message coffee "
							+ message.split(":")[1]);
					match.execute(message.split(":")[1], "coffee");
				} else {
					sendNotification(message, "");
				}

				// for (int i = 0; i < 5; i++) {
				// Log.i(TAG,
				// "Working ... " + (i + 1) + "/5 @"
				// + SystemClock.elapsedRealtime());
				// try {
				// Thread.sleep(5000);
				//
				// } catch (InterruptedException exp) {
				//
				// }
				//
				// }
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				Log.i(TAG, "Recieved @ " + extras.toString());
			}

		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg, String resultType) {
		// TODO Auto-generated method stub
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = null;
		String message = null;
		intent = new Intent(this, LoginActivity.class);
		Log.d(TAG, msg);
		Log.d(TAG, resultType);
		if (resultType.equals("match")) {
			SharedPreferences pref = getApplicationContext()
					.getSharedPreferences("matchPref", 0);
			SharedPreferences.Editor editor = pref.edit();
			editor.putString("MatchInfo", msg);
			editor.commit();
			message = "You got the new Match ";
			Log.d("Result TYpe ", resultType);

			try {
				JSONObject obj = new JSONObject(msg);
				message = message + obj.getString("name");
				intent.putExtra("Match", message);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (resultType.equals("coffee")) {
			message = "You got the coffee request from ";
			try {
				JSONObject obj = new JSONObject(msg);
				JSONObject invitee = obj.getJSONObject("invitee");
				message = message + invitee.getString("name");
				intent.putExtra("coffee", message);
				System.out.println("Message " + message);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			message = msg;
		}
		intent.putExtra("resultType", resultType);
		intent.putExtra("match", msg);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				.setSmallIcon(R.drawable.coffe_pink)
				.setContentTitle("Dating App Notification")
				.setStyle(
						new NotificationCompat.BigTextStyle().bigText(message))
				.setContentText(message)
				.setAutoCancel(true)
				.setSound(
						RingtoneManager
								.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

	}

	@Override
	public void onTaskCompleted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnResult(String result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResult(String result, String resultType) {
		// TODO Auto-generated method stu

		sendNotification(result, resultType);
	}
}
