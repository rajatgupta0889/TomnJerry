package online.daing.onlinedating;

import online.dating.onlinedating.model.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

class GetUserLogin extends AsyncTask<Void, Void, Void> {
	Context context;
	ProgressDialog proDialog;
	private static String url = "http://54.88.90.102:1337/";
	private JSONStringer vm;
	private OnTaskCompleted listener;

	public GetUserLogin(Context context, ProgressDialog proDialog,
			JSONStringer vm) {
		super();
		this.context = context;
		this.proDialog = proDialog;
		this.vm = vm;
	}

	public void setListener(OnTaskCompleted listener) {
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		proDialog = new ProgressDialog(context);
		proDialog.setMessage("Logging In");
		proDialog.setCanceledOnTouchOutside(false);
		proDialog.show();

		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub

		super.onPostExecute(result);
		proDialog.dismiss();
		proDialog.cancel();
		listener.onTaskCompleted();

	}

	String result;

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub

		ServiceHandler sh = new ServiceHandler();
		result = sh.makeServiceCall(url + "login", ServiceHandler.POST, vm);
		Log.d("AsynTAsk", result);
		// System.out.println(result);
		try {
			if (result != null) {
				JSONObject res = new JSONObject(result);

				if (res.getString("id") != null) {

					Intent intent = new Intent(context, LoginActivity.class);

					SharedPreferences pref = context.getSharedPreferences(
							"pref", 0);
					System.out.println("pref " + pref);
					SharedPreferences.Editor editor = pref.edit();

					editor.putString("Id", res.getString("id"));
					editor.putString("fbUserId", res.getString("fbUserId"));
					editor.putString("name", res.getString("name"));
					editor.putString("email", res.getString("email"));
					editor.commit();

					intent.putExtra("name", res.getString("name"));
					intent.putExtra("orientation", res.getString("orientation"));
					intent.putExtra("fbUserId", res.getString("fbUserId"));
					intent.putExtra("email", res.getString("email"));
					intent.putExtra("id", res.getString("id"));
					intent.putExtra("gender", res.getString("gender"));
					Log.i("Splash", pref.getString("Id", ""));

					context.startActivity(intent);
					((Activity) context).finish();
				}

			} else {

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(result);
		return null;
	}
}