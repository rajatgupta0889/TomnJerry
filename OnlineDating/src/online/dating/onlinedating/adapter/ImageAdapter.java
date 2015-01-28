package online.dating.onlinedating.adapter;

import java.util.ArrayList;

import online.daing.onlinedating.R;
import online.dating.onlinedating.Service.ImageLoader;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageAdapter extends PagerAdapter {

	Context context;
	ArrayList<String> imageUrl;
	LayoutInflater inflater;

	public ImageAdapter(Context context, ArrayList<String> imageUrl) {
		this.context = context;
		this.imageUrl = imageUrl;

	}

	@Override
	public int getCount() {
		return imageUrl.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		// Declare Variables

		ImageView profileImage;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.imageview, container, false);

		// Locate the TextViews in viewpager_item.xml

		// Locate the ImageView in viewpager_item.xml
		profileImage = (ImageView) itemView
				.findViewById(R.id.profileMatchImageView);
		// Capture position and set to the ImageView
//		ImageLoader imageLoader = new ImageLoader(context);
//		imageLoader.DisplayImage(imageUrl.get(position),
//				R.drawable.com_facebook_profile_default_icon, profileImage);
		profileImage.setImageResource(R.drawable.user_profile);
		// Add viewpager_item.xml to ViewPager
		((ViewPager) container).addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((RelativeLayout) object);

	}

}
