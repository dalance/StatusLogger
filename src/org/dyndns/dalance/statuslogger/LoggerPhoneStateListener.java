package org.dyndns.dalance.statuslogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.util.Log;

public class LoggerPhoneStateListener extends PhoneStateListener {
	private static final String TAG = LoggerPhoneStateListener.class.getSimpleName();
	private Context context;
	
	LoggerPhoneStateListener (Context context_) {
		context = context_;	
	}
	
	@Override
	public void onSignalStrengthsChanged(SignalStrength signalStrength) {
		Log.d(TAG, "onSignalStrengthChanged");
		
		SharedPreferences pref = context.getSharedPreferences("Status", 0);
		SharedPreferences.Editor editor = pref.edit();
        
		Method[] methods = SignalStrength.class.getMethods();
		for(Method mthd:methods) {
			String methodName = mthd.getName();
			try {
				if(methodName.contains("get") && mthd.getReturnType() == int.class ) {
					String key = methodName.replace("get", "");
					Log.d(TAG, "key: " + key);
					String ret = mthd.invoke(signalStrength, new Object[]{}).toString();
			        editor.putString(key, ret);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
        editor.commit();
	}
}