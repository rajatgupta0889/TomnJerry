package online.dating.onlinedating.Service;

import online.daing.onlinedating.GetUserLogin;
import online.daing.onlinedating.OnTaskCompleted;
import online.dating.onlinedating.model.ServiceHandler;
import android.os.AsyncTask;
import android.util.Log;

public class GetMatchService extends AsyncTask<String, Void, String> {
	private OnTaskCompleted listener;
	String resultType;

	public void setListener(OnTaskCompleted listener) {
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		// System.out.println(result);
		if (result != null) {

			listener.onResult(result, resultType);

		}
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String result = null;
		ServiceHandler sh = new ServiceHandler();
		System.out.println("Param " + params[0] + "  " + params[1]);
		// System.out.println(GetUserLogin.url + "buddy/" + params[0]);
		resultType = params[1];
		if (params[1].equals("match")) {
			Log.d("URL", GetUserLogin.url + "coffee/" + params[0]);
			result = sh.makeServiceCall(GetUserLogin.url + "match-detail/"
					+ params[0], ServiceHandler.GET);
		} else {
			Log.d("URL", GetUserLogin.url + "coffee/" + params[0]);
			result = sh.makeServiceCall(GetUserLogin.url + "coffee/"
					+ params[0], ServiceHandler.GET);

		}
		// Log.d("Result", result);
		return result;
	}

}