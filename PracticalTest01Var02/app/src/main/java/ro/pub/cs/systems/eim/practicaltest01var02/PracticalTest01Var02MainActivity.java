package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class PracticalTest01Var02MainActivity extends AppCompatActivity {

    Button b1 = null;
    Button b2 = null;
    EditText nr1 = null;
    EditText nr2 = null;
    Button next_act = null;
    TextView rez = null;
    private static int serviceStatus = Constants.SERVICE_STOPPED;
    int count = 0;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Mesaj primit]", intent.getStringExtra("message"));
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

    private class OnClickL implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String pos1 = nr1.getText().toString();
            String pos2 = nr2.getText().toString();

            boolean clic = false;
            switch (v.getId()) {
                case R.id.minus:
                    boolean isNumber = Pattern.matches("[0-9]+", pos1);
                    if (!isNumber) {
                        Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_LONG).show();
                        break;
                    }

                    isNumber = Pattern.matches("[0-9]+", pos2);
                    if (!isNumber) {
                        Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_LONG).show();
                        break;
                    }

                    int num1 = Integer.parseInt(pos1);
                    int num2 = Integer.parseInt(pos2);
                    clic = true;
                    rez.setText(String.valueOf(num1) + "-" + String.valueOf(num2) + "=" + String.valueOf(num1 - num2));
                    break;
                case R.id.plus:
                    boolean isNumber1 = Pattern.matches("[0-9]+", pos1);
                    if (!isNumber1) {
                        Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_LONG).show();
                        break;
                    }

                    isNumber1 = Pattern.matches("[0-9]+", pos2);
                    if (!isNumber1) {
                        Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_LONG).show();
                        break;
                    }

                    clic = true;
                    int numm1 = Integer.parseInt(pos1);
                    int numm2 = Integer.parseInt(pos2);
                    rez.setText(String.valueOf(numm1) + "+" + String.valueOf(numm2) + "=" + String.valueOf(numm1 + numm2));
                    break;
                case R.id.next_act:
                   /* EditText e11 = (EditText) findViewById(R.id.textView4);
                    EditText e22 = (EditText) findViewById(R.id.textView5);
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    int numberOfClicks = Integer.parseInt(e11.getText().toString()) +
                            Integer.parseInt(e22.getText().toString());
                    intent.putExtra("numberOfClicks", numberOfClicks);
                    startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE); */

                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02SecondaryActivity.class);
                    String s = rez.getText().toString();
                    intent.putExtra("text", s);
                    startActivityForResult(intent, 2);
                    break;
            }

            if (clic && serviceStatus == Constants.SERVICE_STOPPED) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02Service.class);
                    int num1 = Integer.parseInt(pos1);
                    int num2 = Integer.parseInt(pos2);
                    intent.putExtra("firstNumber", pos1);
                    intent.putExtra("secondNumber", pos2);
                    getApplicationContext().startService(intent);
                    serviceStatus = Constants.SERVICE_STARTED;
            }

        }
    }

    OnClickL click = new OnClickL();

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("numar1", nr1.getText().toString());
        savedInstanceState.putString("numar2", nr2.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 2) {
            Toast.makeText(this, "The activity returned with result" + String.valueOf(resultCode) , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String s = "numerele" + "    ";
        if (savedInstanceState.containsKey("numar1")) {
            nr1.setText(savedInstanceState.getString("numar1"));
            s += "Primul numar" + savedInstanceState.getString("numar1");
        }
        if (savedInstanceState.containsKey("numar2")) {
            nr2.setText(savedInstanceState.getString("numar2"));
            s += "     Al doilea Numar " + savedInstanceState.getString("numar2");
        }
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onDestroy()
    {
        Intent intent = new Intent(this, PracticalTest01Var02Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_main);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(String.valueOf(Constants.actionTypes[index]));
        }

        b1 = (Button) findViewById(R.id.minus);
        b2 = (Button) findViewById(R.id.plus);
        nr1 = (EditText) findViewById(R.id.num1);
        nr2 = (EditText) findViewById(R.id.num2);
        next_act = (Button) findViewById(R.id.next_act);
        rez = (TextView) findViewById(R.id.rezult);
        b1.setOnClickListener(click);
        b2.setOnClickListener(click);
        next_act.setOnClickListener(click);
    }


}
