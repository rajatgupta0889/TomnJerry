package online.daing.onlinedating;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.model.ServiceHandler;
import online.dating.onlinedating.model.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfilePicActivity extends Activity implements OnClickListener {
	ImageView profilePicImageView, profilePicImageView1, profilePicImageView2,
			profilePicImageView3, profilePicImageView4, profilePicImageView5;
	ImageView selectedView;
	private static final int SELECT_FILE = 1;
	Bitmap bm;
	String mCurrentPhotoPath;

	private int REQUEST_CAMERA = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_images);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
		init();
		Intent intent = getIntent();
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				String user = bundle.getString("user");
				if (User.tom == null) {
					try {
						User.tom = User.getUser(new JSONObject(user));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				if (User.tom == null) {
					SharedPreferences pref = getSharedPreferences("pref", 0);
					String user = pref.getString(GetUserLogin.UserTom, null);
					if (user != null) {
						User.tom = User.getUser(user);
					}
				}
			}
			SharedPreferences profilePref = getSharedPreferences("profilePref",
					0);
			String image_url = profilePref.getString("profilePic", "");
			ImageLoader imageLoader = new ImageLoader(this);
			imageLoader.DisplayImage(image_url,
					R.drawable.com_facebook_profile_default_icon,
					profilePicImageView);
			System.out.println();
			if (User.tom.getImageList().size() > 1) {
				imageLoader.DisplayImage(User.tom.getImageList().get(1),
						R.drawable.com_facebook_profile_default_icon,
						profilePicImageView2);
				profilePicImageView5.setTag(R.id.acceptButton, User.tom
						.getImageList().get(1));

			}

			if (User.tom.getImageList().size() > 2) {
				imageLoader.DisplayImage(User.tom.getImageList().get(2),
						R.drawable.com_facebook_profile_default_icon,
						profilePicImageView2);
				profilePicImageView5.setTag(R.id.acceptButton, User.tom
						.getImageList().get(2));

			}
			if (User.tom.getImageList().size() > 3) {
				imageLoader.DisplayImage(User.tom.getImageList().get(3),
						R.drawable.com_facebook_profile_default_icon,
						profilePicImageView3);
				profilePicImageView5.setTag(R.id.acceptButton, User.tom
						.getImageList().get(3));

			}
			if (User.tom.getImageList().size() > 4) {
				imageLoader.DisplayImage(User.tom.getImageList().get(4),
						R.drawable.com_facebook_profile_default_icon,
						profilePicImageView4);
				profilePicImageView5.setTag(R.id.acceptButton, User.tom
						.getImageList().get(4));

			}
			if (User.tom.getImageList().size() > 5) {
				imageLoader.DisplayImage(User.tom.getImageList().get(5),
						R.drawable.com_facebook_profile_default_icon,
						profilePicImageView5);
				profilePicImageView5.setTag(R.id.acceptButton, User.tom
						.getImageList().get(5));
			}
		}
		/* Setting on click Listener */
		profilePicImageView.setOnClickListener(this);
		profilePicImageView1.setOnClickListener(this);
		profilePicImageView2.setOnClickListener(this);
		profilePicImageView3.setOnClickListener(this);
		profilePicImageView4.setOnClickListener(this);
		profilePicImageView5.setOnClickListener(this);
	}

	/* Intialization the imageView */
	public void init() {
		profilePicImageView = (ImageView) findViewById(R.id.ProfilePicImageView);
		profilePicImageView1 = (ImageView) findViewById(R.id.ProfilePicImageView1);
		profilePicImageView2 = (ImageView) findViewById(R.id.ProfilePicImageView2);
		profilePicImageView3 = (ImageView) findViewById(R.id.ProfilePicImageView3);
		profilePicImageView4 = (ImageView) findViewById(R.id.ProfilePicImageView4);
		profilePicImageView5 = (ImageView) findViewById(R.id.ProfilePicImageView5);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		selectedView = (ImageView) v;
		selectedView.setBackgroundColor(R.drawable.image_border);
		switch (v.getId()) {
		case R.id.ProfilePicImageView:
			System.out.println("Profile Pic View Clicked");
			break;
		case R.id.ProfilePicImageView1:
			if (selectedView.getTag(R.id.acceptButton) == null) {
				selectImage();
			} else {
				profilePicImageView
						.setImageDrawable(selectedView.getDrawable());
				setAsProfile();
			}
			break;
		case R.id.ProfilePicImageView2:
			if (selectedView.getTag(R.id.acceptButton) == null) {
				selectImage();
			} else {
				profilePicImageView
						.setImageDrawable(selectedView.getDrawable());
				setAsProfile();
			}
			break;
		case R.id.ProfilePicImageView3:
			if (selectedView.getTag(R.id.acceptButton) == null) {
				selectImage();
			} else {
				profilePicImageView
						.setImageDrawable(selectedView.getDrawable());
				setAsProfile();
			}
			break;

		case R.id.ProfilePicImageView4:
			if (selectedView.getTag(R.id.acceptButton) == null) {
				selectImage();
			} else {
				profilePicImageView
						.setImageDrawable(selectedView.getDrawable());
				setAsProfile();
			}
			break;
		case R.id.ProfilePicImageView5:
			if (selectedView.getTag(R.id.acceptButton) == null) {
				selectImage();
			} else {
				profilePicImageView
						.setImageDrawable(selectedView.getDrawable());
				setAsProfile();
			}
			break;
		default:
			Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
			break;
		}

	}

	private void setAsProfile() {
		// TODO Auto-generated method stub
		final CharSequence[] items = { "Set As Profile Picture",
				"Change Picture", "Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Set As Profile Picture")) {
					SharedPreferences profilePref = getSharedPreferences(
							"profilePref", 0);
					SharedPreferences.Editor edit = profilePref.edit();
					edit.putString("profilePic",
							selectedView.getTag(R.id.acceptButton).toString());
					edit.commit();
				} else if (items[item].equals("Change Picture")) {
					selectImage();
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	/**
	 * helper to retrieve the path of an image URI
	 */

	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public String getPath(Uri uri) {
		// just some safety built in
		if (uri == null) {
			// TODO perform some logging or show user feedback
			return null;
		}
		// try to retrieve the image from the media store first
		// this will only work for images selected from gallery
		String[] projection = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		// this is our fallback here
		return uri.getPath();
	}

	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
							.format(new Date());
					String imageFileName = "JPEG_" + timeStamp + "_";

					File storageDir = Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
					File image = null;
					try {
						image = File.createTempFile(imageFileName, /* prefix */
								".jpg", /* suffix */
								storageDir /* directory */

						);
						mCurrentPhotoPath = "file:" + image.getAbsolutePath();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (image != null) {
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(image));
						startActivityForResult(intent, REQUEST_CAMERA);
					}
				} else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CAMERA) {

				galleryAddPic();
				try {
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
					int targetW = selectedView.getWidth();
					int targetH = selectedView.getHeight();
					bm = BitmapFactory.decodeFile(mCurrentPhotoPath,
							btmapOptions);
					int photoW = btmapOptions.outWidth;
					int photoH = btmapOptions.outHeight;

					// Determine how much to scale down the image
					int scaleFactor = Math.min(photoW / targetW, photoH
							/ targetH);

					new AsyncTask<Void, Void, Void>() {

						@Override
						protected Void doInBackground(Void... params) {
							// TODO Auto-generated method stub
							sendImageTOServer(bm);
							return null;
						}

					};
					// bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
					btmapOptions.inJustDecodeBounds = false;
					btmapOptions.inSampleSize = scaleFactor;
					btmapOptions.inPurgeable = true;

					selectedView.setImageBitmap(bm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == SELECT_FILE) {
				Uri selectedImageUri = data.getData();

				String tempPath = getPath(selectedImageUri, this);
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				if (selectedView != null) {
					selectedView.setImageBitmap(bm);
					new AsyncTask<Void, Void, Void>() {

						@Override
						protected Void doInBackground(Void... params) {
							// TODO Auto-generated method stub
							sendImageTOServer(bm);
							return null;
						}

					};
				}
			}
		}
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	private void sendImageTOServer(Bitmap userIcon) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		userIcon.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		JSONStringer data = null;
		try {
			data = new JSONStringer().object().key("data").value(imageEncoded)
					.endObject();
			ServiceHandler sh = new ServiceHandler();
			String result = sh.makeServiceCall(GetUserLogin.url + "image",
					ServiceHandler.POST, data);
			System.out.println("Result " + result);
		} catch (JSONException e) {
			// TODO
			// Auto-generated
			// catch block
			e.printStackTrace();
		}
	}
}
