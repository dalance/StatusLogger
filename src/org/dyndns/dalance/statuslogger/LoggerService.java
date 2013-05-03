package org.dyndns.dalance.statuslogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

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
	private AsyncTask<Context, Void, String> mainTask;
	
	@Override
    public void onCreate() {
		Log.d(TAG, "onCreate");
    	phoneStateListener = new LoggerPhoneStateListener(this);
    	alarmManager       = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        telephonyManager   = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);		
		pref               = PreferenceManager.getDefaultSharedPreferences(this);
		editor             = pref.edit();

		Intent i = new Intent();
        i.setClassName("org.dyndns.dalance.statuslogger", "org.dyndns.dalance.statuslogger.LoggerService");
        i.setAction("repeat");
        pendingIntent = PendingIntent.getService(this, 0, i, 0);
	}
    
	@Override
    public int onStartCommand(Intent intent, int flags, int startId){
        long current = System.currentTimeMillis();
		Log.d(TAG, "onStartCommand");

        //èàóùñ{ëÃ
    	telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);

        AsyncTask<Context, Void, String> task = new AsyncTask<Context, Void, String>() {
			@Override
			protected String doInBackground(Context... contexts) {
		        Log.d(TAG, "task start");
				Context context = contexts[0];
				
				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
				String outputFilename = pref.getString("OutputFilename", "sample.txt");
				Boolean appendMode    = pref.getBoolean("AppendMode", true);

				String filePath = Environment.getExternalStorageDirectory() + "/StatusLogger/" + outputFilename;
		        File file = new File(filePath);
		        file.getParentFile().mkdirs();
		 
				try {
					Log.d(TAG, "AppendMode: " + appendMode);
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
				
		        Log.d(TAG, "task finish");	            
		        return "";
			}

			@Override
    		protected void onPostExecute(String result) {
    			return;
    		}
    	};
    	task.execute(this);
		
        //çƒãNìÆ
		int period = pref.getInt("Period", 60);

        String intentAction = intent.getAction();
        if(intentAction.equals("start") || intentAction.equals("repeat")){
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (period*1000), pendingIntent);
            editor.putBoolean("ServiceRunning", true);
            editor.commit();
        }else if(intentAction.equals("stop")){
            alarmManager.cancel(pendingIntent);
            editor.putBoolean("ServiceRunning", false);
            editor.commit();
        }
        Log.d(TAG, "onStartCommandFinish: " + ( System.currentTimeMillis() - current) );

        return START_REDELIVER_INTENT;
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
