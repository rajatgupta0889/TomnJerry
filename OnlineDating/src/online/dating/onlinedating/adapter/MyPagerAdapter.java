package online.dating.onlinedating.adapter;

import online.daing.onlinedating.HomeFragment;
import online.daing.onlinedating.MyFragment;
import online.daing.onlinedating.R;
import online.dating.onlinedating.model.MyLinearLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MyPagerAdapter extends FragmentPagerAdapter implements
		ViewPager.OnPageChangeListener {
	private MyLinearLayout cur = null;
	private MyLinearLayout next = null;
	private HomeFragment context;
	private FragmentManager fm;
	private float scale;

	public MyPagerAdapter(HomeFragment context, FragmentManager fm) {
		super(fm);
		this.fm = fm;
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		// make the first pager bigger than others
		if (position == HomeFragment.FIRST_PAGE)
			scale = HomeFragment.BIG_SCALE;
		else
			scale = HomeFragment.SMALL_SCALE;

		position = position % HomeFragment.PAGES;
		return MyFragment.newInstance(context, position, scale);
	}

	@Override
	public int getCount() {
		return HomeFragment.PAGES * HomeFragment.LOOPS;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (positionOffset >= 0f && positionOffset <= 1f) {
			cur = getRootView(position);
			next = getRootView(position + 1);

			cur.setScaleBoth(HomeFragment.BIG_SCALE - HomeFragment.DIFF_SCALE
					* positionOffset);
			next.setScaleBoth(HomeFragment.SMALL_SCALE
					+ HomeFragment.DIFF_SCALE * positionOffset);
		}
	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	private MyLinearLayout getRootView(int position) {
		return (MyLinearLayout) fm
				.findFragmentByTag(this.getFragmentTag(position)).getView()
				.findViewById(R.id.root);
	}

	private String getFragmentTag(int position) {
		return "android:switcher:" + context.pager.getId() + ":" + position;
	}
}
