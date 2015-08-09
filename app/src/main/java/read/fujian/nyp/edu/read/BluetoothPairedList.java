package read.fujian.nyp.edu.read;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class BluetoothPairedList extends Activity {
	// Debugging for LOGCAT
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = true;


	// declare button for launching website and textview for connection status
	Button tlbutton;
	TextView textView1;

	// EXTRA string to send on to mainactivity
	public static String EXTRA_DEVICE_ADDRESS = "device_address";

	// Member fields
	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		//***************
		checkBTState();

		textView1 = (TextView) findViewById(R.id.connecting);
		textView1.setTextSize(40);
		textView1.setText(" ");

		// Initialize array adapter for paired devices
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

		// Find and set up the ListView for paired devices
		ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);

		// Get the local BluetoothPairedList adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		// Get a set of currently paired devices and append to 'pairedDevices'
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		// Add previosuly paired devices to the array
		if (pairedDevices.size() > 0) {
			findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
			for (BluetoothDevice device : pairedDevices) {
				mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
			}
		} else {
			String noDevices = "Not paired";
			mPairedDevicesArrayAdapter.add(noDevices);
		}
	}

	// Set up on-click listener for the list (nicked this - unsure)
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

			textView1.setText("Connecting...");
			// Get the device MAC address, which is the last 17 chars in the View
			String info = ((TextView) v).getText().toString();
			String address = info.substring(info.length() - 17);

			// Make an intent to start next activity while taking an extra which is the MAC address.
			Intent i = new Intent(BluetoothPairedList.this, DynamicGraphActivity.class);
			i.putExtra(EXTRA_DEVICE_ADDRESS, address);
			startActivity(i);
		}
	};

	private void checkBTState() {
		// Check device has BluetoothPairedList and that it is turned on
		mBtAdapter=BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
		if(mBtAdapter==null) {
			Toast.makeText(getBaseContext(), "Device does not support BluetoothPairedList", Toast.LENGTH_SHORT).show();
		} else {
			if (mBtAdapter.isEnabled()) {
				Log.d(TAG, "...BluetoothPairedList ON...");
			} else {
				//Prompt user to turn on BluetoothPairedList
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, 1);

			}
		}
	}
}