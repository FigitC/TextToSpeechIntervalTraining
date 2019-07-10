package com.example.soundqueintervalworkout;

import android.os.Handler;
import android.speech.tts.TextToSpeech;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IntervalWorkoutB {
    public ArrayList<ExerciseInterval> workout = new ArrayList<ExerciseInterval>();
    private TextToSpeech mTTS;
    private ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
    private int i;

    public IntervalWorkoutB(TextToSpeech mTTS){
        this.mTTS = mTTS;
        mTTS.setSpeechRate(0.8f);
    }

    public void startWorkout(){
        long startOfInterval = 10;

        for(i=0; i < workout.size(); i++) {
            exec.schedule(new Runnable() {
                @Override
                public void run() {
                    String title = workout.get(i).title;
                    mTTS.speak("Next exercise is" + title,
                            TextToSpeech.QUEUE_FLUSH, null);


                }
            }, startOfInterval - 10, TimeUnit.SECONDS);

            exec.schedule(new Runnable() {
                @Override
                public void run() {
                    mTTS.speak("Start!", TextToSpeech.QUEUE_FLUSH, null);
                }
            }, startOfInterval, TimeUnit.SECONDS);

            exec.schedule(new Runnable() {
                @Override
                public void run() {
                    mTTS.speak("Rest!", TextToSpeech.QUEUE_FLUSH, null);
                }
            }, startOfInterval + workout.get(i).duration, TimeUnit.SECONDS);

            startOfInterval += workout.get(i).duration + workout.get(i).restDuration;
        }
        mTTS.speak("Workout completed", TextToSpeech.QUEUE_ADD,null);
    }

}
