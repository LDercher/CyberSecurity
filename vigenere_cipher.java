import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Exception;


public class vigenere_cipher {
	
	public static void main (String [] args){
	  String msg = "HUETNMIXVTMQWZTQMMZUNZXNSSBLNSJVSJQDLKR";
	  
	  long startTime = System.nanoTime();
	 String[] possibleKeys = crack(msg, 5, 11);
	 for (int i = 0; i < possibleKeys.length; i++)
	 {
		 System.out.println(decrypt(msg, possibleKeys[i]));
	 }
	 long endTime = System.nanoTime();
	 long duration = (endTime - startTime);
	 double seconds = (double)duration / 1000000000.0;
	 System.out.println("Program took " + seconds + " seconds to crack Vigenere ciphere" );
	 		
		
	}
	
	
	public static String encrypt(String message, final String key) {
        String result = "";
        message = message.toUpperCase();
        for (int i = 0, j = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            result += (char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }
        return result;
    }
	
	 public static String decrypt(String message, final String key)
	    {
	        String result = "";
	        message = message.toUpperCase();
	        for (int i = 0, j = 0; i < message.length(); i++)
	        {
	            char c = message.charAt(i);
	            if (c < 'A' || c > 'Z')
	                continue;
	            result += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
	            j = ++j % key.length();
	        }
	        return result;
	    }

	

	

	public static String[] getStringList(int length) {
		int letters = 26;
		int count = length;
		final int combinations = (int) Math.pow(letters, count);
		StringBuilder sb = new StringBuilder(count);
		String[] keys = new String[combinations];
		for (int i = 0; i < combinations; i++) {
		    sb.setLength(0);
		    for (int j = 0, i2 = i; j < count; j++, i2 /= letters)
		        sb.insert(0, (char) ('a' + i2 % letters));
		    	keys[i] = sb.toString().toUpperCase();
		    	
		}
		return keys;

    }
	
	public static String[] crack(String cipherText, int keyLength, int firstWordLength){
		
		String file = "dict.txt";					//initializations.

		String lastLine = "";

		boolean fileExists = true;
		
		FileReader fileRead = null;					//allows us to verify creation of an object later

		BufferedReader lineIn = null;
		
		Hashtable< Integer, String > hash = new Hashtable< Integer, String >();
		
		String[] keys = getStringList(keyLength);
		
		String cipher_text = cipherText;
		
		String crackedText = "not cracked";
		
		ArrayList<String> goodKeyList = new ArrayList<String>();
		
		int first_word_length = firstWordLength;
		
		String firstWord = "";

		while(fileExists){

			try{									//in case there is no such file, wrapped in a try/catch block

				fileRead= new FileReader(file);		//creates a filereader object linked to the file argument

				lineIn = new BufferedReader(fileRead);	//Creates a buffered reader (like a scanner) for the filereader

				fileExists = false;  				//if this line is reached, the file exists and we can move on
		
				lastLine = lineIn.readLine();		//consume the next line
				
				int i = 0 ;

				while(lastLine!=null){				//while the buffer has lines left to read (ie the string value is not null)

					hash.put(i, lastLine);

					lastLine = lineIn.readLine();	//move the next value of the buffer to the string's reference
					
					i++;

				}



				}

			

			catch(Exception e){

				System.out.println(e.getMessage());	//if something goes wrong, print the associated error message

			}
	}
		int count = keys.length-1;		
		
		while(count >= 0)
		{
			String key = keys[count];
			
			crackedText = decrypt(cipher_text, key);
					
			char[] crackedArray = crackedText.toCharArray();
			
			char[] firstArray = new char[first_word_length];
			
			for(int i = 0; i < firstArray.length; i++)
			{
				firstArray[i] = crackedArray[i];
			}	
			
			firstWord = new String(firstArray);

			if (hash.contains(firstWord))
			{
			goodKeyList.add(key);
			}
			count--;
			
			
		}
		
		Object[] keyObj = goodKeyList.toArray();

		String[] goodKeyArr = Arrays.copyOf(keyObj, keyObj.length, String[].class);
		
		return goodKeyArr;
	}

}


