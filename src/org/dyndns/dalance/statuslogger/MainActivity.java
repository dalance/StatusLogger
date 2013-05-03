package org.dyndns.dalance.statuslogger;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnCheckedChangeListener{
	private static final String TAG = MainActivity.class.getSimpleName();
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// default preference
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		editor = pref.edit();
		editor.putBoolean("ServiceRunning", pref.getBoolean("ServiceRunning", false                 ));
		editor.putString ("FormatString",   pref.getString ("FormatString",   "%[LteSignalStrength]"));
        editor.putString ("OutputFilename", pref.getString ("OutputFilename", "sample.txt"          ));
        editor.putInt    ("Period",         pref.getInt    ("Period",         60                    ));
        editor.putBoolean("AppendMode",     pref.getBoolean("AppendMode",     false                 ));
        editor.putBoolean("AutoStart",      pref.getBoolean("AutoStart",      false                 ));
        editor.commit();

        ServiceRunningPreferenceChangeListener listener = new ServiceRunningPreferenceChangeListener(this);
        pref.registerOnSharedPreferenceChangeListener(listener);
        
		// init widget		
		ToggleButton button1 = (ToggleButton) findViewById(R.id.toggleButtonService); 
		Switch       button2 = (Switch)       findViewById(R.id.switchAppendMode);
		Switch       button3 = (Switch)       findViewById(R.id.switchAutoStart);
        button1.setOnCheckedChangeListener(this);
        button2.setOnCheckedChangeListener(this);
        button3.setOnCheckedChangeListener(this);
        button1.setChecked(pref.getBoolean("ServiceRunning", false));
        button2.setChecked(pref.getBoolean("AppendMode", false));
        button3.setChecked(pref.getBoolean("AutoStart", false));

        EditText editText1 = (EditText) findViewById(R.id.editTextFormatString);
        EditText editText2 = (EditText) findViewById(R.id.editTextOutputFilename);
        EditText editText3 = (EditText) findViewById(R.id.editTextPeriod);

        FormatStringTextChangedListener listener1 = new FormatStringTextChangedListener(this);
        editText1.addTextChangedListener(listener1);
        editText1.setText(pref.getString("FormatString", ""));

        OutputFilenameTextChangedListener listener2 = new OutputFilenameTextChangedListener(this);
        editText2.addTextChangedListener(listener2);
        editText2.setText(pref.getString("OutputFilename", ""));

        PeriodTextChangedListener listener3 = new PeriodTextChangedListener(this);
        editText3.addTextChangedListener(listener3);
        Integer period = pref.getInt("Period", 0);
        editText3.setText(period.toString());        
        
        Log.d(TAG, "onCreate");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton view, boolean isChecked) {
		switch(view.getId()){
    		case R.id.toggleButtonService: {
    			if(isChecked) {
    				Log.d(TAG, "logger start");
    				Intent intent = new Intent(MainActivity.this, LoggerService.class);
            		intent.setAction("start");
            		startService(intent);
    			} else {
    				Log.d(TAG, "logger stop");    				
    				Intent intent = new Intent(MainActivity.this, LoggerService.class);
            		intent.setAction("stop");
            		startService(intent);
    			}
    			break;
    		}
    		case R.id.switchAppendMode: {
				Log.d(TAG, "AppendMode: " + isChecked);
    			editor.putBoolean("AppendMode", isChecked);
    			editor.commit();
    			break;
    		}
    		case R.id.switchAutoStart: {
				Log.d(TAG, "AutoStart: " + isChecked);
    			editor.putBoolean("AutoStart", isChecked);
    			editor.commit();
    			break;
    		}
    	}
	}
}
