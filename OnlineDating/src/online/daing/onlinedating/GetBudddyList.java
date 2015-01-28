package online.daing.onlinedating;

import online.dating.onlinedating.model.ServiceHandler;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class GetBudddyList extends AsyncTask<Void, Void, String> {
	private OnTaskCompleted listener;

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
		if(result != null){
			listener.OnResult(result);
		}
	}

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String result = null;
		ServiceHandler sh = new ServiceHandler();
		result = sh.makeServiceCall(GetUserLogin.url + "get-buddy-list",
				ServiceHandler.GET);
		Log.d("BuddyList", result);
		return result;
	}

}
