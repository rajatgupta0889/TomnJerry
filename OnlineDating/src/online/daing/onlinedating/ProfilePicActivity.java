package online.daing.onlinedating;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class ProfilePicActivity extends Activity implements OnClickListener {
	ImageView profilePicImageView, profilePicImageView1, profilePicImageView2,
			profilePicImageView3, profilePicImageView4, profilePicImageView5;
	ImageView selectedView;
	private static final int SELECT_FILE = 1;
	Bitmap bm;

	private int REQUEST_CAMERA = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_images);

		init();
		Intent intent = getIntent();
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			String user = bundle.getString("user");
			if (User.tom == null) {
				try {
					User.tom = User.getUser(new JSONObject(user));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ImageLoader imageLoader = new ImageLoader(this);
			imageLoader.DisplayImage(User.tom.getImageList().get(0),
					R.drawable.com_facebook_profile_default_icon,
					profilePicImageView);
			imageLoader.DisplayImage(User.tom.getImageList().get(1),
					R.drawable.com_facebook_profile_default_icon,
					profilePicImageView1);
			imageLoader.DisplayImage(User.tom.getImageList().get(2),
					R.drawable.com_facebook_profile_default_icon,
					profilePicImageView2);
			imageLoader.DisplayImage(User.tom.getImageList().get(3),
					R.drawable.com_facebook_profile_default_icon,
					profilePicImageView3);
			imageLoader.DisplayImage(User.tom.getImageList().get(4),
					R.drawable.com_facebook_profile_default_icon,
					profilePicImageView4);
			imageLoader.DisplayImage(User.tom.getImageList().get(5),
					R.drawable.com_facebook_profile_default_icon,
					profilePicImageView5);

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
		switch (v.getId()) {
		case R.id.profileMatchImageView:

			break;

		default:
			selectedView = (ImageView) v;
			if (selectedView.getDrawable().equals(
					R.drawable.com_facebook_profile_default_icon)) {
				selectImage();
			} else {
				profilePicImageView
						.setImageDrawable(selectedView.getDrawable());
			}
			break;
		}

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
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					intent.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(image));
					startActivityForResult(intent, REQUEST_CAMERA);
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
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

					bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
							btmapOptions);
					new AsyncTask<Void, Void, Void>() {

						@Override
						protected Void doInBackground(Void... params) {
							// TODO Auto-generated method stub
							sendImageTOServer(bm);
							return null;
						}

					};
					// bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
					if (selectedView != null)
						selectedView.setImageBitmap(bm);

					String path = android.os.Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "default";
					f.delete();
					OutputStream fOut = null;
					File file = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");
					try {
						fOut = new FileOutputStream(file);
						bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
						fOut.flush();
						fOut.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
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
