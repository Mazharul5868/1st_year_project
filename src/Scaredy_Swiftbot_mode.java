import java.util.Random;

public class Scaredy_Swiftbot_mode extends Swiftbot{

	static long startTime;
	
	Scaredy_Swiftbot_mode() throws InterruptedException{
		
		String asciiArt =                                                                                                                                                                    
	            "                                                                                                                                                                     \n" +
	            "                                                                                                                                                                     \n" +
	            "                                                                                                                                                                     \n" +
	            "███████  ██████  █████  ██████  ███████ ██████  ██    ██     ███████ ██     ██ ██ ███████ ████████ ██████   ██████  ████████     ███    ███  ██████  ██████  ███████ \n" +
	            "██      ██      ██   ██ ██   ██ ██      ██   ██  ██  ██      ██      ██     ██ ██ ██         ██    ██   ██ ██    ██    ██        ████  ████ ██    ██ ██   ██ ██      \n" +
	            "███████ ██      ███████ ██████  █████   ██   ██   ████       ███████ ██  █  ██ ██ █████      ██    ██████  ██    ██    ██        ██ ████ ██ ██    ██ ██   ██ █████   \n" +
	            "     ██ ██      ██   ██ ██   ██ ██      ██   ██    ██             ██ ██ ███ ██ ██ ██         ██    ██   ██ ██    ██    ██        ██  ██  ██ ██    ██ ██   ██ ██      \n" +
	            "███████  ██████ ██   ██ ██   ██ ███████ ██████     ██        ███████  ███ ███  ██ ██         ██    ██████   ██████     ██        ██      ██  ██████  ██████  ███████ \n" +
	            "                                                                                                                                                                     \n" +
	            "                                                                                                                                                                     \n" +
	            "                                                                                                                                                                     ";

	        System.out.println(asciiArt);
		
		System.out.println("Swiftbot start wandering with the blue underlights...");
		
		sMovement();
		
	}

	void sMovement() {
		
		double distance = calculateDistance();
		boolean object_detect = false;
		double maxObjectDistance = 100.0;
		startTime = System.currentTimeMillis();
		long currentTime;
		
		startMovement(60, 60);					//start moving
		setUnderLight(0, 255, 0);				// set underlights to blue
		
		if(distance <= maxObjectDistance) {								// object detected
			System.out.println("Object detected within 1 meter");
			
			object_detect = true;				
			if(object_detect) {													//to count object detect
				Object_Detect_main.encounterObject++;
			}
			
			objectEncounter();
			distance = calculateDistance();
			sMovement();
		} else {
			
			currentTime = System.currentTimeMillis();
			
			while(distance > maxObjectDistance && currentTime - startTime < 5000) {				// looking for object within 5sec..
				startMovement(60, 60);					
				setUnderLight(0, 255, 0);
				
				distance = calculateDistance();
				currentTime = System.currentTimeMillis();
			}
			if(distance > maxObjectDistance) {
				System.out.println("No movement detect for last 5 sec...");
			}
			
			while(distance > maxObjectDistance && currentTime - startTime >= 5000) {			// no object found
				currentTime = System.currentTimeMillis();
				noObjectEncounter();
				distance = calculateDistance();
				break;
			}
			sMovement();
		}
		
		stopMovement();
		closeUnderlights();
		
	}
	
	// Object detect within 1m and less than 5sec - capture image, blinking underlights and turn in opposite direction
	void objectEncounter() {
		startTime = System.currentTimeMillis();									// Reset start time
		image_Process();
		blinking();
		
		System.out.println("Setting the underlights to red");
		setUnderLight(255, 0, 0);
		
		System.out.println("Moving away from the object for 3sec..");
		turning(60, 60, 3000);
		
		closeUnderlights();
		stopMovement();

	}
	
	// No Object detect within 1m less than 5sec
	void noObjectEncounter() {
		
		System.out.println("Waiting for 1 sec...");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Start moving in different direction");
		
		Random random = new Random();
        int randomNumber = random.nextInt(2) + 1;
	
        if(randomNumber == 1) {
        	System.out.println("Moving on left");											// turning left
        	turning(0, 60, 1500);

        	closeUnderlights();
    		stopMovement();
        } 
        else if(randomNumber == 2) {
        	System.out.println("Moving on right");											// turning right
        	turning(60, 0, 1500);

        	closeUnderlights();
    		stopMovement();
        }
	}
	
	// blinking the underlights
	void blinking() {
		
		System.out.println("Blue underlights are blinking");
		System.out.println("Turning in opposite direction");
		
		Thread blinkThread = new Thread(() -> {
			
			long startTime = System.currentTimeMillis();
			boolean isOn = false;
			double turningTime = 2100.0;
			
			while(System.currentTimeMillis() - startTime < turningTime) {				// blink the underlights
				
				if(isOn) {
					setUnderLight(0,255,0);
					isOn = false;
				} 
				else {
					setUnderLight(0, 0, 0);
					isOn = true;
				}
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			closeUnderlights();
		});
		
		Thread turnThread = new Thread(() -> {											// turning in opposite direction
			turning(0, 60, 2100);
		});
		
		blinkThread.start();
		turnThread.start();
		
		try {
			blinkThread.join();
			turnThread.join();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
}
