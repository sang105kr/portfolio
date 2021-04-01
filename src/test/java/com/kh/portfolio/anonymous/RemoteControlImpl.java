package com.kh.portfolio.anonymous;

public class RemoteControlImpl implements RemoteControl{

	@Override
	public void turnOn() {
		System.out.println("켜다");
		
	}

	@Override
	public void turnOff() {
		System.out.println("끄다");
	}

}
