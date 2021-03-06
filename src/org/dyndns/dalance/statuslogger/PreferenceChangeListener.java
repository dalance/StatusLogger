package org.dyndns.dalance.statuslogger;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;
import android.widget.ToggleButton;

public class PreferenceChangeListener implements OnSharedPreferenceChangeListener {
	private static final String TAG = PreferenceChangeListener.class.getSimpleName();
	private Activity activity;
	
	PreferenceChangeListener(Activity activity_) {
		activity = activity_;	
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences pref, String key) {
		if(key == "ServiceRunning") {
			Log.d(TAG, "ServiceRunning pref changed");
			ToggleButton button = (ToggleButton) activity.findViewById(R.id.toggleButtonService); 
	        button.setChecked(pref.getBoolean("ServiceRunning", false));
		}
	}
}
