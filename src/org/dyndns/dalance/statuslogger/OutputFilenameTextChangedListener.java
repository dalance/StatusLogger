package org.dyndns.dalance.statuslogger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class OutputFilenameTextChangedListener implements TextWatcher {
	private static final String TAG = OutputFilenameTextChangedListener.class.getSimpleName();
	private Context context;
	
	OutputFilenameTextChangedListener (Context context_) {
		context = context_;	
	}

	@Override
	public void afterTextChanged(Editable s) {
		Log.d(TAG, "afterTextChanged: " + s.toString());
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
        editor.putString("OutputFilename", s.toString());
		editor.commit();
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
