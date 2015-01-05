package online.daing.onlinedating;

import online.dating.onlinedating.model.GPSTracker;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationSettingActivity extends Activity implements
		OnMapReadyCallback {
	GPSTracker gps;
	double latitude;
	double longitude;
	MapFragment mMapFragment;
	GoogleMap mGoogleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_setting);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
		mMapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);
		mMapFragment.getMapAsync(this);

		mGoogleMap = mMapFragment.getMap();
		mGoogleMap.setMyLocationEnabled(true);

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

			map.addMarker(new MarkerOptions().position(
					new LatLng(latitude, longitude)).title("Marker"));
			CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(
					latitude, longitude));
			CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
			map.moveCamera(center);
			map.animateCamera(zoom);
		} else {
			gps.showSettingsAlert();
		}

	}
}
