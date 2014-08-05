package com.hjtech.secretary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MTCommon {
	private static Context context;
	
	public static void init(Context context){
		MTCommon.context = context;
	}
	
	public static String getContent(EditText et){
		String content = et.getText().toString();
		if (content == null || content.length() == 0) {
			return null;
		}
		return content;
	}
	
	public static void setContent(EditText et, int StringId){
		et.setText(context.getResources().getString(StringId));
	}
	
	public static void setContent(TextView et, int StringId){
		et.setText(context.getResources().getString(StringId));
	}
	
	public static void ShowToast(String content){
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

	public static void moveSelectionToLast(EditText passWord) {
		passWord.requestFocus();
		passWord.setSelection(passWord.getText().toString().length());
	}

	public static void ShowToast(CharSequence text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    
    public static boolean isEllipsis(TextView view){
    	Layout l = view.getLayout();
    	if (l != null) {
			int lines = l.getLineCount();
			if (lines > 0) {
				if (l.getEllipsisCount(lines - 1) > 0) {
					return true;
				}
				
			}
		}
    	
    	return false;
    }
	
}
