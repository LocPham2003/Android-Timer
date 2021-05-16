package com.company.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button startPause;
    Button reset;
    TextView timer;

    //Initialize a seekbar
    SeekBar seekBar;

    long startTimeInMilliseconds = 165000;
    long timeLeftInMilliseconds = 165000; //165 because a match lasts for 2 minutes and 45 seconds
    boolean timerIsRunning = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.progress);
        //Configure our progress bar
        seekBar.setMax(165);
        seekBar.setProgress(0);

        //Listen to touches from the user
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //As you drag the bar
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //int progress means how much time has passed in our context
                System.out.println(progress);
                String modifiedTime = String.valueOf(progress);
                //(165 - progress) * 1000
                timeLeftInMilliseconds = (165 - progress) * 1000;
                timer.setText(modifiedTime);
            }

            //Occur the moment the user touches the dot
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //ahafhaofhoahoihofhaodh
            }

            //Occur the moment the user let go of the dot
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //ahdiahhaiudhaoifhoaf
            }
        });

        timer = findViewById(R.id.timer);
        timer.setText("0");

        startPause = findViewById(R.id.action);
        reset = findViewById(R.id.reset);

        startPause.setOnClickListener(v -> {
            if (timerIsRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        reset.setOnClickListener(v -> resetTimer());

    }

    private void startTimer(){
        //Initialize the countDownTimer because we only use it when we start it.

            countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) { //Count down interval is 1 second
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMilliseconds = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    timerIsRunning = false;
                    startPause.setText("Start");
                    startPause.setVisibility(View.INVISIBLE);
                    reset.setVisibility(View.VISIBLE);

                }
            }.start();

            //Change the button text from Start to pause
            startPause.setText("Pause");

            //Hide the reset button
            reset.setVisibility(View.INVISIBLE);

            //Change the boolean value to true, indicating that the timer is running
            timerIsRunning = true;

            //Disable dragging from seekbar
            seekBar.setEnabled(false);

    }

    private void pauseTimer(){
        countDownTimer.cancel();
        timerIsRunning = false;
        startPause.setText("Start");
        reset.setVisibility(View.VISIBLE);
        //Enable dragging to the user when you pause the timer
        seekBar.setEnabled(true);
        updateCountDownText();

    }

    private void resetTimer(){
        timeLeftInMilliseconds = startTimeInMilliseconds;
        reset.setVisibility(View.INVISIBLE);
        startPause.setVisibility(View.VISIBLE);
        timer.setText("0");
        seekBar.setProgress(0);
        startPause.setText("Start");
    }

    private void updateCountDownText(){
        System.out.println(timeLeftInMilliseconds);

        int timeLeft = (int) (timeLeftInMilliseconds) / 1000; //How much timeLeft until it reaches 165 seconds

        //To find the how much time are we into the match, we must use 165 - timeLeft (0 -> 165)
        //Remove the line if you want it to countdown. (165 -> 0)
        int progressTime = 165 - timeLeft;


        String timeToString = Integer.toString(progressTime);

        timer.setText(timeToString);

        //Update the seekbar
        seekBar.setProgress(progressTime);

    }

}