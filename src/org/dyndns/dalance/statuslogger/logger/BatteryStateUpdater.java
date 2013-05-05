package org.dyndns.dalance.statuslogger.logger;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;

public class BatteryStateUpdater {
	private static final String TAG = BatteryStateUpdater.class.getSimpleName();
	
	public static void update(Context context) {
		Intent intent = context.getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
			int status        = intent.getIntExtra("status", 0);
			int health        = intent.getIntExtra("health", 0);
			boolean present   = intent.getBooleanExtra("present", false);
			int level         = intent.getIntExtra("level", 0);
			int scale         = intent.getIntExtra("scale", 0);
			int plugged       = intent.getIntExtra("plugged", 0);
			int voltage       = intent.getIntExtra("voltage", 0);
			int temperature   = intent.getIntExtra("temperature", 0);
			String technology = intent.getStringExtra("technology");
			
			SharedPreferences pref = context.getSharedPreferences("Status", 0);
			SharedPreferences.Editor editor = pref.edit();
			
            switch (status) {
        		case BatteryManager.BATTERY_STATUS_CHARGING:     editor.putString("BatteryStatus", "Charging"    ); break;
            	case BatteryManager.BATTERY_STATUS_DISCHARGING:  editor.putString("BatteryStatus", "Discharging" ); break;
            	case BatteryManager.BATTERY_STATUS_FULL:         editor.putString("BatteryStatus", "Full"        ); break;
            	case BatteryManager.BATTERY_STATUS_NOT_CHARGING: editor.putString("BatteryStatus", "Not Charging"); break;
            	case BatteryManager.BATTERY_STATUS_UNKNOWN:      editor.putString("BatteryStatus", "Unknown"     ); break;
            }
            switch (health) {
            	case BatteryManager.BATTERY_HEALTH_COLD:                editor.putString("BatteryHealth", "Cold"               ); break;
        		case BatteryManager.BATTERY_HEALTH_DEAD:                editor.putString("BatteryHealth", "Dead"               ); break;
        		case BatteryManager.BATTERY_HEALTH_GOOD:                editor.putString("BatteryHealth", "Good"               ); break;
        		case BatteryManager.BATTERY_HEALTH_OVERHEAT:            editor.putString("BatteryHealth", "Over Heat"          ); break;
        		case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:        editor.putString("BatteryHealth", "Over Voltage"       ); break;
            	case BatteryManager.BATTERY_HEALTH_UNKNOWN:             editor.putString("BatteryHealth", "Unknown"            ); break;
        		case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE: editor.putString("BatteryHealth", "Unspecified Failure"); break;
            }
            switch (plugged) {
        		case BatteryManager.BATTERY_PLUGGED_AC:       editor.putString("BatteryPlugged", "AC"      ); break;
        		case BatteryManager.BATTERY_PLUGGED_USB:      editor.putString("BatteryPlugged", "USB"     ); break;
        		case BatteryManager.BATTERY_PLUGGED_WIRELESS: editor.putString("BatteryPlugged", "Wireless"); break;
            }
            if(present) {
            	editor.putString("BatteryPresent", "Present"    );     	
            } else {
            	editor.putString("BatteryPresent", "Not Present");	
            }
                        
            editor.putString("BatteryLevel",       String.valueOf(level)      );
            editor.putString("BatteryScale",       String.valueOf(scale)      );
            editor.putString("BatteryVoltage",     String.valueOf(voltage)    );
            editor.putString("BatteryTemperature", String.valueOf(temperature));
            editor.putString("BatteryTechnology",  technology                 );
            
            editor.commit();
		}
	}	
}
