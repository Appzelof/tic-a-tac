package bordan16.woact.no.ticatac.alerts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;

import bordan16.woact.no.ticatac.R;
import bordan16.woact.no.ticatac.activities.MainActivity;

/**
 * Created by daniel on 27/03/2018.
 */

public class CustomAlertController {

    private String title;
    private int score;
    private Context context;
    private AlertDialog.Builder alertDialog;
    private Dialog dialog;

    public CustomAlertController(Context context) {
        this.context = context;
        alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
    }

    public Boolean showAlertDialog(String player){
        alertDialog.setTitle("CONGRATULATIONS");
        alertDialog.setMessage(player.toUpperCase() + " WINS");
        if (MainActivity.twoPlayer) {
            alertDialog.setIcon(R.drawable.dragon);
        } else {
            alertDialog.setIcon(R.drawable.dragon_blue);
        }
        dialog = alertDialog.create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.TOP);
        return true;
    }

    public AlertDialog.Builder getAlertDialog(){
        return alertDialog;
    }
}
