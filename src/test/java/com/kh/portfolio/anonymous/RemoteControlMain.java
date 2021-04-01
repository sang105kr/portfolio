package com.kh.portfolio.anonymous;

public class RemoteControlMain {

	public static void main(String[] args) {
		RemoteControl rc = new RemoteControlImpl();
		
		rc.turnOn();
		rc.turnOff();
		
		
		RemoteControl rc2 = new RemoteControl() {
			@Override
			public void turnOff() {
				System.out.println("켜다2");
				
			}
			@Override
			public void turnOn() {
				System.out.println("끄다2");				
			}
		};
		rc2.turnOn(); rc2.turnOff();
		
		RemoteControl rc3 = new RemoteControlImpl();
		rc3.turnOn();rc3.turnOff();	
		
		RemoteControl rc4 = new RemoteControl() {
			@Override
			public void turnOff() {
				System.out.println("켜다4");
				
			}
			@Override
			public void turnOn() {
				System.out.println("끄다4");				
			}
		};
		rc4.turnOn(); rc4.turnOff();		
	}

}
