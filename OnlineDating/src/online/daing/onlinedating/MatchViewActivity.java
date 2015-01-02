package online.daing.onlinedating;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MatchViewActivity extends Activity {
	ImageView likeImageView;
	ImageView disLikeImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_match_info);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb4e6a")));
		likeImageView = (ImageView) findViewById(R.id.userLikeImageView);
		likeImageView.setOnClickListener(new OnClickListener() {
			Boolean imageLike = true;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (imageLike) {
					likeImageView
							.setImageResource(R.drawable.ic_user_like_selected);
					disLikeImageView.setVisibility(View.GONE);
					imageLike = false;
				} else if (!imageLike) {
					likeImageView.setImageResource(R.drawable.ic_user_like);
					disLikeImageView.setVisibility(View.VISIBLE);;

					imageLike = true;
				}
			}
		});
		disLikeImageView = (ImageView) findViewById(R.id.userDisLikeImageView);
		disLikeImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						UserDislikeActivity.class);
				startActivity(intent);
			}
		});
	}

}
