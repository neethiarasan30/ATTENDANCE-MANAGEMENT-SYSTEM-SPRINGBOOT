//package AMS.ams.TEMP;
//import java.security.SecureRandom;
//import java.util.Base64;
//
//public class GenerateJwtKey {
//		
//    public static void main(String[] args) {
//    	
//
//    	  // Generate a random 256-bit key (32 bytes)
//        byte[] key = new byte[32];
//        SecureRandom random = new SecureRandom();
//        random.nextBytes(key);
//
//        // Encode the key using Base64
//        String base64Key = Base64.getEncoder().encodeToString(key);
//
//        System.out.println("Base64-encoded secret key: " + base64Key);
//
//    }
//}
