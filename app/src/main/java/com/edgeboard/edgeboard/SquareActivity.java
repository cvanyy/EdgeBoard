package com.edgeboard.edgeboard;

import android.content.Context;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.edgeboard.edgeboard.drawing.BaseTouchSquareLayout;

import java.util.Locale;

public class SquareActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        this.tts = new TextToSpeech(this, this);
        TextToSpeechUtils ttsUtils = new TextToSpeechUtils(tts);
        setContentView(R.layout.activity_write);

        LinearLayout layout = (LinearLayout)findViewById(R.id.layout);

        Bundle b = getIntent().getExtras();
        boolean writingState = b.getBoolean("state");
        BaseTouchSquareLayout baseTouchSquareLayout = new BaseTouchSquareLayout(this, vibrator, ttsUtils, writingState);
        layout.addView(baseTouchSquareLayout);
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
}
