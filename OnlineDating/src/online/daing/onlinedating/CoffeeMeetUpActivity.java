package online.daing.onlinedating;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CoffeeMeetUpActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coffee_meet);
		TextView userNameTV= (TextView) findViewById(R.id.coffeeMeetUserName);
		ImageView userIconIV = (ImageView) findViewById(R.id.coffeeMeetUserIcon);
		
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if(!bundle.isEmpty()){
			userNameTV.setText(bundle.getString("Name"));
			userIconIV.setImageResource(bundle.getInt("ImageIcon"));
		}
		
	}

	
}
