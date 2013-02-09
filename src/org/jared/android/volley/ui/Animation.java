/**
 * 
 */
package org.jared.android.volley.ui;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;

/**
 * Static methods for animation purpose
 * 
 * @author Eric Taix
 */
@SuppressLint("NewApi")
public class Animation {

	static final boolean IS_HONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

	/**
	 * Animate a view with an alpha transition
	 */
	public static void alpha(View view, float fromAlpha, float toAlpha, long duration, int repeatCount) {
		if (view != null) {
			if (IS_HONEYCOMB) {
				ObjectAnimatorAlpha.invoke(view, fromAlpha, toAlpha, duration, repeatCount);
			}
			else {
				AlphaAnimation alpha = new AlphaAnimation(fromAlpha, toAlpha);
				alpha.setRepeatCount(repeatCount);
				alpha.setDuration(duration);
				view.startAnimation(alpha);
			}
		}
	}

	/**
	 * Animate a view with a rotation
	 * 
	 * @param view
	 */
	public static void rotateLeft(View view, float fromDegrees, float toDegrees, long duration) {
		if (view != null) {
			if (IS_HONEYCOMB) {
				ObjectAnimatorRotate.invoke(view, fromDegrees, toDegrees, duration);
			}
			else {
				RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, view.getWidth() / 2.0f, view.getHeight() / 2.0f);
				rotate.setDuration(duration);
				view.startAnimation(rotate);
			}
		}
	}

	/**
	 * Animate a view with a fade in transition. At this end of the animation, the
	 * view will be visible
	 */
	public static void fadeIn(final View view, long duration) {
		if (view != null) {
			AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
			alpha.setDuration(duration);
			alpha.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
				@Override
				public void onAnimationEnd(android.view.animation.Animation animation) {
					view.setVisibility(View.VISIBLE);
				}

				@Override
				public void onAnimationRepeat(android.view.animation.Animation animation) {
				}

				@Override
				public void onAnimationStart(android.view.animation.Animation animation) {
				}
			});
			view.startAnimation(alpha);
		}
	}

	/**
	 * Animate a view with a fade in transition. At this end of the animation, the
	 * view will be visible
	 */
	public static void fadeOut(final View view, long duration) {
		if (view != null) {
			AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
			alpha.setDuration(duration);
			alpha.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
				@Override
				public void onAnimationEnd(android.view.animation.Animation animation) {
					view.setVisibility(View.INVISIBLE);
				}

				@Override
				public void onAnimationRepeat(android.view.animation.Animation animation) {
				}

				@Override
				public void onAnimationStart(android.view.animation.Animation animation) {
				}
			});
			view.startAnimation(alpha);
		}
	}

	/**
	 * Animate a view with a 360 degrees rotation AND a fade out transition. At
	 * this end of the animation, the view will be invisible
	 */
	public static void fadeOutAndRotate(final View view, long duration) {
		if (view != null) {
			AnimationSet anims = new AnimationSet(true);
			AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
			alpha.setDuration(duration);
			alpha.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
				@Override
				public void onAnimationEnd(android.view.animation.Animation animation) {
					view.setVisibility(View.INVISIBLE);
				}

				@Override
				public void onAnimationRepeat(android.view.animation.Animation animation) {
				}

				@Override
				public void onAnimationStart(android.view.animation.Animation animation) {
				}
			});
			anims.addAnimation(alpha);
			RotateAnimation rotate = new RotateAnimation(0, 360, view.getWidth() / 2.0f, view.getHeight() / 2.0f);
			rotate.setDuration(duration);
			anims.addAnimation(rotate);
			view.startAnimation(anims);
		}
	}

	/**
	 * Wrap the ObjectAnimator call by a class ! This allows coding new API 11
	 * code, without a VerifyError exception: this "invoke" method won't be called
	 * until the current API version is equal or higher than HONEY_COMB and
	 * because this verification is done before the class access
	 * 
	 * @author Eric Taix
	 */
	private static final class ObjectAnimatorAlpha {
		static void invoke(View view, float fromAlpha, float toAlpha, long duration, int repeatCount) {
			ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", fromAlpha, toAlpha);
			alpha.setRepeatMode(ObjectAnimator.REVERSE);
			alpha.setRepeatCount(repeatCount);
			alpha.setDuration(duration);
			alpha.start();
		}
	}

	private static final class ObjectAnimatorRotate {
		static void invoke(View view, float fromDegrees, float toDegrees, long duration) {
			ObjectAnimator.ofFloat(view, "rotationY", fromDegrees, toDegrees).setDuration(duration).start();
		}
	}

}
