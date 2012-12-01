/**
 * 
 */
package org.jared.android.volley.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * This utility class provides utility methods to transform images
 * 
 * @author Eric Taix
 */
public class ImageUtility {

	/**
	 * Add rounded corners to an image
	 * @param context
	 * @param input The source image
	 * @param pixels The corner's radius
	 * @param w The width of the final image
	 * @param h The height of the final image
	 * @return A new bitmap with rounded corners
	 */
	public static Bitmap getRoundedCornerBitmap(Context context, Bitmap input, int pixels , int w , int h) {
	    Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);
	    final float densityMultiplier = context.getResources().getDisplayMetrics().density;

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, w, h);
	    final RectF rectF = new RectF(rect);

	    // Make sure that our rounded corner is scaled appropriately
	    final float roundPx = pixels*densityMultiplier;

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

	    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	    Rect src = new Rect(0, 0, input.getWidth(), input.getHeight());
	    RectF dst = new RectF(0, 0, w, h);
	    canvas.drawBitmap(input, src, dst, paint);
	    return output;
	}
	
	/**
	 * Paint a bitmap inside a circle
	 * @param context
	 * @param input The source image
	 * @param pixels The corner's radius
	 * @param w The width of the final image
	 * @param h The height of the final image
	 * @return A new bitmap with rounded corners
	 */
	public static Bitmap getCircleBitmap(Context context, Bitmap input, int r, int w, int h) {
	    Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);
	    final float densityMultiplier = context.getResources().getDisplayMetrics().density;

	    final int color = 0xff424242;
	    final Paint paint = new Paint();

	    // Make sure that our rounded corner is scaled appropriately
	    final float radius = r*densityMultiplier;

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawCircle(w/2, h/2, radius, paint);

	    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	    Rect src = new Rect(0, 0, input.getWidth(), input.getHeight());
	    RectF dst = new RectF(0, 0, w, h);
	    canvas.drawBitmap(input, src, dst, paint);
	    return output;
	}
}
