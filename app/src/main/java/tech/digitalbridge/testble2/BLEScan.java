package tech.digitalbridge.testble2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.neovisionaries.bluetooth.ble.advertising.ADPayloadParser;
import com.neovisionaries.bluetooth.ble.advertising.ADStructure;
import com.neovisionaries.bluetooth.ble.advertising.EddystoneURL;

import java.util.List;

public class BLEScan extends AppCompatActivity {
    private final static String TAG = "BLEActivity";

    BluetoothManager bluetoothManager;
    BluetoothAdapter bluetoothAdapter;
    BluetoothLeScanner bleScanner;
    ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
//            Log.d(TAG, "Callback Type: " + callbackType);
//            Log.d(TAG, "Result Address: " + result.getDevice().getAddress());
            List<ADStructure> structures = ADPayloadParser.getInstance().parse(result.getScanRecord().getBytes());

            for (ADStructure structure: structures){
                if(structure instanceof EddystoneURL){
                    EddystoneURL eddystoneURL = (EddystoneURL)structure;
                    Log.d(TAG, "URL: " + eddystoneURL.getURL());
                    Log.d(TAG, "Tx Power: " + eddystoneURL.getTxPower());
                    Log.d(TAG, "Tx Power (res): " + result.getTxPower());
                    Log.d(TAG, "RSSI: " + result.getRssi());
                    Log.d(TAG, "RSSI(res): " + result.getRssi());
                    Log.d(TAG, "Est. Distance: " + getDistance(result.getRssi(), eddystoneURL.getTxPower()));

                }
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for(ScanResult result: results) {
                Log.d(TAG, "onBatchScanResults: " + result.toString());
            }
        }
    };
    BluetoothAdapter.LeScanCallback bleScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord){
            // Parse Payload of the Advertisement
            List<ADStructure> structures = ADPayloadParser.getInstance().parse(scanRecord);

            for (ADStructure structure: structures){
                if(structure instanceof EddystoneURL){
                    EddystoneURL eddystoneURL = (EddystoneURL)structure;
                    Log.d(TAG, "Tx Power: " + eddystoneURL.getTxPower());
                    Log.d(TAG, "URL: " + eddystoneURL.getURL());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blescan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        bluetoothManager
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bleScanner = bluetoothAdapter.getBluetoothLeScanner();
        Log.d(TAG, "onCreate: " + bleScanner.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bleScanner != null) {
            Log.d(TAG, "Scan should start now...");
            bleScanner.startScan(scanCallback);
        }else {
            Log.d(TAG, "BLE Scanner not available");
        }
    }

    @Override
    protected void onPause() {
        bleScanner.stopScan(scanCallback);
        super.onPause();
    }



    double getDistance(int rssi, int txPower) {
        /*
         * RSSI = TxPower - 10 * n * lg(d)
         * n = 2 (in free space)
         *
         * d = 10 ^ ((TxPower - RSSI) / (10 * n))
         */

        return Math.pow(10d, ((double) txPower - rssi) / (10 * 2));
    }


}
