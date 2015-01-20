package online.dating.onlinedating.adapter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import online.dating.onlinedating.model.GPSTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements
		Filterable {
	private static final String LOG_TAG = "LocationSettingActivity";
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	private static final String API_KEY = "AIzaSyA3WfMGR7kx8BZuM4mFEzHFmDiUSylCdnU";
	private GPSTracker gps;
	private ArrayList<String> resultList;
	private Context context;

	public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.context = context;
	}

	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public String getItem(int index) {
		return resultList.get(index);
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		gps = new GPSTracker(context);

		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					// Retrieve the autocomplete results.
					resultList = autocomplete(constraint.toString());
					// Assign the data to the FilterResults
					filterResults.values = resultList.toArray();
					filterResults.count = resultList.size();
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				if (results != null && results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}

	private ArrayList<String> autocomplete(String input) {
		ArrayList<String> resultList = null;

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE
					+ TYPE_AUTOCOMPLETE + OUT_JSON);
			sb.append("?input=" + URLEncoder.encode(input, "utf8"));
			sb.append("&location=" + gps.getLatitude() + ","
					+ gps.getLongitude());
			sb.append("&key=" + API_KEY);
			Log.d(LOG_TAG, sb.toString());
			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			System.out.println("Connection " + conn);
			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
				System.out.println("Read" + read);
			}
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error connecting to Places API", e);
			return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
			System.out.println("JSON Object " + jsonObj);
			// Extract the Place descriptions from the results
			resultList = new ArrayList<String>(predsJsonArray.length());
			System.out.println(predsJsonArray);
			for (int i = 0; i < predsJsonArray.length(); i++) {
				resultList.add(predsJsonArray.getJSONObject(i).getString(
						"description"));
				Log.d(LOG_TAG,
						predsJsonArray.getJSONObject(i)
								.getString("description"));
			}
		} catch (JSONException e) {
			Log.e(LOG_TAG, "Cannot process JSON results", e);
		}

		return resultList;
	}
}
