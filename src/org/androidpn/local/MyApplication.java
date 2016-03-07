package org.androidpn.local;

import org.androidpn.client.ServiceManager;
import org.androidpn.demoapp.R;
import org.androidpn.util.SimpleHandler;

import android.app.Application;

public class MyApplication extends Application {
	public static SimpleHandler handler;

	@Override
	public void onCreate() {
		super.onCreate();
		ServiceManager serviceManager = new ServiceManager(this);
		serviceManager.setNotificationIcon(R.drawable.notification);
		serviceManager.startService();
		System.out.println("????????????????????????????");
		handler = new SimpleHandler(this);
	}
}
