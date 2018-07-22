package com.wy.store.common.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy:mm:dd  HH:mm:ss");
	
	public static String getCurrentDateString() {
		
        long now=System.currentTimeMillis();
        
        return standardDateFormat.format(now);

	}
	
	
	public static String getCustomFormatDateString(Date date) {

		
        return standardDateFormat.format(date);

	}
	
	
}
