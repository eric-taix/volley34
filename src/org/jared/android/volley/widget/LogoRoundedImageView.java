package org.jared.android.volley.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
* An ImageView with rounded corners
* 
* @author Eric Taix
*/
public class LogoRoundedImageView extends ImageView {

	public LogoRoundedImageView(Context context) {
		super(context);
	}

	public LogoRoundedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LogoRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		Drawable drawable = getDrawable();
		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		int w = getWidth(), h = getHeight();
//		Bitmap roundBitmap = ImageUtility.getCircleBitmap(getContext(), b, 40, w, h);
		Bitmap roundBitmap = ImageUtility.getRoundedCornerBitmap(getContext(), b, 10, w, h);
		canvas.drawBitmap(roundBitmap, 0, 0, null);
	}

}