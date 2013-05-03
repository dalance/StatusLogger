package org.dyndns.dalance.statuslogger;

import com.deploygate.sdk.DeployGate;

import android.app.Application;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
	    DeployGate.install(this);
	}
}
