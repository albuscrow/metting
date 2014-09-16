package com.hjtech.secretary.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.hjtech.secretary.R;
import com.hjtech.secretary.common.MTUserManager;
import com.hjtech.secretary.data.GetDataAnsycTask;
import com.hjtech.secretary.data.MTSimpleResult;
import com.hjtech.secretary.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.hjtech.secretary.data.MTUser;
import com.hjtech.secretary.listener.NewActivityListener;
import com.hjtech.secretary.utils.MTCommon;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * The Class EditUserInfActivity.
 * 编辑用户信息
 * @author albuscrow
 */
public class EditUserInfActivity extends BaseActivity {
	
	/** The user. */
	private MTUser user;
	
	/** The image view. 
	 *  用户头像
	 */
	private ImageView imageView;
	
	/** The name edit. */
	private EditText nameEdit;
	
	/** The nick name edit. */
	private EditText nickEdit;
	
	/** The sex edit. */
	private EditText sexEdit;
	
	/** The unit edit. 
	 *  公司
	 */
	private EditText unitEdit;
	
	/** The department edit. 
	 *	部门 
	 */
	private EditText departmentEdit;
	
	/** The position edit. 
	 * 	职务
	 */
	private EditText positionEdit;
	
	/** The qq edit. */
	private EditText qqEdit;
	
	/** The weixin edit. */
	private EditText weixinEdit;
	
	/** The email edit. */
	private EditText emailEdit;

	/** The items. */
	private String[] items = new String[] { "选择本地图片", "拍照" };
	
	/** The photo. */
	private Bitmap photo;

	/* 请求码 */
	
	/** The Constant IMAGE_REQUEST_CODE. */
	private static final int IMAGE_REQUEST_CODE = 0;
	
	/** The Constant CAMERA_REQUEST_CODE. */
	private static final int CAMERA_REQUEST_CODE = 1;
	
