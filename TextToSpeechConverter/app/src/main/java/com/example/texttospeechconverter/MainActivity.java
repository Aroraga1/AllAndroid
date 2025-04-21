package com.example.texttospeechconverter;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnInitListener {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    private TextToSpeech textToSpeech;
    private EditText editText;
    private Button btnConvert;
    private Button btnSpeechToText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editText = findViewById(R.id.editText);
        btnConvert = findViewById(R.id.btnConvert);
        btnSpeechToText = findViewById(R.id.btnSpeechToText);

        // Initialize Text-to-Speech engine
        textToSpeech = new TextToSpeech(this, this);

        // Button click listener for Text-to-Speech
        btnConvert.setOnClickListener(v -> {
            String text = editText.getText().toString();
            if (!text.isEmpty()) {
                convertTextToSpeech(text);
            } else {
                Toast.makeText(MainActivity.this, "Please enter text", Toast.LENGTH_SHORT).show();
            }
        });

        // Button click listener for Speech-to-Text
        btnSpeechToText.setOnClickListener(v -> {
            startSpeechToText();
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language to English
            int langResult = textToSpeech.setLanguage(Locale.US);
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Text-to-Speech initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void convertTextToSpeech(String text) {
        // Set UtteranceProgressListener for TTS
        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // Called when TTS starts
            }

            @Override
            public void onDone(String utteranceId) {
                // Called when TTS finishes speaking
            }

            @Override
            public void onError(String utteranceId) {
                // Called if an error occurs during speech synthesis
                Toast.makeText(MainActivity.this, "Error during speech synthesis", Toast.LENGTH_SHORT).show();
            }
        });

        // Speak the text
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private void startSpeechToText() {
        // Check if the app has the necessary permissions to record audio
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        } else {
            // Start Speech-to-Text Intent
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            // Get the speech input
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                // Display the speech-to-text result in the EditText
                editText.setText(result.get(0));
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
