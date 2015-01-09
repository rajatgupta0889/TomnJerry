package online.daing.onlinedating;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CoffeeMeetUpActivity extends Activity {
	LinearLayout setDateLayout;
	LinearLayout setLocationLayout;
	Button askButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coffee_meet);
		TextView userNameTV = (TextView) findViewById(R.id.coffeeMeetUserName);
		ImageView userIconIV = (ImageView) findViewById(R.id.coffeeMeetUserIcon);

		setDateLayout = (LinearLayout) findViewById(R.id.setDateLayout);
		setDateLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						CoffeeSetDateActivity.class);
				startActivity(intent);
			}
		});
		setLocationLayout = (LinearLayout) findViewById(R.id.setLocationLayout);
		setLocationLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						LocationSettingActivity.class);
				startActivity(intent);
			}
		});
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle !=null &&!bundle.isEmpty()) {
			userNameTV.setText(bundle.getString(HomeFragment.intentNameTag));
			userIconIV.setImageResource(bundle.getInt("ImageIcon"));
		}
		askButton = (Button) findViewById(R.id.setSettingButton);
		askButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "A request for coffee",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onNavigateUpFromChild(Activity child) {
		// TODO Auto-generated method stub
		return super.onNavigateUpFromChild(child);
	}

}