	/** The Constant RESULT_REQUEST_CODE. */
	private static final int RESULT_REQUEST_CODE = 2;

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initData();
		initUI(R.layout.activity_user_edit, R.string.title_activity_personal,
				R.string.title_activity_user_edit, R.string.complete);
	}

	/**
	 * Inits the data.
	 */
	private void initData() {
		user = MTUserManager.getUser();
	}

	/* (non-Javadoc)
	 * @see com.hjtech.secretary.activity.BaseActivity#initUI(int, int, int, int)
	 */
	@Override
	protected void initUI(int layoutId, int backId, int titleId, int rightId) {
		super.initUI(layoutId, backId, titleId, rightId);
		setBackButton();

		// get view
		imageView = (ImageView) gv(R.id.personal_imageview);
		nameEdit = (EditText) gv(R.id.edit_name_textview);
		nickEdit = (EditText) gv(R.id.edit_nickname_textview);
		sexEdit = (EditText) gv(R.id.edit_gender);
		unitEdit = (EditText) gv(R.id.edit_company_textview);
		departmentEdit = (EditText) gv(R.id.edit_department_textview);
		positionEdit = (EditText) gv(R.id.edit_position_textview);
		qqEdit = (EditText) gv(R.id.edit_qq_textview);
		weixinEdit = (EditText) gv(R.id.edit_weixin_textview);
		emailEdit = (EditText) gv(R.id.edit_email_textview);

		// set content
		Bitmap photo = user.getMuPhotoImage();
		if (photo != null) {
			imageView.setImageBitmap(photo);
		} else {
			ImageLoader.getInstance().loadImage(user.getMuPhoto(),
					new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {

				}

				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
					imageView .setImageResource(R.drawable.common_default_image);
				}

				@Override
				public void onLoadingComplete(String imageUri,
						View view, Bitmap loadedImage) {
					if (loadedImage == null) {
						imageView .setImageResource(R.drawable.common_default_image);
					} else {
						imageView.setImageBitmap(loadedImage);
						user.setMuPhotoImage(loadedImage);
					}
				}

				@Override
				public void onLoadingCancelled(String imageUri,
						View view) {
				}
			});
		}

		String muName = user.getMuName();
		if (muName != null && muName.length() != 0) {
			nameEdit.setText(muName);
			nameEdit.setSelection(muName.length());
		}
		String muNickName = user.getMuNickName();
		if (muNickName != null && muNickName.length() != 0) {
			nickEdit.setText(muNickName);
		}
		String muSexString = user.getMuSexString();
		if (muSexString != null && muSexString.length() != 0) {
			sexEdit.setText(muSexString);
		}
		if (user.getMuUnitName() != null && user.getMuUnitName().length() != 0) {
			unitEdit.setText(user.getMuUnitName());
		}
		if (user.getMuSector() != null && user.getMuSector().length() != 0) {
			departmentEdit.setText(user.getMuSector());
		}
		if (user.getMuPosition() != null && user.getMuPosition().length() != 0) {
			positionEdit.setText(user.getMuPosition());
		}
		if (user.getMuQq() != null && user.getMuQq().length() != 0) {
			qqEdit.setText(user.getMuQq());
		}
		if (user.getMuWeixin() != null && user.getMuWeixin().length() != 0) {
			weixinEdit.setText(user.getMuWeixin());
		}
		if (user.getMuEmail() != null && user.getMuEmail().length() != 0) {
			emailEdit.setText(user.getMuEmail());
		}

		gv(R.id.edit_password_button).setOnClickListener(
				new NewActivityListener(this, ModifyPasswordActivity.class));

		gv(R.id.edit_profile_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}
		});

		setRightClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bitmap image = MTCommon.getImageFromView(imageView);
				user.setMuPhotoImage(image);

				String name = MTCommon.getContent(nameEdit);
				final MTUser user = new MTUser();
				user.setMuName(name);

				String nickName = MTCommon.getContent(nickEdit);
				user.setMuNickName(nickName);

				String sex = MTCommon.getContent(sexEdit);
				int sex_int = -1;
				if (sex != null) {
					if (sex.equals("男")) {
						sex_int = 0;
					} else if (sex.equals("女")) {
						sex_int = 1;
					}
				}
				user.setMuSex(sex_int);

				String unit = MTCommon.getContent(unitEdit);
				user.setMuUnitName(unit);

				String department = MTCommon.getContent(departmentEdit);
				user.setMuSector(department);

				String position = MTCommon.getContent(positionEdit);
				user.setMuPosition(position);

				String qq = MTCommon.getContent(qqEdit);
				user.setMuQq(qq);

				String email = MTCommon.getContent(emailEdit);
				if (email != null && email.length() != 0) {
					if (!MTCommon.isEmail(email)) {
						MTCommon.ShowToast("请输入正确的邮箱地址");
						return;
					}
				}
				user.setMuEmail(email);

				String weixin = MTCommon.getContent(weixinEdit);
				user.setMuWeixin(weixin);

				user.setMuAccount(MTUserManager.getUser().getMuAccount());
				user.setMuPhotoImage(EditUserInfActivity.this.user
						.getMuPhotoImage());

				new GetDataAnsycTask().setOnDataAnsyTaskListener(
						new OnDataAnsyTaskListener() {

							@Override
							public void onPreExecute() {

							}

							@Override
							public void onPostExecute(Object result) {
								if (result != null && result instanceof Integer) {
									MTCommon.ShowToast("当前网络不可用,请检查网络链接");
									return;
								}

								if (result == null) {
									MTCommon.ShowToast("修改失败");
									return;
								}
								MTSimpleResult sr = (MTSimpleResult) result;
								if (sr.getResult() == 1) {
									MTCommon.ShowToast("修改成功");
									EditUserInfActivity.this.user = MTUserManager
											.updateUser(user);
									finish();
								} else {
									MTCommon.ShowToast("修改失败");
								}
							}
						}).modifyUserInf(user);
			}
		});
	}

	/**
	 * 显示选择对话框.
	 */
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent photoPickerIntent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							photoPickerIntent.setType("image/*");
							photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
							// 判断存储卡是否可以用，可用进行存储
							if (MTCommon.hasSdcard()) {
								photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
							}
							startActivityForResult(photoPickerIntent, IMAGE_REQUEST_CODE);
							break;
						case 1:
							Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							intentFromCapture.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
							intentFromCapture.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
							File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);  
							File tempFile = new File(path,TEMP_PHOTO_FILE);
							// 判断存储卡是否可以用，可用进行存储
							if (MTCommon.hasSdcard()) {
								intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
							}

							startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// 结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (MTCommon.hasSdcard()) {
					File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);  
					File tempFile = new File(path,TEMP_PHOTO_FILE);
					startPhotoZoom(Uri.fromFile(tempFile));  
				} else {
					MTCommon.ShowToast("未找到存储卡，无法存储照片！");
				}
				break;
			case RESULT_REQUEST_CODE: // 图片缩放完成后
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现.
	 * 
	 * @param uri
	 *            the uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	/**
	 * 保存裁剪之后的图片数据.
	 * 
	 * @param data
	 *            the data
	 * @return the image to view
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			photo = decodeUriAsBitmap(imageUri);// decode bitmap
			imageView.setImageBitmap(photo);
		}
	}

	/**
	 * Gets the temp uri.
	 * 用于暂存从图库或者从拍照得到的照片，供下一步裁剪
	 * @return the temp uri
	 */
	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	/** The Constant TEMP_PHOTO_FILE. */
	private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";

	/**
	 * Gets the temp file.
	 * 
	 * @return the temp file
	 */
	private File getTempFile() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE);
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return file;
		} else {
			return null;
		}
	}

	/** The image uri. */
	Uri imageUri = getTempUri(); // The Uri to store the big bitmap

	/**
	 * Decode uri as bitmap.
	 * 
	 * @param uri
	 *            the uri
	 * @return the bitmap
	 */
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
}
