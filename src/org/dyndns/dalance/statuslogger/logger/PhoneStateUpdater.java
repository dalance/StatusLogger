package org.dyndns.dalance.statuslogger.logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

public class PhoneStateUpdater extends PhoneStateListener {
	@SuppressWarnings("unused")
	private static final String TAG = PhoneStateUpdater.class.getSimpleName();
	private Context context;
	
	PhoneStateUpdater (Context context_) {
		context = context_;	
	}
	
	@Override
	public void onSignalStrengthsChanged(SignalStrength signalStrength) {		
		SharedPreferences pref = context.getSharedPreferences("Status", 0);
		SharedPreferences.Editor editor = pref.edit();
        
		Method[] methods = SignalStrength.class.getMethods();
		for(Method mthd:methods) {
			String methodName = mthd.getName();
			try {
				if(methodName.contains("get") && mthd.getReturnType() == int.class ) {
					String key = methodName.replace("get", "");
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