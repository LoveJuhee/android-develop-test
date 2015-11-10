package com.kit.developtest.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by kit on 15. 7. 12..
 */
public class BroadcastUtil {

	public static boolean sendBroadcast(Context context, Intent intent) {
		if (context == null || intent == null) {
			return false;
		}
		context.sendBroadcast(intent);
		return true;
	}

	/**
	 * Send local broadcast.
	 *
	 * @param context the context
	 * @param intent the intent
	 * @return the boolean
	 */
	public static synchronized boolean sendLocalBroadcast(Context context, Intent intent) {
		if (context == null || intent == null) {
			return false;
		}
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
		return true;
	}

	/**
	 * Register local broadcast.
	 *
	 * @param context the context
	 * @param receiver the receiver
	 * @param intentFilter the intent filter
	 * @return the boolean
	 */
	public static synchronized boolean registerLocalBroadcast(Context context,
		BroadcastReceiver receiver, IntentFilter intentFilter) {
		if (context == null || receiver == null || intentFilter == null) {
			return false;
		}
		LocalBroadcastManager.getInstance(context).registerReceiver(receiver, intentFilter);
		return true;
	}

	/**
	 * Unregister local broadcast.
	 *
	 * @param context the context
	 * @param receiver the receiver
	 * @return the boolean
	 */
	public static synchronized boolean unregisterLocalBroadcast(Context context,
		BroadcastReceiver receiver) {
		if (context == null || receiver == null) {
			return false;
		}
		LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
		return true;
	}

	/**
	 * Register broadcast.
	 *
	 * @param context the context
	 * @param receiver the receiver
	 * @param intentFilter the intent filter
	 * @return the boolean
	 */
	public static synchronized boolean registerBroadcast(Context context, BroadcastReceiver receiver,
		IntentFilter intentFilter) {
		if (context == null || receiver == null || intentFilter == null) {
			return false;
		}
		context.registerReceiver(receiver, intentFilter);
		return true;
	}

	/**
	 * Unregister broadcast.
	 *
	 * @param context the context
	 * @param receiver the receiver
	 * @return the boolean
	 */
	public static synchronized boolean unregisterBroadcast(Context context, BroadcastReceiver receiver) {
		if (context == null || receiver == null) {
			return false;
		}
		context.unregisterReceiver(receiver);
		return true;
	}
}
