package com.example.soundqueintervalworkout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private IntervalWorkoutB myWorkout;
    private TextToSpeech mTTS;
    private EditText mEditTextTitle, mEditTextDuration, mEditTextRestDuration;
    private Button mButtonAddInterval, mButtonStartWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextTitle = findViewById(R.id.title_text);
        mEditTextDuration = findViewById(R.id.duration_text);
        mEditTextRestDuration = findViewById(R.id.rest_duration_text);
        mButtonAddInterval = findViewById(R.id.add_button);
        mButtonStartWorkout = findViewById(R.id.start_workout_button);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mButtonAddInterval.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        mEditTextDuration.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isDigitsOnly(charSequence)){
                    mButtonAddInterval.setEnabled(false);
                }else{
                    mButtonAddInterval.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEditTextRestDuration.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isDigitsOnly(charSequence)){
                    mButtonAddInterval.setEnabled(false);
                }else{
                    mButtonAddInterval.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        myWorkout = new IntervalWorkoutB(mTTS);

        mButtonAddInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEditTextTitle.getText().toString();
                long duration = Integer.parseInt(mEditTextDuration.getText().toString());
                long restDuration = Integer.parseInt(mEditTextRestDuration.getText().toString());
                ExerciseInterval interval = new ExerciseInterval(title, duration, restDuration);

                myWorkout.workout.add(interval);
                clearEditTextViews();
            }
        });

        mButtonStartWorkout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myWorkout.startWorkout();
            }
        });


    }

    private void clearEditTextViews(){
        mEditTextTitle.setText("");
        mEditTextDuration.setText("");
        mEditTextRestDuration.setText("");
    }
}
