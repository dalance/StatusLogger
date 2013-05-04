package org.dyndns.dalance.statuslogger;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnCheckedChangeListener, OnClickListener, TagDialog.SelectListner{
	private static final String TAG = MainActivity.class.getSimpleName();
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Init Update
		//Intent intent = new Intent(MainActivity.this, LoggerService.class);
		//intent.setAction("one-shot");
		//startService(intent);

		// default preference
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		editor = pref.edit();
		editor.putBoolean("ServiceRunning", pref.getBoolean("ServiceRunning", false                              ));
		editor.putString ("FormatString",   pref.getString ("FormatString",   "%[Time]: Battery %[BatteryLevel]%"));
        editor.putString ("OutputFilename", pref.getString ("OutputFilename", "StatusLogger/sample.txt"          ));
        editor.putInt    ("Period",         pref.getInt    ("Period",         60                                 ));
        editor.putBoolean("AppendMode",     pref.getBoolean("AppendMode",     false                              ));
        editor.putBoolean("AutoStart",      pref.getBoolean("AutoStart",      false                              ));
        editor.commit();

        ServiceRunningPreferenceChangeListener listener = new ServiceRunningPreferenceChangeListener(this);
        pref.registerOnSharedPreferenceChangeListener(listener);
        
		// init widget		
		ToggleButton button1 = (ToggleButton) findViewById(R.id.toggleButtonService); 
		Switch       button2 = (Switch)       findViewById(R.id.switchAppendMode);
		Switch       button3 = (Switch)       findViewById(R.id.switchAutoStart);
		ImageButton  button4 = (ImageButton)  findViewById(R.id.imageButtonTagDialog);
        button1.setOnCheckedChangeListener(this);
        button2.setOnCheckedChangeListener(this);
        button3.setOnCheckedChangeListener(this);
        button4.setOnClickListener(this);
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
		//getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	@Override
	public void onResume() {
		super.onResume();
		String sampleString = LoggerFormatter.format(this);
		TextView textView = (TextView)findViewById(R.id.textViewSample);
        textView.setText(sampleString);
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

	@Override
	public void onClick(View view) {
		switch(view.getId()){
			case R.id.imageButtonTagDialog: {
				Bundle args = new Bundle();
				args.putString(TagDialog.TITLE, "ƒ^ƒO‘I‘ð");
				DialogFragment df = new TagDialog();
				df.setArguments(args);
				df.show(getFragmentManager(), "TagDialog");
				break;
			}
		}
	}

	@Override
	public void onSelected(String str) {
		EditText editText = (EditText) findViewById(R.id.editTextFormatString);
		int start = editText.getSelectionStart();
		int end = editText.getSelectionEnd();
		Editable editable = editText.getText();
		editable.replace( Math.min( start, end ), Math.max( start, end ), str );
	}
}
