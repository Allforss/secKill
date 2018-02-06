package com.secKill.util;

import org.springframework.util.DigestUtils;

public class MD5Util implements IConst{

	public static String getMD5(long des){
		String base = des + "/" + SLAT;
		String md5String = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5String;
	}
	
}
