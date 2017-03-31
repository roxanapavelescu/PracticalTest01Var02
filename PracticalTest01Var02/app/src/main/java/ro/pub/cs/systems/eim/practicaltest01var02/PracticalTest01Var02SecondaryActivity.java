package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PracticalTest01Var02SecondaryActivity extends AppCompatActivity {

    private EditText numberOfClicksTextView = null;
    private Button okButton = null;
    private Button cancelButton = null;



    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.corect:
                    setResult(-1, null);
                    break;
                case R.id.incorect:
                    setResult(-2, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_secondary);

        numberOfClicksTextView = (EditText) findViewById(R.id.result);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("text")) {
            String numberOfClicks = intent.getStringExtra("text");
            numberOfClicksTextView.setText(numberOfClicks);
        }
        okButton = (Button)findViewById(R.id.corect);
        okButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button)findViewById(R.id.incorect);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}
