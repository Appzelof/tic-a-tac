package no.woact.bordan16.ticatac.customAnimations;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by daniel on 06/03/2018.
 */

public class ImageAnimations {

    /**
     * Method that creates a slide down animation on desired widgets, in this case, imageViews.
     * We use a Translate Animation for the desired effect. This is crucial for the slide effect.
     * We can use Float values which defines the slide Animations
     * @param imageView widget that we want to animate
     * @return animation which can be used in an AnimationSet if you want to combine multiple animations
     */
    public Animation slideDownImage(ImageView imageView){
        Animation animate = new TranslateAnimation(0,0,0,imageView.getHeight());
        animate.setDuration(400);
        imageView.startAnimation(animate);
        imageView.setVisibility(View.INVISIBLE);
        animate.setFillAfter(true);
        animate.start();

        return animate;
    }

    /**
     * Method that creates a slide up animation on desired widgets, in this case, imageViews.
     * We use a Translate Animation for the desired effect. This is crucial for the slide effect.
     * We can use Float values which defines the slide Animations
     * @param imageView widget that we want to animate
     * @return
     */
    public Animation slideImageUp(ImageView imageView) {
        Animation anim = new TranslateAnimation(0,0,0, -imageView.getHeight());
        anim.setDuration(400);
        imageView.startAnimation(anim);
        imageView.setVisibility(View.INVISIBLE);
        anim.setFillAfter(true);
        anim.start();

        return anim;

    }

    /**
     * Method that creates an AlphaAnimation
     * @param imageView widget we want to animate.
     * @return animation
     */
    public Animation fadeOut(ImageView imageView) {
        Animation anim = new AlphaAnimation(1,0);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(400);
        imageView.startAnimation(anim);
        imageView.setVisibility(View.INVISIBLE);
        anim.start();

        return anim;
    }

    /**
     * Method that creates an AlphaAnimation
     * @param imageView widget we want to animate.
     * @return animation
     */
    public Animation fadeIn(ImageView imageView){
        Animation anim = new AlphaAnimation(0,1);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(400);
        imageView.startAnimation(anim);
        imageView.setVisibility(View.VISIBLE);
        anim.start();

        return anim;
    }

}
