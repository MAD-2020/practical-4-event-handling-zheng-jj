package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";
    int score= 0;
    CountDownTimer myCountDown;
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

        myCountDown = new CountDownTimer(10000, 1000){
            Toast ToastDisplay;
            public void onTick(long millisUntilFinished){
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
                ToastDisplay.makeText(getApplicationContext(), "Get ready in "+Integer.toString((int) (millisUntilFinished/1000)) +" Seconds!", Toast.LENGTH_SHORT).show();

            }
            public void onFinish(){
                Log.v(TAG, "Ready CountDown Complete!");
                ToastDisplay.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                myCountDown.cancel();
            }
        };
        myCountDown.start();
    }

    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        Timer moletimer = new Timer();
	    moletimer.schedule(new TimerTask(){
            public void run(){
			    TimerMethod();
            }
        }, 1000, 1000);
    }
    private void TimerMethod()
    {
        this.runOnUiThread(TimerTick);
    }
    private Runnable TimerTick = new Runnable() {
        public void run() {
            Log.v(TAG, "New mole location");
            setNewMole();
        }
    };

    private static final int[] BUTTON_IDS = {
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
        R.id.hole1,R.id.hole2,R.id.hole3,R.id.hole4,R.id.hole5,R.id.hole6,R.id.hole7,R.id.hole8,R.id.hole9
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent receive = getIntent();
        score = receive.getIntExtra("Score", 0);
        Log.v(TAG, "Current User Score: " + String.valueOf(score));
        final TextView scoretext = findViewById(R.id.scoretext);
        scoretext.setText("Advanced score: "+Integer.toString(score));
        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            final Button tempbtn = findViewById(id);
            tempbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCheck(tempbtn);
                    scoretext.setText("Advanced score: "+Integer.toString(score));
                }
            });
        }
        readyTimer();
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private void doCheck(Button checkButton) {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        if (checkButton.getText() == "*") {
            score++;
            //instead of setting a new mole, i cleared all the moles here to balance the game
            clearAllMoles();
            Log.v(TAG, "HIT! SCORE +1!");
        } else {
            score--;
            clearAllMoles();
            Log.v(TAG, "MISS! SCORE -1!");
        }
    }
    public void clearAllMoles()
    {
        for(Integer buttonid : BUTTON_IDS){
            Button temp = findViewById(buttonid);
            temp.setText("o");
        }
    }
    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        clearAllMoles();
        Button newmole = findViewById(BUTTON_IDS[randomLocation]);
        newmole.setText("*");
    }
}

