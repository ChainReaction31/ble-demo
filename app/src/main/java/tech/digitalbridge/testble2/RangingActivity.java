package tech.digitalbridge.testble2;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import tech.digitalbridge.oshblebeacons.BLEBeacon;

public class RangingActivity extends Activity {
    protected static final String TAG = "RangingActivity";
    BLEBeacon blebBeacon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blebBeacon = new BLEBeacon(this.getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        blebBeacon = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        blebBeacon.BeaconManagerSetup();
    }


    @Override
    public void onPause() {
        super.onPause();
        blebBeacon.printBeaconList();
        blebBeacon.unbind();
    }
}


