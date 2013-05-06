package org.dyndns.dalance.statuslogger.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class StringFormatter {
	@SuppressWarnings("unused")
	private static final String TAG = StringFormatter.class.getSimpleName();
	
	public static String format(Context context) {
		SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(context);
		String formatString = defaultPref.getString("FormatString", "");
		SharedPreferences statusPref = context.getSharedPreferences("Status", 0);
        
		//Date date = new Date();
	    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy'/'MM'/'dd");
	    //SimpleDateFormat timeFormat = new SimpleDateFormat("kk':'mm':'ss");
        
		formatString = formatString.replace( "%[Date]",               statusPref.getString("Date",               "Unknown"));
		formatString = formatString.replace( "%[Time]",               statusPref.getString("Time",               "Unknown"));
		formatString = formatString.replace( "%[Year]",               statusPref.getString("Year",               "Unknown"));
		formatString = formatString.replace( "%[Month]",              statusPref.getString("Month",              "Unknown"));
		formatString = formatString.replace( "%[Day]",                statusPref.getString("Day",                "Unknown"));
		formatString = formatString.replace( "%[Hour]",               statusPref.getString("Hour",               "Unknown"));
		formatString = formatString.replace( "%[Min]",                statusPref.getString("Min",                "Unknown"));
		formatString = formatString.replace( "%[Sec]",                statusPref.getString("Sec",                "Unknown"));
		formatString = formatString.replace( "%[UptimeDate]",         statusPref.getString("UptimeDate",         "Unknown"));
		formatString = formatString.replace( "%[UptimeTime]",         statusPref.getString("UptimeTime",         "Unknown"));
		formatString = formatString.replace( "%[UptimeYear]",         statusPref.getString("UptimeYear",         "Unknown"));
		formatString = formatString.replace( "%[UptimeMonth]",        statusPref.getString("UptimeMonth",        "Unknown"));
		formatString = formatString.replace( "%[UptimeDay]",          statusPref.getString("UptimeDay",          "Unknown"));
		formatString = formatString.replace( "%[UptimeHour]",         statusPref.getString("UptimeHour",         "Unknown"));
		formatString = formatString.replace( "%[UptimeMin]",          statusPref.getString("UptimeMin",          "Unknown"));
		formatString = formatString.replace( "%[UptimeSec]",          statusPref.getString("UptimeSec",          "Unknown"));
		formatString = formatString.replace( "%[RealUptimeDate]",     statusPref.getString("RealUptimeDate",     "Unknown"));
		formatString = formatString.replace( "%[RealUptimeTime]",     statusPref.getString("RealUptimeTime",     "Unknown"));
		formatString = formatString.replace( "%[RealUptimeYear]",     statusPref.getString("RealUptimeYear",     "Unknown"));
		formatString = formatString.replace( "%[RealUptimeMonth]",    statusPref.getString("RealUptimeMonth",    "Unknown"));
		formatString = formatString.replace( "%[RealUptimeDay]",      statusPref.getString("RealUptimeDay",      "Unknown"));
		formatString = formatString.replace( "%[RealUptimeHour]",     statusPref.getString("RealUptimeHour",     "Unknown"));
		formatString = formatString.replace( "%[RealUptimeMin]",      statusPref.getString("RealUptimeMin",      "Unknown"));
		formatString = formatString.replace( "%[RealUptimeSec]",      statusPref.getString("RealUptimeSec",      "Unknown"));
		formatString = formatString.replace( "%[BatteryStatus]",      statusPref.getString("BatteryStatus",      "Unknown"));
		formatString = formatString.replace( "%[BatteryHealth]",      statusPref.getString("BatteryHealth",      "Unknown"));
		formatString = formatString.replace( "%[BatteryPlugged]",     statusPref.getString("BatteryPlugged",     "Unknown"));
		formatString = formatString.replace( "%[BatteryPresent]",     statusPref.getString("BatteryPresent",     "Unknown"));
		formatString = formatString.replace( "%[BatteryLevel]",       statusPref.getString("BatteryLevel",       "Unknown"));
		formatString = formatString.replace( "%[BatteryScale]",       statusPref.getString("BatteryScale",       "Unknown"));
		formatString = formatString.replace( "%[BatteryVoltage]",     statusPref.getString("BatteryVoltage",     "Unknown"));
		formatString = formatString.replace( "%[BatteryTemperature]", statusPref.getString("BatteryTemperature", "Unknown"));
		formatString = formatString.replace( "%[BatteryTechnology]",  statusPref.getString("BatteryTechnology",  "Unknown"));
		formatString = formatString.replace( "%[CdmaAsuLevel]",       statusPref.getString("CdmaAsuLevel",       "Unknown"));
		formatString = formatString.replace( "%[CdmaDbm]",            statusPref.getString("CdmaDbm",            "Unknown"));
		formatString = formatString.replace( "%[CdmaEcio]",           statusPref.getString("CdmaEcio",           "Unknown"));
		formatString = formatString.replace( "%[CdmaLevel]",          statusPref.getString("CdmaLevel",          "Unknown"));
		formatString = formatString.replace( "%[EvdoAsuLevel]",       statusPref.getString("EvdoAsuLevel",       "Unknown"));
		formatString = formatString.replace( "%[EvdoDbm]",            statusPref.getString("EvdoDbm",            "Unknown"));
		formatString = formatString.replace( "%[EvdoEcio]",           statusPref.getString("EvdoEcio",           "Unknown"));
		formatString = formatString.replace( "%[EvdoLevel]",          statusPref.getString("EvdoLevel",          "Unknown"));
		formatString = formatString.replace( "%[EvdoSnr]",            statusPref.getString("EvdoSnr",            "Unknown"));
		formatString = formatString.replace( "%[GsmAsuLevel]",        statusPref.getString("GsmAsuLevel",        "Unknown"));
		formatString = formatString.replace( "%[GsmBitErrorRate]",    statusPref.getString("GsmBitErrorRate",    "Unknown"));
		formatString = formatString.replace( "%[GsmDbm]",             statusPref.getString("GsmDbm",             "Unknown"));
		formatString = formatString.replace( "%[GsmLevel]",           statusPref.getString("GsmLevel",           "Unknown"));
		formatString = formatString.replace( "%[GsmSignalStrength]",  statusPref.getString("GsmSignalStrenght",  "Unknown"));
		formatString = formatString.replace( "%[LteAsuLevel]",        statusPref.getString("LteAsuLevel",        "Unknown"));
		formatString = formatString.replace( "%[LteCqi]",             statusPref.getString("LteCqi",             "Unknown"));
		formatString = formatString.replace( "%[LteDbm]",             statusPref.getString("LteDbm",             "Unknown"));
		formatString = formatString.replace( "%[LteLevel]",           statusPref.getString("LteLevel",           "Unknown"));
		formatString = formatString.replace( "%[LteRsrp]",            statusPref.getString("LteRsrp",            "Unknown"));
		formatString = formatString.replace( "%[LteRsrq]",            statusPref.getString("LteRsrq",            "Unknown"));
		formatString = formatString.replace( "%[LteRssnr]",           statusPref.getString("LteRssnr",           "Unknown"));
		formatString = formatString.replace( "%[LteSignalStrength]",  statusPref.getString("LteSignalStrenght",  "Unknown"));
		formatString = formatString.replace( "%[MobileRxBytes]",      statusPref.getString("MobileRxBytes",      "Unknown"));
		formatString = formatString.replace( "%[MobileTxBytes]",      statusPref.getString("MobileTxBytes",      "Unknown"));
		formatString = formatString.replace( "%[MobileRxPackets]",    statusPref.getString("MobileRxPackets",    "Unknown"));
		formatString = formatString.replace( "%[MobileTxPackets]",    statusPref.getString("MobileTxPackets",    "Unknown"));
		formatString = formatString.replace( "%[TotalRxBytes]",       statusPref.getString("TotalRxBytes",       "Unknown"));
		formatString = formatString.replace( "%[TotalTxBytes]",       statusPref.getString("TotalTxBytes",       "Unknown"));
		formatString = formatString.replace( "%[TotalRxPackets]",     statusPref.getString("TotalRxPackets",     "Unknown"));
		formatString = formatString.replace( "%[TotalTxPackets]",     statusPref.getString("TotalTxPackets",     "Unknown"));

		return formatString;
	}
}
