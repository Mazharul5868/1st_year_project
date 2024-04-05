import swiftbot.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Object_Detect_main {
	static SwiftBotAPI swiftBot;
	static String currentMode = "";
	static long startTime = 0;
	static long endTime = 0; 
	static long duration = 0;
	static int encounterObject = 0;
	static int imageCount = 0;
	
	public static void main(String[] args)	throws Exception{
		
		try {
            swiftBot = new SwiftBotAPI();
        } catch (Exception e) {
        	e.printStackTrace();
            System.exit(1);
        }
        
		Scanner scanner = new Scanner(System.in);
		String response;

		System.out.println("Press 'S' to start the program: ");
		
		for(int i=0; i<2; i++) {
			response = scanner.next();
			
			if(response.equals("S")) {
				
				startTime = System.currentTimeMillis();									// record the start time
				
				System.out.println("User instructions: ");
				System.out.println("  - Swiftbot can run by scanning a valid QR code.");
				System.out.println("  - Image can capture by Pressing 'C'");
				System.out.println("  - Program can terminate by pressing 'X' during the journey");
				System.out.println("  - Scan a valid QR code to select the mode: 'Curious Swiftbot', 'Scaredy Swiftbot', 'Dubious Swiftbot'");
				System.out.println("  - The Swiftbot need to run in the free of space");
				System.out.println("  - Ensure that the battery is fully charged to run the Swiftbot");
				System.out.println("                  ------------------------------           \n");
				
				while(true) {
					
					XPressed();
					
					System.out.println("Please select a feature: \n");
					System.out.println("1. Press 'Q' to scan the QR code.");
					System.out.println("2. Press 'C' to capture an image.");
					System.out.println("3. Press 'X' button on the SwiftBot or Keyboard to terminate the program.");
					System.out.println("Enter a key: ");
					
					response = scanner.next();
					
					switch(response) {
						
					// Scan a QR code
					case "Q":
						scanQR();
									
						break;
					
					// Image capturing
					case "C":
						Swiftbot.image_Process();
						
						break;
						
					//Terminate the program
					case "X": 
						scanner.close();
						System.exit(0);
						break;
					
					default: 
						System.out.println("Invalid key. Please press a valid key.\n");
					}				 
				}
					
			} else {
				System.out.println("Unable to start. Please try again...");
			}
		}
		
		scanner.close();
		System.exit(0);	
	}
	
	// Access SwiftBotAPI
	public static SwiftBotAPI GetAPI()
	{
		return(swiftBot);
	}
	
	// X - button press to terminate
	
	static void XPressed() {
		 try {
		    	swiftBot.disableButton(Button.X);
		        swiftBot.enableButton(Button.X, () -> {
		        	
		        	Swiftbot.stopMovement();
		        	Swiftbot.closeUnderlights();
		        	
		        	endTime = System.currentTimeMillis();										// record the last time
					duration = (endTime - startTime) / 1000;
		        	
		        	//Execution Log
		        	executionLog();
		        	
		            System.exit(1);
		        });
		    } 
		    catch(Exception e) {
		    	e.printStackTrace();
		    }	    
	}
	
	// QR code scan
	static void scanQR() {
		
		try {
			
			Thread.sleep(2000);
			
			BufferedImage image = swiftBot.getQRImage();
			String decodeMessage = swiftBot.decodeQRImage(image);
			
			if(!decodeMessage.isEmpty()){
			    
				int mode = Integer.parseInt(decodeMessage);
				
				switch(mode) {
				
				case 1: 
					System.out.println("QR code scanning...");
					
					currentMode = "Curious Swiftbot";
					
					Thread.sleep(2000);
					Curious_Swiftbot_mode curiousMode = new Curious_Swiftbot_mode();
					break;
				
				case 2: 
					System.out.println("QR code scanning...");
					
					currentMode = "Scaredy Swiftbot";
					
					Thread.sleep(2000);
					Scaredy_Swiftbot_mode scaredyMode = new Scaredy_Swiftbot_mode();
					break;
					
				case 3 :
					System.out.println("QR code scanning...");
					
					currentMode = "Dubious Swiftbot";
					
					Thread.sleep(2000);
					Dubious_Swiftbot_mode dubiousMode = new Dubious_Swiftbot_mode();
					break;
					
				default: 
					System.out.println("Please scan a valid QR code.");
				}
			 }
			else {
				System.out.println("No QR code has found.");
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	};

	static void executionLog() {
		System.out.println("Terminate the program");
		System.out.println("\nExecution Log: ");
		System.out.println("The current mode is: " + currentMode);
		System.out.println("Duration : " + duration/60 + "min " + duration%60 + "sec");
		System.out.println("Encounter objects: " + encounterObject);
		System.out.println("Images save: " + imageCount);
	}
}