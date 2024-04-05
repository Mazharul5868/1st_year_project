import java.util.Random;

public class Curious_Swiftbot_mode extends Swiftbot{

	Curious_Swiftbot_mode() throws InterruptedException {
		
		String asciiArt =                                                                
	            "                                                                                                                                                                  \n" +
	            "                                                                                                                                                                  \n" +
	            "                                                                                                                                                                  \n" +
	            " ██████ ██    ██ ██████  ██  ██████  ██    ██ ███████     ███████ ██     ██ ██ ███████ ████████ ██████   ██████  ████████     ███    ███  ██████  ██████  ███████ \n" +
	            "██      ██    ██ ██   ██ ██ ██    ██ ██    ██ ██          ██      ██     ██ ██ ██         ██    ██   ██ ██    ██    ██        ████  ████ ██    ██ ██   ██ ██      \n" +
	            "██      ██    ██ ██████  ██ ██    ██ ██    ██ ███████     ███████ ██  █  ██ ██ █████      ██    ██████  ██    ██    ██        ██ ████ ██ ██    ██ ██   ██ █████   \n" +
	            "██      ██    ██ ██   ██ ██ ██    ██ ██    ██      ██          ██ ██ ███ ██ ██ ██         ██    ██   ██ ██    ██    ██        ██  ██  ██ ██    ██ ██   ██ ██      \n" +
	            " ██████  ██████  ██   ██ ██  ██████   ██████  ███████     ███████  ███ ███  ██ ██         ██    ██████   ██████     ██        ██      ██  ██████  ██████  ███████ \n" +
	            "                                                                                                                                                                  \n" +
	            "                                                                                                                                                                  \n" +
	            "                                                                                                                                                                  ";

	        System.out.println(asciiArt);
		
		System.out.println("Swiftbot start wandering with the blue underlights...");
		cMovement();
	
	}

	// Movement of the SwiftBot
	static void cMovement() throws InterruptedException {
		
		double distance = calculateDistance();
		double requiredDistance = 15.0;
		double maxObjectDistance = 100.0;
		boolean printed1 = false;
		boolean object_detect = false;
		boolean countedObject = false;
		
		if(distance > requiredDistance) {																// starting wandering with blue underlights
			
			while(distance > maxObjectDistance) {
				startMovement(60, 60);
				setUnderLight(0, 255, 0);
				distance = calculateDistance();
			}
			
			while(distance > requiredDistance && distance < maxObjectDistance) {						// forward movement	and green underlights
				
				if(!object_detect && !countedObject) {													//to count object detect
					Object_Detect_main.encounterObject++;
					countedObject = true;
				}
				
					if(!printed1) {
						System.out.println("Object detected! Following 15cm distance from the object...");
						System.out.println("Updating underlights to green...");
						System.out.println("Moving forward...");
						printed1 = true;
					}
					startMovement(60, 60);
					setUnderLight(0, 0, 255);

				distance = calculateDistance();
			}

			System.out.println("Stopped at 15cm from the object");
			System.out.println("Underlights are turned off");
			stopMovement();
			closeUnderlights();
			image_Process();
			
			newMovement();
		}
		
		else if(distance < requiredDistance && distance > 0) {											// backward movement and green underlights
			System.out.println("Moving backward...");
							
			if(!object_detect && !countedObject) {													//to count object detect
				Object_Detect_main.encounterObject++;
				countedObject = true;
			}
			
			while(distance < requiredDistance && distance > 0) {
				startMovement(-60, -60);
				setUnderLight(0, 0, 255);
				distance = calculateDistance();
			}
			
			System.out.println("Stopped at 15cm from the object");
			System.out.println("Underlights are turned off");
			stopMovement();
			closeUnderlights();
			image_Process();

			newMovement();
		}
		
		else if(distance == requiredDistance) {															// remain stationary and blinking green underlights
			System.out.println("Swiftbot exactly at 15cm (Buffer zone)");
			System.out.println("Green underlights are blinking");

			if(!object_detect && !countedObject) {													//to count object detect
				Object_Detect_main.encounterObject++;
				countedObject = true;
			}
			
			while(distance == requiredDistance) {
				setUnderLight(0, 0, 255);
				setUnderLight(0, 0, 0);
				distance = calculateDistance();
			}
			
			System.out.println("Stopped at 15cm from the object");
			System.out.println("Underlights are turned off");
			stopMovement();
			closeUnderlights();
			image_Process();

			newMovement();
		}
		else {
			System.out.println("Unable to move");
		}
	}
	
	// movement after image processing
	static void newMovement() throws InterruptedException {
		double beforeDistance = calculateDistance();
		Thread.sleep(2000);
		double afterDistance = calculateDistance();
		
		double difference = Math.abs(beforeDistance - afterDistance);
		
		if(difference < 1.5 && difference > 0) {										// checking object movement
			System.out.println("The object has not moved");
			System.out.println("Waiting for 5sec...");
			
			double oldDistance = calculateDistance();
			Thread.sleep(5000);
			double newDistance = calculateDistance();
			double newDifference = Math.abs(oldDistance - newDistance);
			
			if(newDifference < 1.5 && newDifference > 0) {
				System.out.println("There is no movement for last 5sec...");
				Thread.sleep(1000);
				
				Random random = new Random();
		        int randomNumber = random.nextInt(2) + 1;
			
		        if(randomNumber == 1) {
		        	System.out.println("Moving on left");											// turning left
		        	turning(0, 60, 1500);
		        	cMovement();
		        } 
		        else if(randomNumber == 2) {
		        	System.out.println("Moving on right");											// turning right
		        	turning(60, 0, 1500);
		        	cMovement();
		        }
			} 
			else {
				System.out.println("Object has moved.");
				cMovement();
			}
		} 
		else {
			System.out.println("Object has moved.");
			cMovement();
		}
	}
}

