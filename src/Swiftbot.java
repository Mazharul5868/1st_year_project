import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import swiftbot.ImageSize;

public class Swiftbot {

	// calculate distance
	static double calculateDistance() {
		return Object_Detect_main.GetAPI().useUltrasound();					// calculate distance
	}
	
	// start movement 
	 static void startMovement(int x, int y) {
		try {
			Object_Detect_main.GetAPI().startMove(x, y);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// turn left or right
	static void turning(int x, int y, int z) {
		try {
			Object_Detect_main.GetAPI().move(x, y, z);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Stop moving
	static void stopMovement() {
		
		try {
			Object_Detect_main.GetAPI().stopMove();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// underlight - to set the underlight
	static void setUnderLight(int x, int y, int z) {
		
		int[] colourToLightUp = {x,y,z};
		
		try {
			Object_Detect_main.GetAPI().fillUnderlights(colourToLightUp);
		} 
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	
	// Turn off underlights
	static void closeUnderlights() {
		
		try {
			Object_Detect_main.GetAPI().disableUnderlights();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Image Processing
	static void image_Process()  {
		
		try {
	        BufferedImage img = Object_Detect_main.GetAPI().takeStill(ImageSize.SQUARE_1080x1080);
	        
	        if(img == null) {
	        	 System.out.println("Unable to capture the image.");
	        } 
	        else {
	        	ImageIO.write(img, "jpg", new File("/home/pi/MyWork/TestImage.jpg"));
	        	System.out.println("Captured the image of object and saved");
	        	Object_Detect_main.imageCount ++;
	        } 
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
