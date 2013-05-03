package org.dyndns.dalance.statuslogger;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoggerFormatter {
	private static final String TAG = LoggerFormatter.class.getSimpleName();
	static final String[] KEYWORD = new String[] {
		"%[LteSignalStrength]", "%[LteRssnr]", "%[LteRsrq]", "%[LteRsrp]",
		"%[LteLevel]", "%[LteDbm]", "%[LteCqi]", "%[LteAsuLevel]",
		"%[Date]", "%[Time]" 
	};
	
	public static String format(Context context) {
		SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(context);
		String formatString = defaultPref.getString("FormatString", "");
		SharedPreferences statusPref = context.getSharedPreferences("Status", 0);
        
		Date date = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy'/'MM'/'dd");
	    SimpleDateFormat timeFormat = new SimpleDateFormat("kk':'mm':'ss");
	
		formatString = formatString.replace( "%[LteSignalStrength]", statusPref.getString("LteSignalStrenght", "Unknown"));
		formatString = formatString.replace( "%[LteRssnr]",          statusPref.getString("LteRssnr",          "Unknown"));
		formatString = formatString.replace( "%[LteRsrq]",           statusPref.getString("LteRsrq",           "Unknown"));
		formatString = formatString.replace( "%[LteRsrp]",           statusPref.getString("LteRsrp",           "Unknown"));
		formatString = formatString.replace( "%[LteLevel]",          statusPref.getString("LteLevel",          "Unknown"));
		formatString = formatString.replace( "%[LteDbm]",            statusPref.getString("LteDbm",            "Unknown"));
		formatString = formatString.replace( "%[LteCqi]",            statusPref.getString("LteCqi",            "Unknown"));
		formatString = formatString.replace( "%[LteAsuLevel]",       statusPref.getString("LteAsuLevel",       "Unknown"));
		formatString = formatString.replace( "%[Date]",              dateFormat.format(date));
		formatString = formatString.replace( "%[Time]",              timeFormat.format(date));
		return formatString;
	}
}
