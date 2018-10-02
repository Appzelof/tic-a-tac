package bordan16.woact.no.ticatac.customAnimations;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.transition.Fade;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;

import bordan16.woact.no.ticatac.R;

/**
 * Created by daniel on 04/03/2018.
 */

public class ButtonAnimations {

    private Context context;
    private Animation animation;
    private MyBounceInt myBounceInt;


    public ButtonAnimations(Context context) {
        this.context = context;
        animation = AnimationUtils.loadAnimation(context, R.anim.bounce_anim);
        myBounceInt = new MyBounceInt(0.2, 20);


    }

    public void bounceAnim(final Button button){
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                button.setLayerType(button.LAYER_TYPE_HARDWARE, null);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.setLayerType(View.LAYER_TYPE_NONE, null);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation.setInterpolator(myBounceInt);
        button.startAnimation(animation);
    }

    public void fadeIn(Button button) {
        Animation anim = new AlphaAnimation(0,1);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(500);
        button.setVisibility(View.VISIBLE);
        button.startAnimation(anim);
    }

}
