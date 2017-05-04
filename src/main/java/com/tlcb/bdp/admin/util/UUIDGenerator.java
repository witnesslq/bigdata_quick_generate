package com.tlcb.bdp.admin.util;

import java.util.UUID;

public class UUIDGenerator {
	public UUIDGenerator() {
		
	}

	/**
	 * 鑾峰緱涓�釜UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}
	
	public static String getUUIDShort() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8);
	}
	
	public static String getUUIDLength(int length) {
		String s = UUID.randomUUID().toString();
		return s.substring(0, length);
	}

	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] s = new String[number];
		for (int i = 0; i < number; i++) {
			s[i] = getUUID();
		}
		return s;
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
