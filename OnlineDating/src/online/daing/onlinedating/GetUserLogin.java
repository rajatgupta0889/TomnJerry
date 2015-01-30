package online.daing.onlinedating;

import online.dating.onlinedating.model.ServiceHandler;

import org.json.JSONStringer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.ProgressBar;

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
			proDialog = new ProgressDialog(context);
			proDialog.setMessage("Logging In");
			proDialog.setCanceledOnTouchOutside(false);
			proDialog.show();
		}
		super.onPreExecute();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub

		super.onPostExecute(result);
		if (proDialog != null) {
			proDialog.dismiss();

			proDialog.cancel();
		}
		if (result != null) {
			listener.onTaskCompleted();
			listener.OnResult(result);
		} else {

			new AlertDialog.Builder(context)
					.setTitle("Error in Connection")
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

	String result;

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub

		ServiceHandler sh = new ServiceHandler();
		result = sh.makeServiceCall(url + "login", ServiceHandler.POST, vm);
		// Log.d("AsynTAsk", result);
		// System.out.println(result);
		// try {
		// if (result != null) {
		// JSONObject res = new JSONObject(result);
		//
		// if (res.getString("id") != null) {
		//
		// if (MainFragement.tom != null) {
		// ArrayList<String> temp = new ArrayList<String>();
		// System.out.println("Login Result" + res);
		// JSONArray imageArray = res.getJSONArray("images");
		// for (int i = 0; i < imageArray.length(); i++) {
		// temp.add(imageArray.getString(0));
		// }
		// MainFragement.tom.setImageList(temp);
		// Intent intent = new Intent(context, LoginActivity.class);
		//
		// MainFragement.tom.setUserToken(res.getString("id"));
		//
		// SharedPreferences pref = context.getSharedPreferences(
		// "pref", 0);
		// SharedPreferences.Editor editor = pref.edit();
		// editor.putString(UserTom, MainFragement.tom.toString());
		// editor.commit();
		// context.startActivity(intent);
		// ((Activity) context).finish();
		// }
		//
		// }
		//
		// } else {
		//
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		//
		// }

		// System.out.println(result);
		return result;
	}

}