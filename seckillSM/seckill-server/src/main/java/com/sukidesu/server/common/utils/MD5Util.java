package com.sukidesu.server.common.utils;

import com.sukidesu.server.common.Constants.Constants;
import org.springframework.util.DigestUtils;

public class MD5Util{

	public static String getMD5(long des){
		String base = des + "/" + Constants.MD5_SALT;
		String md5String = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5String;
	}

	public static void main(String[] args) {
		System.out.println(getMD5(1));
	}
	
}
