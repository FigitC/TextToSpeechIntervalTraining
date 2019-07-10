package com.example.soundqueintervalworkout;

import android.os.Handler;
import android.speech.tts.TextToSpeech;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;

public class IntervalWorkout {
    public ArrayList<ExerciseInterval> workout = new ArrayList<ExerciseInterval>();
    private TextToSpeech mTTS;

    public IntervalWorkout(TextToSpeech mTTS){
        this.mTTS = mTTS;
        mTTS.setSpeechRate(0.8f);
    }

    public void startWorkout(){
        String titleBuffer, start = "3... 2... 1... Start!",rest = "Rest!";
        long duration, restDuration, preQue, postQue;

        for(int i=0; i < workout.size(); i++) {
            titleBuffer = workout.get(i).title;
            duration = workout.get(i).duration * 1000;
            restDuration = workout.get(i).restDuration * 1000;

            if(i != 0) {
                preQue = restDuration * 3/4;
                postQue = restDuration * 1/4;

                mTTS.playSilence(preQue, TextToSpeech.QUEUE_ADD, null);
                mTTS.speak("Next exercise", TextToSpeech.QUEUE_ADD, null);
                mTTS.playSilence(1500, TextToSpeech.QUEUE_ADD, null);
                mTTS.speak(titleBuffer, TextToSpeech.QUEUE_ADD, null);
                mTTS.playSilence(postQue, TextToSpeech.QUEUE_ADD, null);
            }else{
                mTTS.speak("Get Ready.", TextToSpeech.QUEUE_ADD, null);
                mTTS.playSilence(2000, TextToSpeech.QUEUE_ADD, null);
                mTTS.speak("First exercise is", TextToSpeech.QUEUE_ADD, null);
                mTTS.speak(titleBuffer, TextToSpeech.QUEUE_ADD, null);
                mTTS.playSilence(3000, TextToSpeech.QUEUE_ADD, null);
            }

            mTTS.speak(start, TextToSpeech.QUEUE_ADD, null);
            mTTS.playSilence(duration, TextToSpeech.QUEUE_ADD, null);
            mTTS.speak(rest, TextToSpeech.QUEUE_ADD, null);
        }
        mTTS.speak("Workout completed", TextToSpeech.QUEUE_ADD, null);


    }
}
