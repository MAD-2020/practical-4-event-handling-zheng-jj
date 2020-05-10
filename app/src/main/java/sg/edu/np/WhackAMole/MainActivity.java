package sg.edu.np.WhackAMole;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */
    int score = 0;
    Button[] buttons = new Button[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "Finished Pre-Initialisation!");
        buttons[0]= findViewById(R.id.hole1);
        buttons[1]= findViewById(R.id.hole2);
        buttons[2]= findViewById(R.id.hole3);
        final TextView scoretext = findViewById(R.id.scoretext);
        for(final Button x:buttons)
        {
            x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCheck(x);
                    scoretext.setText("Score: "+Integer.toString(score));
                    setNewMole();
                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if (checkButton.getText()=="*") {
            score++;
            Log.v(TAG,"HIT! SCORE +1!");
        }
        else{
            score--;
            Log.v(TAG,"MISS! SCORE -1!");
        }
        if (score%10==0)
        {
            nextLevelQuery();
        }
    }

    private void nextLevelQuery() {
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
        Log.v(TAG, "Advance option given to user!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Next Level");
        builder.setMessage("Go to advanced mode?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.v(TAG, "User decline!");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent activity2 = new Intent(MainActivity.this, Main2Activity.class);
        activity2.putExtra("Score",score);
        startActivity(activity2);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        for(Button x : buttons){
            x.setText("o");
        }
        buttons[randomLocation].setText("*");
    }
}