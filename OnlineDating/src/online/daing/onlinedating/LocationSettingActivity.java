package online.daing.onlinedating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import online.dating.onlinedating.Service.PlacesService;
import online.dating.onlinedating.adapter.PlacesAutoCompleteAdapter;
import online.dating.onlinedating.model.GPSTracker;
import online.dating.onlinedating.model.Place;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView.Tokenizer;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationSettingActivity extends Activity implements
		OnMapReadyCallback, OnItemClickListener, OnMarkerClickListener {
	GPSTracker gps;
	double latitude;
	double longitude;
	MapFragment mMapFragment;
	GoogleMap mGoogleMap;
	MultiAutoCompleteTextView locationTV;
	Button locationSearchButton, locationSetButton;
	final String TAG = "Location Setting";
	Bundle intentData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_setting);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		locationTV = (MultiAutoCompleteTextView) findViewById(R.id.locationSearchEditTextView);
		PlacesAutoCompleteAdapter adapter = new PlacesAutoCompleteAdapter(
				getApplicationContext(), R.layout.locatio_list_item);
		locationTV.setAdapter(adapter);
		locationTV.setThreshold(2);
		locationTV.setOnItemClickListener(this);
		locationTV.setTokenizer(new Tokenizer() {

			@Override
			public CharSequence terminateToken(CharSequence text) {
				// TODO Auto-generated method stub
				return text;
			}

			@Override
			public int findTokenStart(CharSequence text, int cursor) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int findTokenEnd(CharSequence text, int cursor) {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		Intent intent = getIntent();
		intentData = intent.getExtras();

		locationSetButton = (Button) findViewById(R.id.setLocationButton);
		locationSetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String location = locationTV.getText().toString();
				LatLng latlng = getLocationFromAddress(location);
				if (!location.isEmpty() && latlng != null) {
					LatLng locLatLng = (LatLng) locationTV.getTag();
					
					Intent intent = new Intent();
					intent.putExtra("Address", location);
					intent.putExtra("locaLat", locLatLng.latitude);
					intent.putExtra("locaLong", locLatLng.longitude);
					setResult(RESULT_OK, intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Please Select Correct Location", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
		locationSearchButton = (Button) findViewById(R.id.searchLocationButton);
		locationSearchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mGoogleMap.clear();
				String location = locationTV.getText().toString();
				setCameraPostion(location, (LatLng) locationTV.getTag());
			}
		});

		mMapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);
		mMapFragment.getMapAsync(this);

		mGoogleMap = mMapFragment.getMap();
		mGoogleMap.setMyLocationEnabled(true);
		mGoogleMap.setOnMarkerClickListener(this);
	}

	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		String str = (String) adapterView.getItemAtPosition(position);
		locationTV.setText(str);
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub
		gps = new GPSTracker(getApplicationContext());
		/*
		 * if(Session.getActiveSession() != null){ startActivity(new
		 * Intent(getActivity(),LoginActivity.class)); }
		 */
		Log.i("LocationSetting", "Inside Map ready");

		if (gps.canGetLocation()) {
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			// map.addMarker(new MarkerOptions().position(
			// new LatLng(latitude, longitude)).title("Your Position"));
			// CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(
			// latitude, longitude));
			// CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
			// map.moveCamera(center);
			// map.animateCamera(zoom);
			if (intentData == null) {
				new GetPlaces(this, "cafe").execute();
			} else {
				locationTV.setText(intentData.getString("Address"));
				setCameraPostion(locationTV.getText().toString(), null);
			}
		} else {
			gps.showSettingsAlert();
		}

	}

	private class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {

		private ProgressDialog dialog;

		private Context context;
		private String places;

		public GetPlaces(Context context, String places) {

			this.context = context;

			this.places = places;

		}

		@Override
		protected void onPostExecute(ArrayList<Place> result) {

			super.onPostExecute(result);

			if (dialog.isShowing()) {

				dialog.dismiss();

			}

			for (int i = 0; i < result.size(); i++) {
				mGoogleMap.addMarker(new MarkerOptions()
						.title(result.get(i).getName())
						.position(
								new LatLng(result.get(i).getLatitude(), result
										.get(i).getLongitude()))
						.snippet(result.get(i).getVicinity()));

			}
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(result.get(0).getLatitude(), result.get(
							0).getLongitude())) // Sets the center of the map to
					.zoom(14) // Sets the zoom
					.tilt(30) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder
			mGoogleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			dialog = new ProgressDialog(context);
			dialog.setCancelable(false);
			dialog.setMessage("Loading..");
			dialog.isIndeterminate();
			dialog.show();

		}

		@Override
		protected ArrayList<Place> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			PlacesService service = new PlacesService(
					"AIzaSyA3WfMGR7kx8BZuM4mFEzHFmDiUSylCdnU");
			ArrayList<Place> findPlaces = service.findPlaces(latitude,
					longitude, places); // 28.632808

			// for (int i = 0; i < findPlaces.size(); i++) {
			//
			// Place placeDetail = findPlaces.get(i);
			//
			// Log.e(TAG, "places : " + placeDetail.getName());
			//
			// }
			return findPlaces;

		}
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		// locationTV.setText(marker.getTitle());
		marker.showInfoWindow();
		locationTV.setText(marker.getTitle() + "," + marker.getSnippet());
		locationTV.setTag(marker.getPosition());
		return true;
	}

	public LatLng getLocationFromAddress(String strAddress) {

		Geocoder coder = new Geocoder(this);
		List<Address> address;
		LatLng p1 = null;

		try {
			address = coder.getFromLocationName(strAddress, 5);
			if (address == null || address.isEmpty()) {
				return null;
			}
			Address location = address.get(0);
			location.getLatitude();
			location.getLongitude();

			p1 = new LatLng(latitude, longitude);	
			System.out.println(p1);

			return p1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p1;
	}

	private void setCameraPostion(String location, LatLng latlng) {
		if (latlng == null) {
			latlng = getLocationFromAddress(location);
		}
		if (latlng != null) {
			if (location.contains(",")) {
				String[] locationArray = location.split(",");
				mGoogleMap.addMarker(new MarkerOptions()
						.title(locationArray[0]).position(latlng)
						.snippet(locationArray[1]));
			} else {
				mGoogleMap.addMarker(new MarkerOptions().title(location)
						.position(latlng).snippet(location));
			}
			mGoogleMap.addMarker(new MarkerOptions().title("").position(latlng)
					.snippet(""));

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(latlng) // Sets the center of the map to
					.zoom(14) // Sets the zoom
					.tilt(30) // Sets the tilt of the camera to 30
								// degrees
					.build(); // Creates a CameraPosition from the
								// builder
			mGoogleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

		}
	}
}