package utils;

/*
 * Ismail Kuru - Max Matthes - Lewis Cannalongo
 * 
 * File : DatayTypeConverter.java
 * Aim : This file implements transformatinos in between 
 * String( Hex representation) and Byte types  
 *
 * */

import java.util.*;

	public class DataTypeConverter {
	
	public static List<byte[]> toByteList(byte[][] bytes) {
		List<byte[]> l = new ArrayList<byte[]>();
		for (int i = 0; i < bytes.length; i++) {
			l.add(bytes[i]);
		}
		return l;
	}
	/**
	 * concats the given character to the given name until reached the
	 * given string length.
	 */
	public static String bufferLeft(char c, int i, String name) {
		while (name.length() < i) {
			name = c + name;
		}
		return name;
	}
	
	/**
	 * list of bytes constructed from the given array of bytes.
	 */
	public static List<Byte> toByteList(byte[] bytes) {
		List<Byte> l = new ArrayList<Byte>();
		for (int i = 0; i < bytes.length; i++) {
			l.add(bytes[i]);
		}
		return l;
	}
	/**
	 * array of bytes constructed from the given list of bytes.
	 */
	public static byte[] toByteArray(List<Byte> bytel){
		byte[] res = new byte[bytel.size()];
		for (int i = 0; i < bytel.size(); i++)
			res[i] = bytel.get(i);
		return res;
	}

	/**
	 * given stream of bytes with the ordinal and parameters
	 * concatenated to it.
	 */
	public static byte[] cat(byte[] bytes, byte ordinal, byte[] params) {
		byte b[] = new byte[bytes.length + 1 + params.length];
		// name
		int i = 0;
		for (; i < bytes.length; i++) {
			b[i] = bytes[i];
		}
		// state
		b[i] = ordinal;
		i++;
		// parameters
		int j = 0;
		for (; i < b.length; i++) {
			b[i] = params[j];
			j++;
		}
		return b;
	}
	
}
