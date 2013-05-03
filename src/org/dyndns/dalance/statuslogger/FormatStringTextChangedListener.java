package org.dyndns.dalance.statuslogger;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

public class FormatStringTextChangedListener implements TextWatcher {
	private static final String TAG = FormatStringTextChangedListener.class.getSimpleName();
	private Activity activity;
	
	FormatStringTextChangedListener (Activity activity_) {
		activity = activity_;	
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		Log.d(TAG, "afterTextChanged: " + s.toString());
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
		SharedPreferences.Editor editor = pref.edit();
        editor.putString("FormatString", s.toString());
		editor.commit();
		
		String sampleString = LoggerFormatter.format(activity);
		TextView textView = (TextView)activity.findViewById(R.id.textViewSample);
        textView.setText(sampleString);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}
}
