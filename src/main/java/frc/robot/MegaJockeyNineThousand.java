package frc.robot;

import frc.robot.Vjoy_interface.VJoy;

public class MegaJockeyNineThousand {
    public static void setupClass() {
        try {
			String architecture = System.getProperty("sun.arch.data.model");
			
			if (architecture.equals("32")) {
				System.loadLibrary("32/vJoyInterface");
				System.loadLibrary("32/libJvJoyInterfaceNative");
			} else if (architecture.equals("64")) {
				System.loadLibrary("64/vJoyInterface");
				System.loadLibrary("64/libJvJoyInterfaceNative");
			} else {
//				throw new UnexpectedException("Unexpected architecture: " + architecture);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
    }
}

