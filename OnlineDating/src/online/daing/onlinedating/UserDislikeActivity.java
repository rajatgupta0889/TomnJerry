package online.daing.onlinedating;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class UserDislikeActivity extends Activity {
	Button disLikeRequestButton;
	CheckBox interestCB, positionCB, imageCB, heightCB;
	String reasonForDislike = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_dislike);
		interestCB = (CheckBox) findViewById(R.id.interestCB);
		positionCB = (CheckBox) findViewById(R.id.positionCB);
		imageCB = (CheckBox) findViewById(R.id.imageCB);
		heightCB = (CheckBox) findViewById(R.id.heightCB);
		if (interestCB.isChecked()) {
			reasonForDislike = reasonForDislike + interestCB.getText();
		}
		if (positionCB.isChecked()) {
			reasonForDislike = reasonForDislike + positionCB.getText();
		}
		if (imageCB.isChecked()) {
			reasonForDislike = reasonForDislike + imageCB.getText();
		}
		if (heightCB.isChecked()) {
			reasonForDislike = reasonForDislike + heightCB.getText();
		}
		disLikeRequestButton = (Button) findViewById(R.id.userDislikeButton);
		disLikeRequestButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Reason of DisLike ",
						Toast.LENGTH_SHORT).show();
			}
		});

	}

}
