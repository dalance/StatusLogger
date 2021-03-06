package org.dyndns.dalance.statuslogger;

import org.dyndns.dalance.statuslogger.logger.LoggerService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartReceiver extends BroadcastReceiver {
	@SuppressWarnings("unused")
	private static final String TAG = StartReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent(context, LoggerService.class);
		serviceIntent.setAction("auto_start");
		context.startService(serviceIntent);
	}
}
