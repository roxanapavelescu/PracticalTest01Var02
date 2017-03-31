package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

/**
 * Created by Roxxana on 3/31/2017.
 */

public class ProcessingThread  extends Thread {
    private Context context = null;
    private boolean isRunning = true;

    private Random random = new Random();

    private int sum = 0;
    private int dif = 0;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        sum = firstNumber + secondNumber;
        dif = firstNumber - secondNumber;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        sendMessage1();
        sleep();
        sendMessage2();
        sleep();

        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage1() {
        Intent intent = new Intent();
        intent.setAction(String.valueOf(Constants.actionTypes[0]));
        intent.putExtra("message", "Suma " + String.valueOf(sum) );
        context.sendBroadcast(intent);
    }

    private void sendMessage2() {
        Intent intent = new Intent();
        intent.setAction(String.valueOf(Constants.actionTypes[1]));
        intent.putExtra("message", "Diferenta " + String.valueOf(dif) );
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
