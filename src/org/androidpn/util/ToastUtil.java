package org.androidpn.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	public static void show(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
