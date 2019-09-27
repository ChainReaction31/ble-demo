package tech.digitalbridge.testble2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "BLETEST2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startBLEActivity(View view){
        Log.d(TAG, "startBLEActivity: You pressed the button, you animal!");
        Intent intent = new Intent(this, RangingActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(TAG, message);
        startActivity(intent);
    }

    public void startBLEGoogleActivity(View view){
        Log.d(TAG, "startBLEGoogleActivity: Google BLE Scanner Activity requested...");
        Intent intent = new Intent(this, BLEScan.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(TAG, message);
        startActivity(intent);
    }
}
