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

	/**
	 * @return concats the given character to the given name until reached the
	 * given string length.
	 */
	public static String bufferLeft(char c, int i, String name) {
		while (name.length() < i) {
			name = c + name;
		}
		return name;
	}

	/**
	 * @param a byte sequence 
	 * @return a hexadecimal representation of byte stream.
	 */
	public static String toHexString(byte[] seq) {
		String shex = "";
		
		for (byte b: seq) {
			shex += String.format("%02x ", b);
		}
		shex = shex.substring(0, shex.length() - 1);
		return shex;
	}
	
	/**
	 * @param hexStr hexadecimal space-delimited representation of a byte stream.
	 * @return the actual byte stream.
	 */
	public static byte[] toByteStream(String hexStr) {
		String[] sbyte = hexStr.split(" ");
		byte[] res = new byte[sbyte.length];
		for (int i = 0; i < sbyte.length; i++)
			res[i] = (byte) Integer.parseInt(sbyte[i], 16);
		return res;
	}
	
	/**
	 * @return a list of bytes constructed from the given array of bytes.
	 */
	public static List<Byte> toByteList(byte[] bytes) {
		List<Byte> l = new ArrayList<Byte>();
		for (int i = 0; i < bytes.length; i++) {
			l.add(bytes[i]);
		}
		return l;
	}

	/**
	 * @return the given stream of bytes with the ordinal and parameters
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
