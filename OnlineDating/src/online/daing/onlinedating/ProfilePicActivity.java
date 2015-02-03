package online.daing.onlinedating;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import online.dating.onlinedating.Service.ImageLoader;
import online.dating.onlinedating.model.ServiceHandler;
import online.dating.onlinedating.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfilePicActivity extends Activity implements OnClickListener {
	ImageView profilePicImageView, profilePicImageView1, profilePicImageView2,
			profilePicImageView3, profilePicImageView4, profilePicImageView5;
	ImageView selectedView;
	private static final int SELECT_FILE = 1;
	String baseImageUrl = "https://s3-ap-southeast-1.amazonaws.com/tomnjerry/profile-pics/";
	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

	Bitmap bm;
	String mCurrentPhotoPath;

	private int REQUEST_CAMERA = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_images);
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		init();
		Intent intent = getIntent();
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				String user = bundle.getString("user");
				if (User.tom == null) {
					User.tom = User.getUser(user);
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
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
				mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
			} else {
				mAlbumStorageDirFactory = new BaseAlbumDirFactory();
			}
			Log.d("Profile Activity ", " " + User.tom.toString());
			SharedPreferences profilePref = getSharedPreferences("profilePref",
					0);
			String image_url = profilePref.getString("profilePic", "");
			ImageLoader imageLoader = new ImageLoader(this);
			imageLoader.DisplayImage(image_url,
					R.drawable.com_facebook_profile_default_icon,
					profilePicImageView);
			if (User.tom.getImageList().size() > 0) {
				imageLoader.DisplayImage(User.tom.getImageList().get(0),
						R.drawable.com_facebook_profile_default_icon,
						profilePicImageView1);
				profilePicImageView1.setTag(R.id.acceptButton, User.tom
						.getImageList().get(0));
			} else {
				imageLoader.DisplayImage(image_url,
						R.drawable.com_facebook_profile_default_icon,
						profilePicImageView1);
				profilePicImageView1.setTag(R.id.acceptButton, image_url);
			}
			System.out.println(User.tom.getImageList().size());
			if (User.tom.getImageList() != null) {
				if (User.tom.getImageList().size() > 1) {
					imageLoader.DisplayImage(User.tom.getImageList().get(1),
							R.drawable.com_facebook_profile_default_icon,
							profilePicImageView2);
					profilePicImageView2.setTag(R.id.acceptButton, User.tom
							.getImageList().get(1));

				}
				if (User.tom.getImageList().size() > 2) {
					imageLoader.DisplayImage(User.tom.getImageList().get(2),
							R.drawable.com_facebook_profile_default_icon,
							profilePicImageView3);
					profilePicImageView3.setTag(R.id.acceptButton, User.tom
							.getImageList().get(2));

				}
				if (User.tom.getImageList().size() > 3) {
					imageLoader.DisplayImage(User.tom.getImageList().get(3),
							R.drawable.com_facebook_profile_default_icon,
							profilePicImageView4);
					profilePicImageView4.setTag(R.id.acceptButton, User.tom
							.getImageList().get(3));

				}
				if (User.tom.getImageList().size() > 4) {
					imageLoader.DisplayImage(User.tom.getImageList().get(4),
							R.drawable.com_facebook_profile_default_icon,
							profilePicImageView5);
					profilePicImageView5.setTag(R.id.acceptButton, User.tom
							.getImageList().get(4));
				}
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
					File f = null;
					try {

						f = setUpPhotoFile();
						mCurrentPhotoPath = f.getAbsolutePath();
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(f));

						startActivityForResult(intent, REQUEST_CAMERA);
					} catch (IOException e) {
						e.printStackTrace();
						f = null;
						mCurrentPhotoPath = null;
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
				if (mCurrentPhotoPath != null) {
					setPic();
					galleryAddPic();
					mCurrentPhotoPath = null;
				}
			} else if (requestCode == SELECT_FILE) {
				Uri selectedImageUri = data.getData();

				String tempPath = getPath(selectedImageUri, this);
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				if (selectedView != null) {
					selectedView.setImageBitmap(bm);
					sendImageTOServer(bm);
				}
			}
		}
	}

	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			storageDir = mAlbumStorageDirFactory
					.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}

		} else {
			Log.v(getString(R.string.app_name),
					"External storage is not mounted READ/WRITE.");
		}

		return storageDir;
	}

	private String getAlbumName() {
		return getString(R.string.album_name);
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
				albumF);
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {

		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();

		return f;
	}

	private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = selectedView.getWidth();
		int targetH = selectedView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		final Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath,
				bmOptions);

		// TODO Auto-generated method stub
		sendImageTOServer(bitmap);

		/* Associate the Bitmap to the ImageView */
		selectedView.setImageBitmap(bitmap);
		selectedView.setVisibility(View.VISIBLE);
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	private void sendImageTOServer(Bitmap userIcon) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		userIcon.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		JSONStringer data = null;
		try {
			data = new JSONStringer().object().key("data").value(imageEncoded)
					.endObject();
			UpdateImage updateImage = new UpdateImage(getApplicationContext(),
					data);
			updateImage.execute();
		} catch (JSONException e) {
			// TODO
			// Auto-generated
			// catch block
			e.printStackTrace();
		}
	}

	public class UpdateImage extends AsyncTask<Void, String, String> {
		Context context;
		JSONStringer data;

		public UpdateImage(Context context, JSONStringer data) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this.data = data;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				try {
					JSONArray obj = new JSONArray(result);
					JSONObject jsonObj = obj.getJSONObject(0);
					JSONArray objArray = jsonObj.getJSONArray("images");
					String imageName = objArray
							.getString(objArray.length() - 1);
					System.out.println("ImageName " + imageName);
					selectedView.setTag(R.id.acceptButton, baseImageUrl
							+ imageName);
					ArrayList<String> temp = User.tom.getImageList();
					switch (selectedView.getId()) {

					case R.id.ProfilePicImageView1:
						temp.set(0, baseImageUrl + imageName);
						break;
					case R.id.ProfilePicImageView2:
						if (temp.size() > 1) {
							temp.set(1, baseImageUrl + imageName);
						} else {
							temp.add(baseImageUrl + imageName);
						}
						break;

					case R.id.ProfilePicImageView3:
						if (temp.size() > 2) {
							temp.set(2, baseImageUrl + imageName);
						} else {
							temp.add(baseImageUrl + imageName);
						}
						break;

					case R.id.ProfilePicImageView4:
						if (temp.size() > 3) {
							temp.set(3, baseImageUrl + imageName);
						} else {
							temp.add(baseImageUrl + imageName);
						}
						break;
					case R.id.ProfilePicImageView5:
						if (temp.size() > 4) {
							temp.set(4, baseImageUrl + imageName);
						} else {
							temp.add(baseImageUrl + imageName);
						}
						break;
					default:
						break;
					}
					User.tom.setImageList(temp);
					SharedPreferences pref = context.getSharedPreferences(
							"pref", 0);
					SharedPreferences.Editor editor = pref.edit();
					editor.putString(GetUserLogin.UserTom, User.tom.toString());
					editor.commit();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub

			ServiceHandler sh = new ServiceHandler();
			if (data != null) {
				String result = sh.makeServiceCall(GetUserLogin.url + "image",
						ServiceHandler.POST, data);

				System.out.println(result);
				return result;
			}
			return null;
		}

	}
}
