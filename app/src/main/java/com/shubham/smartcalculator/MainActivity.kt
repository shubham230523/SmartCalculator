package com.shubham.smartcalculator

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var textToSpeech: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val speaker = findViewById<ImageView>(R.id.iv_hear)
        val microphone = findViewById<ImageView>(R.id.iv_microphone)
        textToSpeech = TextToSpeech(this, this)
        speaker.setOnClickListener {
            speak()
        }
        microphone.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                .putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
            speechToTextLauncher.launch(intent)

        }
    }

    // launcher for getting text from speech
    private val speechToTextLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val spokenText: String =
                    it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        .let { results ->
                            results?.get(0) ?: ""
                        }
                if (spokenText == "4") {
                    textToSpeech?.speak("Correct", TextToSpeech.QUEUE_FLUSH, null, "")
                } else textToSpeech?.speak("Incorrect", TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }

    //initializing the language for textToSpeech
    override fun onInit(p0: Int) {
        if (p0 != TextToSpeech.ERROR) {
            textToSpeech?.language = Locale.ENGLISH
        }
    }

    //This function converts text to speech
    private fun speak() {
        textToSpeech?.speak("what is 2 multiply by 2 ", TextToSpeech.QUEUE_FLUSH, null, "")
    }
}