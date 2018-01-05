package com.wxbooking.shuding.common;

import android.content.Context;

import com.wxbooking.shuding.constants.MyConstants;
import com.wxbooking.shuding.utils.FileUtil;

public class AppConfig {

	private static AppConfig mAppConfig;

	public static AppConfig getAppConfig(Context context) {
		if (mAppConfig == null) {
			mAppConfig = new AppConfig();
		}
		return mAppConfig;
	}
	// 默认存放文件下载的路径
	public final static String DEFAULT_SAVE_FILE_PATH = FileUtil.getSDPath() + "/" + MyConstants.DOWNLOAD  ;

	
}
