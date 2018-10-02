package bordan16.woact.no.ticatac.customAnimations;

import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by daniel on 06/03/2018.
 */

public class ImageAnimations {


    public ImageAnimations() {

    }

    public Animation slideDownImage(ImageView imageView){
        Animation animate = new TranslateAnimation(0,0,0,imageView.getHeight());
        animate.setDuration(400);
        imageView.startAnimation(animate);
        imageView.setVisibility(View.INVISIBLE);
        animate.setFillAfter(true);
        animate.start();

        return animate;
    }

    public Animation slideImageUp(ImageView imageView) {
        Animation animation = new TranslateAnimation(0,0,0, -imageView.getHeight());
        animation.setDuration(400);
        imageView.startAnimation(animation);
        imageView.setVisibility(View.INVISIBLE);
        animation.setFillAfter(true);
        animation.start();

        return animation;

    }

    public Animation fadeOut(ImageView imageView) {
        Animation anim = new AlphaAnimation(1,0);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(400);
        imageView.startAnimation(anim);
        imageView.setVisibility(View.INVISIBLE);
        anim.start();

        return anim;
    }



}
