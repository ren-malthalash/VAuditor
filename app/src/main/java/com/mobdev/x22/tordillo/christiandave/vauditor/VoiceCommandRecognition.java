package com.mobdev.x22.tordillo.christiandave.vauditor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class VoiceCommandRecognition implements RecognitionListener {

    private boolean bSpeechOn;
    private Context context;
    private FloatingActionButton fab;
    private View layout;

    public VoiceCommandRecognition(Context context) {
        this.context = context;
        bSpeechOn = false;
    }

    public VoiceCommandRecognition(Context context, FloatingActionButton fab, View layout) {
        this.context = context;
        this.fab = fab;
        this.bSpeechOn = false;
        this.layout = layout;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {
        String convertedSpeech = Objects.requireNonNull(bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)).get(0);
        String[] speechArray = convertedSpeech.split("\\s+");
        StringBuilder toastString = new StringBuilder();
        double amount;
        Log.d("VOICE RECOGNITION", convertedSpeech);

        StringBuilder toAccountName = new StringBuilder();
        StringBuilder fromAccountName = new StringBuilder();
        StringBuilder itemName = new StringBuilder();

        Toast toast = Toast.makeText(context, toastString.toString(), Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(layout);
        TextView text = (TextView) layout.findViewById(R.id.text);
        layout.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_500)));
        try {
            if (convertedSpeech.contains("deposit")) {
                amount = Double.parseDouble(speechArray[1]);
                toastString.append("Depositing ").append(amount);

                for (int i = 0; i < speechArray.length; i++) {
                    if (speechArray[i].equals("to")) {
                        toastString.append(" to ");
                        for (int j = ++i; j < speechArray.length; j++) {
                            toastString.append(speechArray[j].toUpperCase()).append(" ");
                            toAccountName.append(speechArray[j]).append(" ");
                        }
                    }
                }
                layout.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_500)));
                text.setText(toastString.toString());
                toast.show();
            } else if (convertedSpeech.contains("transfer")) {
                amount = Double.parseDouble(speechArray[1]);
                toastString.append("Transferring ").append(amount); // + " from " + speechArray[3] + " to " + speechArray[5];

                for (int i = 0; i < speechArray.length; i++) {
                    if (speechArray[i].equals("from")) {
                        toastString.append(" from ");
                        for (int j = ++i; j < speechArray.length; j++) {
                            if (speechArray[j].equals("to")) {
                                toastString.append(" to ");
                                for (int k = ++j; k < speechArray.length; k++) {
                                    toastString.append(speechArray[k].toUpperCase()).append(" ");
                                    toAccountName.append(speechArray[k]).append(" ");
                                }
                                break;
                            } else {
                                toastString.append(speechArray[j].toUpperCase()).append(" ");
                                fromAccountName.append(speechArray[j]).append(" ");
                            }
                        }
                        layout.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_500)));
                        text.setText(toastString.toString());
                        toast.show();
                        break;
                    } else if (speechArray[i].equals("to")) {
                        toastString.append(" to ");
                        for (int j = ++i; j < speechArray.length; j++) {
                            if (speechArray[j].equals("from")) {
                                toastString.append(" from ");
                                for (int k = ++j; k < speechArray.length; k++) {
                                    toastString.append(speechArray[k].toUpperCase()).append(" ");
                                    fromAccountName.append(speechArray[k]).append(" ");
                                }
                                break;
                            } else {
                                toastString.append(speechArray[j].toUpperCase()).append(" ");
                                toAccountName.append(speechArray[j]).append(" ");
                            }
                        }
                        layout.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_500)));
                        text.setText(toastString.toString());
                        toast.show();
                        break;
                    }
                    layout.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_200)));
                    text.setText("I'm sorry, I didn't understand you, please try again.");
                    toast.show();
                }
            } else if (convertedSpeech.contains("purchased") || convertedSpeech.contains("purchase") || convertedSpeech.contains("bought") || convertedSpeech.contains("bot")){
                toastString.append("Purchased ");
                for (int i = 1; i < speechArray.length; i++) {
                    if (speechArray[i].equals("using")) {
                        toastString.append(" using ");
                        for (int j = ++i; j < speechArray.length; j++) {
                            toastString.append(speechArray[i].toUpperCase()).append(" ");
                            fromAccountName.append(speechArray[i]).append(" ");
                        }
                        break;
                    } else {
                        toastString.append(speechArray[i].toUpperCase()).append(" ");
                        itemName.append(speechArray[i]).append(" ");
                    }
                }
                layout.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_500)));
                text.setText(toastString.toString());
                toast.show();
            }
        } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
            layout.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_200)));
            text.setText("Number not heard");
            toast.show();
        }
        fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple_500)));
        bSpeechOn = false;
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    public boolean isSpeechOn() {
        return bSpeechOn;
    }

    public void setSpeechOn(boolean bSpeechOn) {
        this.bSpeechOn = bSpeechOn;
    }
}
