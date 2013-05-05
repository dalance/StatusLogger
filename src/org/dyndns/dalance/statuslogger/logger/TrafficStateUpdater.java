package org.dyndns.dalance.statuslogger.logger;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.TrafficStats;

public class TrafficStateUpdater {
	private static final String TAG = TrafficStateUpdater.class.getSimpleName();
	
	public static void update(Context context) {
		SharedPreferences pref = context.getSharedPreferences("Status", 0);
		SharedPreferences.Editor editor = pref.edit();
		
		editor.putString("MobileRxBytes",   String.valueOf(TrafficStats.getMobileRxBytes()  ));
		editor.putString("MobileTxBytes",   String.valueOf(TrafficStats.getMobileTxBytes()  ));
		editor.putString("MobileRxPackets", String.valueOf(TrafficStats.getMobileRxPackets()));
		editor.putString("MobileTxPackets", String.valueOf(TrafficStats.getMobileTxPackets()));
		editor.putString("TotalRxBytes",    String.valueOf(TrafficStats.getTotalRxBytes()   ));
		editor.putString("TotalTxBytes",    String.valueOf(TrafficStats.getTotalTxBytes()   ));
		editor.putString("TotalRxPackets",  String.valueOf(TrafficStats.getTotalRxPackets() ));
		editor.putString("TotalTxPackets",  String.valueOf(TrafficStats.getTotalTxPackets() ));
		editor.commit();
	}
}
