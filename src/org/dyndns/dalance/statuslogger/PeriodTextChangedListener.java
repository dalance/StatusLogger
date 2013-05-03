package org.dyndns.dalance.statuslogger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class PeriodTextChangedListener implements TextWatcher {
	private static final String TAG = PeriodTextChangedListener.class.getSimpleName();
	private Context context;
	
	PeriodTextChangedListener (Context context_) {
		context = context_;	
	}

	@Override
	public void afterTextChanged(Editable s) {
		Log.d(TAG, "afterTextChanged: " + s.toString());
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		try {
			int period = Integer.parseInt(s.toString());
			editor.putInt("Period", period);
			editor.commit();
		} catch(NumberFormatException e) {
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}
}
