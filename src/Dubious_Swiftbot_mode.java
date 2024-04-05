import java.util.Random;

public class Dubious_Swiftbot_mode {
	
	Dubious_Swiftbot_mode() throws InterruptedException {
		
		String asciiArt =                                                                                                                                                                
	            "                                                                                                                                                                  \n" +
	            "                                                                                                                                                                  \n" +
	            "                                                                                                                                                                  \n" +
	            "██████  ██    ██ ██████  ██  ██████  ██    ██ ███████     ███████ ██     ██ ██ ███████ ████████ ██████   ██████  ████████     ███    ███  ██████  ██████  ███████ \n" +
	            "██   ██ ██    ██ ██   ██ ██ ██    ██ ██    ██ ██          ██      ██     ██ ██ ██         ██    ██   ██ ██    ██    ██        ████  ████ ██    ██ ██   ██ ██      \n" +
	            "██   ██ ██    ██ ██████  ██ ██    ██ ██    ██ ███████     ███████ ██  █  ██ ██ █████      ██    ██████  ██    ██    ██        ██ ████ ██ ██    ██ ██   ██ █████   \n" +
	            "██   ██ ██    ██ ██   ██ ██ ██    ██ ██    ██      ██          ██ ██ ███ ██ ██ ██         ██    ██   ██ ██    ██    ██        ██  ██  ██ ██    ██ ██   ██ ██      \n" +
	            "██████   ██████  ██████  ██  ██████   ██████  ███████     ███████  ███ ███  ██ ██         ██    ██████   ██████     ██        ██      ██  ██████  ██████  ███████ \n" +
	            "                                                                                                                                                                  \n" +
	            "                                                                                                                                                                  \n" +
	            "                                                                                                                                                                  ";

	      System.out.println(asciiArt);
		
		Random random = new Random();
        int randomNumber = random.nextInt(2) + 1;
        
        System.out.println("Avaiable mode: ");
        System.out.println("  1. Curious Swiftbot");
        System.out.println("  2. Scaredy Swiftbot\n");
        
        switch (randomNumber) {
        
        case 1: 
        	System.out.println("Selected Mode: Curious Swiftbot Mode");
        	System.out.println("Initializing Curious Swiftbot mode...");
        	Curious_Swiftbot_mode curiousMode = new Curious_Swiftbot_mode();
			break;
			
        case 2: 
        	System.out.println("Selected Mode: Scaredy Swiftbot Mode");
        	System.out.println("Initializing Scaredy Swiftbot mode...");
			Scaredy_Swiftbot_mode scaredyMode = new Scaredy_Swiftbot_mode();
			break;
			
        default: 
			System.out.println("Unable to generate a rndom mode.");

        }
	}
}
