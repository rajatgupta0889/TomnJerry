package online.daing.onlinedating;

import online.dating.onlinedating.Service.ConnectionDetector;
import online.dating.onlinedating.model.ServiceHandler;

import org.json.JSONStringer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class GetUserLogin extends AsyncTask<Void, Void, String> {
	Context context;
	ProgressDialog proDialog;
	public static final String url = "http://54.88.90.102:1337/";
	private JSONStringer vm;
	private OnTaskCompleted listener;
	public static final String UserTom = "Tom";

	public GetUserLogin(Context context, ProgressDialog proDialog,
			JSONStringer vm) {
		super();
		this.context = context;
		this.proDialog = proDialog;
		proDialog = new ProgressDialog(context);
		this.vm = vm;
	}

	public GetUserLogin(Context context, JSONStringer vm) {
		super();
		this.context = context;
		this.vm = vm;
	}

	public void setListener(OnTaskCompleted listener) {
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		if (proDialog != null) {
			proDialog.setMessage("Logging In");
			proDialog.setCanceledOnTouchOutside(false);
			proDialog.show();
		}
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub

		super.onPostExecute(result);
		if (proDialog != null) {
			proDialog.dismiss();

			proDialog.cancel();
		}
		if (result != null) {
			if (!result.contains("error")) {
				listener.onTaskCompleted();
				listener.OnResult(result);
			} else {
				new AlertDialog.Builder(context)
						.setTitle("Error")
						.setMessage("Please Check your GPS setting")
						.setPositiveButton(android.R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// continue with delete
										((Activity) context).finish();
									}
								}).setIcon(android.R.drawable.ic_dialog_alert)
						.show();
			}
		} else {

			new AlertDialog.Builder(context)
					.setTitle("Error in connection")
					.setMessage("Login after sometime")
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// continue with delete
									((Activity) context).finish();
								}
							}).setIcon(android.R.drawable.ic_dialog_alert)
					.show();

		}
	}

	String result = null;

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		ConnectionDetector cd = new ConnectionDetector(context);
		if (cd.isConnectingToInternet()) {
			ServiceHandler sh = new ServiceHandler();
			result = sh.makeServiceCall(url + "login", ServiceHandler.POST, vm);

		}
		return result;
	}

}