package no.woact.bordan16.ticatac.engines;

import android.util.SparseArray;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import no.woact.bordan16.ticatac.fragments.GameFragment;
import no.woact.bordan16.ticatac.interfaces.GameWin;

/**
 * Created by daniel on 08/03/2018.
 */

/**
 * This class handles the gameLogic.
 */
public class GameEngine {

    private ArrayList<Integer[]> multiIntegerArrayList;
    private GameWin gameWin;
    private HashMap<Integer, Button> buttonHashMap;
    private HashMap<Integer, Integer> playerOneHashMap;
    private HashMap<Integer, Integer> playerTwoHashMap;
    private ArrayList<Integer> integerArrayList;

    public GameEngine(GameFragment gameFragment, HashMap<Integer, Button> buttonHashMap, HashMap<Integer, Integer> playerOne, HashMap<Integer, Integer> playerTwo) {
        gameWin = gameFragment;
        this.buttonHashMap = buttonHashMap;
        this.playerOneHashMap = playerOne;
        this.playerTwoHashMap = playerTwo;
        multiIntegerArrayList = new ArrayList<>();
        createRandomList();
    }

    /**
     * Method that checks if playerHashMap contains any of the winnerCombinations.
     * We iterate trough our winnerCombinationList and sets a Boolean to true if we find
     * our desired values.
     *
     * @param player PlayerHashMap that we iterate trough
     * @return Boolean
     */
    public Boolean gameWon(HashMap<Integer, Integer> player) {
        Boolean combFound = false;
        Boolean hasFailed;
        //Iterates trough winnerCombination ArrayList to get all Integer[]`s.
        for (Integer[] intArr : winnerCombinations()) {
            combFound = false;
            hasFailed = false;
            //Iterates trough Integer[] array to get all int`s.
            for (int i : intArr) {
                //Checks if one of the players placement is null, if it is, we skip the if logic.
                if (player.get(i) != null) {
                    //If the if logic is called, we will check if hasFailed is false.
                    if (!hasFailed) {
                        /*
                           If hasFailed is false, we have found that players placement matches one of the
                           winnerCombinations and we can set combFound to true.
                         */
                        combFound = true;
                        //we have successfully found our combination.
                    }
                } else {
                    //Sets boolean to false if we cant find one of the winnerCombinations.
                    combFound = false;
                    hasFailed = true;
                }
            }
            //combFound is true and we call our interface!
            if (combFound) {
                gameWin.gameWin(intArr);
                System.out.println("winner");
                break;
            }
        }
        //Returns true or false based on success.
        return combFound;
    }


    /**
     * Method that creates a random list by Collections.shuffle.
     * We use a new instance of ArrayList which we populate with buttonHashMap.size.
     * By using Collections.shuffle we get the numberList in a random order.
     */
    public void createRandomList(){
        integerArrayList = new ArrayList<>();
        for (int i = 1; i < buttonHashMap.size() + 1; i++) {
            integerArrayList.add(i);
            Collections.shuffle(integerArrayList);
        }
    }

    /**
     * Method that handles our randomGenerator. First we iterate trough an arrayList which contains
     * 9 numbers, in this case, the size of buttonHashmap.size. After the iteration we will check if one of the buttons in
     * the buttonHashmap is enabled, if it`s true, we will get our random number by assigning the variable "number" to "i".
     * If the if logic is not called, we will call another method that creates a new randomList.
     *
     * After the if logic we will remove the index from our arrayList so that we know for sure that the buttonHashmap.get.(i)
     * is disabled. That way we can return a unique number between 1 - 9 depending on what place in buttonHashmap is enabled.
     * @return int RandomNumber
     */
    public int gameLogicAI() {
        int number = 0;
            for (Integer i : integerArrayList){
                if (buttonHashMap.get(i).isEnabled()){
                    number = i;
                } else {
                    createRandomList();
                }
        }
        integerArrayList.remove(Integer.valueOf(number));
        Collections.shuffle(integerArrayList);
        return number;
    }

    /**
     * Method that checks if every button is disabled. If its true, we will have a draw.
     * @return Boolean
     */
    public Boolean gameDraw(){
        if (!gameWon(playerOneHashMap) && !gameWon(playerTwoHashMap)) {
            if (!buttonHashMap.get(1).isEnabled() && !buttonHashMap.get(2).isEnabled() && !buttonHashMap.get(3).isEnabled() &&
                    !buttonHashMap.get(4).isEnabled() && !buttonHashMap.get(5).isEnabled() && !buttonHashMap.get(6).isEnabled() &&
                    !buttonHashMap.get(7).isEnabled() && !buttonHashMap.get(8).isEnabled() && !buttonHashMap.get(9).isEnabled()) {
                System.out.println("draw");
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * Method that populates an arrayList with possible winner-combinations. The ArrayList is  multidimensional
     * that contains Integer[].
     * @return Arraylist
     */
    public ArrayList<Integer[]> winnerCombinations() {
        //We creates our winnerCombinations so we can populate them in our arrayList.
        Integer[] win0 = {1,2,3};
        Integer[] win1 = {4,5,6};
        Integer[] win2 = {7,8,9};
        Integer[] win3 = {1,4,7};
        Integer[] win4 = {2,5,8};
        Integer[] win5 = {3,6,9};
        Integer[] win6 = {1,5,9};
        Integer[] win7 = {3,5,7};

        //Here we populate our arrayList
        multiIntegerArrayList.add(win0);
        multiIntegerArrayList.add(win1);
        multiIntegerArrayList.add(win2);
        multiIntegerArrayList.add(win3);
        multiIntegerArrayList.add(win4);
        multiIntegerArrayList.add(win5);
        multiIntegerArrayList.add(win6);
        multiIntegerArrayList.add(win7);

        return multiIntegerArrayList;
    }
}
