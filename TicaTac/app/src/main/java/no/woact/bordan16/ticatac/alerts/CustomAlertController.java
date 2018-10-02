package no.woact.bordan16.ticatac.alerts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import no.woact.bordan16.ticatac.R;
import no.woact.bordan16.ticatac.activities.MainActivity;

/**
 * Created by daniel on 27/03/2018.
 */

/**
 * Class that creates an AlertController.Builder which is created one time but appears
 * when a player has won, or if its a tie.
 */
public class CustomAlertController {
    //We use a builder to create a new alert
    private AlertDialog.Builder alertDialog;
    private Dialog dialog;

    public CustomAlertController(Context context) {
        //Creates a new instance
        alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
    }

    /**
     * Method that creates an AlertDialog with a custom title, message, and a icon.
     * The if logic varies from what kind of player has won.
     * @param player spiller
     * @param color farge
     * @return Boolean
     */
    public Boolean showAlertDialog(String player, String color){
        alertDialog.setTitle("CONGRATULATIONS");
        alertDialog.setMessage(player.toUpperCase() + " WINS");

        if (color.equalsIgnoreCase("RED")) {
            if (MainActivity.pokemonSelected){
                alertDialog.setIcon(R.drawable.charmander);
            } else {
                alertDialog.setIcon(R.drawable.dragon);
            }

        } else if (color.equalsIgnoreCase("BLUE")) {
            if (MainActivity.pokemonSelected){
                alertDialog.setIcon(R.drawable.bullbasaur);
            } else {
                alertDialog.setIcon(R.drawable.dragon_blue);
            }

        } else if (color.equalsIgnoreCase("GOLD")){
            alertDialog.setTitle(player.toString());
            alertDialog.setMessage("PLAY AGAIN");
            alertDialog.setIcon(R.drawable.drop);
        }


        dialog = alertDialog.create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.TOP);

        return true;
    }

    /**
     * Method that returns our custom AlertDialog.
     * @return AlertDialog.Builder.
     */
    public AlertDialog.Builder getAlertDialog(){
        return alertDialog;
    }
}
