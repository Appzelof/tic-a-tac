package no.woact.bordan16.ticatac.customAnimations;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import no.woact.bordan16.ticatac.R;

/**
 * Created by daniel on 04/03/2018.
 */

public class ButtonAnimations {

    private Context context;
    private Animation animation;
    private BounceLogic bounceLogic;


    public ButtonAnimations(Context context) {
        this.context = context;
        animation = AnimationUtils.loadAnimation(context, R.anim.bounce_anim);
        bounceLogic = new BounceLogic(0.2, 20f);
    }

    /**
     * Method that sets an AnimationListener so we can define a layer type. We use Layer_type hardware so we can
     * easily reach 60fps without losing valuable frame time. We want our app to run at 60fps with frame time under 16ms
     * When the animation is done we set the Layer_Type to none so we can save resources when we can.
     * @param button the widget we want to animate
     */
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

        animation.setInterpolator(bounceLogic);
        button.startAnimation(animation);
    }

    /**
     * Method that creates a alpha animation! We use an AccelerateInterpolator to create the effect.
     * We also set a duration. The duration tells how many ms the animation should take.
     *
     * @param button the widged we want to animate
     */
    public void fadeIn(Button button) {
        Animation anim = new AlphaAnimation(0,1);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(500);
        button.setVisibility(View.VISIBLE);
        button.startAnimation(anim);
    }

    /**
     * Method that creates a Alpha-Animation from invisible to visible. Here we also use
     * an AccelerateInterpolator for the desired effect.
     * @param imageButton the widget we want to animate.
     */
    public void fadeInImgBtn(ImageButton imageButton){
        Animation anim = new AlphaAnimation(0,1);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(500);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.startAnimation(anim);
    }

    /**
     * Method that creates an Alpha-Animation from visible to invisible. Here we also use
     * an AccelerateInterpolator for the desired effect
     * @param imageButton
     */
    public void fadeOutImgBtn(ImageButton imageButton){
        Animation anim = new AlphaAnimation(1,0);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(500);
        imageButton.setVisibility(View.INVISIBLE);
        imageButton.startAnimation(anim);
    }

}
