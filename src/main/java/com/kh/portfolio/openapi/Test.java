package com.kh.portfolio.openapi;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test {

	static final String serviceKey = "bJ0AcEWnYARdHMe24EsPd77ralP%2BiRWLuhIeWgoIBgM%2F4dqlAgbS%2FilwgSiZkbkL9ojCBQHuEZI2TtoMqYzRhA%3D%3D";
	public static void main(String[] args) {
		try {
			System.out.println(serviceKey);
			System.out.println(URLDecoder.decode(serviceKey,"UTF-8"));
			System.out.println(URLEncoder.encode(serviceKey,"UTF-8"));
			System.out.println(URLEncoder.encode(URLDecoder.decode(serviceKey,"UTF-8"),"UTF-8"));
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
