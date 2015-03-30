package com.isaac.sharelocation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

public class Utils 
{
    public static boolean isStringNullOrEmpty(String string)
    {
        if (string ==null) return true;
        if ( string != null && string. length () == 0 ){ return true ; }
        else
            return false;
    }
    public static Calendar getTimeAfterInSecs(int secs)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND,secs);
        return cal;
    }
    public static Calendar getCurrentTime()
    {
        Calendar cal = Calendar.getInstance();
        return cal;
    }

	public static long getThreadId()
	{
		Thread t = Thread.currentThread();
		return t.getId();
	}
	public static String getThreadSignature()
	{
		Thread t = Thread.currentThread();
		long l = t.getId();
		String name = t.getName();
		long p = t.getPriority();
		String gname = t.getThreadGroup().getName();
		return (name + ":(id)" + l + ":(priority)" + p
				+ ":(group)" + gname);
	}
	public static void logThreadSignature(String tag)
	{
		Log.d(tag, getThreadSignature());
	}
	public static void sleepForInSecs(int secs)
	{
		try
		{
			Thread.sleep(secs * 1000);
		}
		catch(InterruptedException x)
		{
			throw new RuntimeException("interrupted",x);
		}
	}


    public static String getPrefValue(Context context, String value)
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(value, "");
    }
    public static InputStream downloadUrl(String urlString) throws IOException
    {
        URL url=new URL(urlString);
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        InputStream stream=conn.getInputStream();
        return stream;
    }

}
