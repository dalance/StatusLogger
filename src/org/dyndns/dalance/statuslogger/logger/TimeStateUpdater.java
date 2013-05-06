package org.dyndns.dalance.statuslogger.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;

public class TimeStateUpdater {
	@SuppressWarnings("unused")
	private static final String TAG = TimeStateUpdater.class.getSimpleName();
	
	public static void update(Context context) {
		SharedPreferences pref = context.getSharedPreferences("Status", 0);
		SharedPreferences.Editor editor = pref.edit();
		
		Locale locale = Locale.getDefault();
	    SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy'/'MM'/'dd", locale);
	    SimpleDateFormat timeFormat  = new SimpleDateFormat("kk':'mm':'ss", locale);
	    SimpleDateFormat yearFormat  = new SimpleDateFormat("yyyy", locale);
	    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", locale);
	    SimpleDateFormat dayFormat   = new SimpleDateFormat("dd", locale);
	    SimpleDateFormat hourFormat  = new SimpleDateFormat("kk", locale);
	    SimpleDateFormat minFormat   = new SimpleDateFormat("mm", locale);
	    SimpleDateFormat secFormat   = new SimpleDateFormat("mm", locale);
	    
	    Date currentDate    = new Date();
	    long realUptimeMillis    = SystemClock.elapsedRealtime();
	    long realUptimeSec       = (realUptimeMillis / 1000) % 60;
	    long realUptimeSecRest   = (realUptimeMillis / 1000) - realUptimeSec;
	    long realUptimeMin       = (realUptimeSecRest / 60) % 60;
	    long realUptimeMinRest   = (realUptimeSecRest / 60) - realUptimeMin;
	    long realUptimeHour      = (realUptimeMinRest / 60) % 24;
	    long realUptimeHourRest  = (realUptimeMinRest / 60) - realUptimeHour;
	    long realUptimeDay       = (realUptimeHourRest / 24) % 30;
	    long realUptimeDayRest   = (realUptimeHourRest / 24) - realUptimeDay;
	    long realUptimeMonth     = (realUptimeDayRest / 30) % 12;
	    long realUptimeMonthRest = (realUptimeDayRest / 30) - realUptimeMonth;
	    long realUptimeYear      = (realUptimeMonthRest / 12);
	    long uptimeMillis    = SystemClock.uptimeMillis();
	    long uptimeSec       = (uptimeMillis / 1000) % 60;
	    long uptimeSecRest   = (uptimeMillis / 1000) - uptimeSec;
	    long uptimeMin       = (uptimeSecRest / 60) % 60;
	    long uptimeMinRest   = (uptimeSecRest / 60) - uptimeMin;
	    long uptimeHour      = (uptimeMinRest / 60) % 24;
	    long uptimeHourRest  = (uptimeMinRest / 60) - uptimeHour;
	    long uptimeDay       = (uptimeHourRest / 24) % 30;
	    long uptimeDayRest   = (uptimeHourRest / 24) - uptimeDay;
	    long uptimeMonth     = (uptimeDayRest / 30) % 12;
	    long uptimeMonthRest = (uptimeDayRest / 30) - uptimeMonth;
	    long uptimeYear      = (uptimeMonthRest / 12);
	    //Date realUptimeDate = new Date(SystemClock.elapsedRealtime());
	    //Date uptimeDate     = new Date(SystemClock.uptimeMillis());

	    String uptimeDate = String.valueOf(uptimeYear) + "/" + String.valueOf(uptimeMonth) + "/" + String.valueOf(uptimeDay);
	    String uptimeTime = String.valueOf(uptimeHour) + ":" + String.valueOf(uptimeMin) + ":" + String.valueOf(uptimeSec);
	    String realUptimeDate = String.valueOf(realUptimeYear) + "/" + String.valueOf(realUptimeMonth) + "/" + String.valueOf(realUptimeDay);
	    String realUptimeTime = String.valueOf(realUptimeHour) + ":" + String.valueOf(realUptimeMin) + ":" + String.valueOf(realUptimeSec);
	    
		editor.putString("Date",  dateFormat.format(currentDate) );
		editor.putString("Time",  timeFormat.format(currentDate) );
		editor.putString("Year",  yearFormat.format(currentDate) );
		editor.putString("Month", monthFormat.format(currentDate));
		editor.putString("Day",   dayFormat.format(currentDate)  );
		editor.putString("Hour",  hourFormat.format(currentDate) );
		editor.putString("Min",   minFormat.format(currentDate)  );
		editor.putString("Sec",   secFormat.format(currentDate)  );
		editor.putString("UptimeDate",  uptimeDate                 );
		editor.putString("UptimeTime",  uptimeTime                 );
		editor.putString("UptimeYear",  String.valueOf(uptimeYear) );
		editor.putString("UptimeMonth", String.valueOf(uptimeMonth));
		editor.putString("UptimeDay",   String.valueOf(uptimeDay)  );
		editor.putString("UptimeHour",  String.valueOf(uptimeHour) );
		editor.putString("UptimeMin",   String.valueOf(uptimeMin)  );
		editor.putString("UptimeSec",   String.valueOf(uptimeSec)  );
		editor.putString("RealUptimeDate",  realUptimeDate                 );
		editor.putString("RealUptimeTime",  realUptimeTime                 );
		editor.putString("RealUptimeYear",  String.valueOf(realUptimeYear) );
		editor.putString("RealUptimeMonth", String.valueOf(realUptimeMonth));
		editor.putString("RealUptimeDay",   String.valueOf(realUptimeDay)  );
		editor.putString("RealUptimeHour",  String.valueOf(realUptimeHour) );
		editor.putString("RealUptimeMin",   String.valueOf(realUptimeMin)  );
		editor.putString("RealUptimeSec",   String.valueOf(realUptimeSec)  );
		editor.commit();
	}

}
