package org.dyndns.dalance.statuslogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class LoggerService extends Service {
	private static final String TAG = LoggerService.class.getSimpleName();

	private TelephonyManager telephonyManager;
	private AlarmManager alarmManager;
	private LoggerPhoneStateListener phoneStateListener;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private PendingIntent pendingIntent;
	
	public static Boolean isRunning(ActivityManager activityManager) {
		List<RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
		for (RunningServiceInfo info : services) {
			if (LoggerService.class.getCanonicalName().equals(info.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
    public void onCreate() {
		Log.d(TAG, "onCreate");
    	phoneStateListener   = new LoggerPhoneStateListener(this);
    	alarmManager         = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        telephonyManager     = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);		
		pref                 = PreferenceManager.getDefaultSharedPreferences(this);
		editor               = pref.edit();

		Intent i = new Intent();
        i.setClassName("org.dyndns.dalance.statuslogger", "org.dyndns.dalance.statuslogger.LoggerService");
        i.setAction("repeat");
        pendingIntent = PendingIntent.getService(this, 0, i, 0);
	}
    
	@Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "LogUpdateStart: " + System.currentTimeMillis());

		//çƒãNìÆó\ñÒ
		int period = pref.getInt("Period", 60);
		Boolean autoStart= pref.getBoolean("AutoStart", false);

        String intentAction = intent.getAction();
        if(intentAction.equals("start") || intentAction.equals("repeat")){
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (period*1000), pendingIntent);
            editor.putBoolean("ServiceRunning", true);
            editor.commit();
        }else if(intentAction.equals("stop")){
            alarmManager.cancel(pendingIntent);
            editor.putBoolean("ServiceRunning", false);
            editor.commit();
        }else if(intentAction.equals("auto_start")){
        	if(autoStart) {
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (period*1000), pendingIntent);
                editor.putBoolean("ServiceRunning", true);
                editor.commit();
        	} else {
        		return START_NOT_STICKY;
        	}
        }else if(intentAction.equals("one_shot")){
        }


        //èàóùñ{ëÃ
    	telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);

        AsyncTask<Context, Void, String> task = new AsyncTask<Context, Void, String>() {
			@Override
			protected String doInBackground(Context... contexts) {
				Context context = contexts[0];
				
				LoggerBatteryStateListener.receive(context);
				
				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
				String outputFilename = pref.getString("OutputFilename", "sample.txt");
				Boolean appendMode    = pref.getBoolean("AppendMode", true);

				String filePath = Environment.getExternalStorageDirectory() + "/" + outputFilename;
		        File file = new File(filePath);
		        file.getParentFile().mkdirs();
		 
				try {
					FileOutputStream fos = new FileOutputStream(file, appendMode);
					OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
					BufferedWriter bw = new BufferedWriter(osw);
			        bw.write(LoggerFormatter.format(context) + "\n");
			        bw.flush();
			        bw.close();
			    } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	            } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		        Log.d(TAG, "LogUpdateFinish: " + System.currentTimeMillis());
		        return "";
			}

			@Override
    		protected void onPostExecute(String result) {
    			return;
    		}
    	};
    	task.execute(this);
		
        return START_NOT_STICKY;
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
