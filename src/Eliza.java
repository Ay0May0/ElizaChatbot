///////////////////////////////////////////////////////////////////////////////
// Title:            (ELIZA)
// Files:            (Eliza.java, Config.java, ElizaTests.java)
// Semester:         (CS 302) Spring 2016
//
// Author:           (Manish Dhungana)
// Email:            (mddhungana@wsic.edu)
// CS Login:         (dhungana)
// Lecturer's Name:  (Gary Dahl)
// Lab Section:      (344)
// Online sources:   http://stackoverflow.com/

import java.util.Arrays;
import java.util.Scanner;

/**
 * ELIZA is a program which emulates a therapist.
 * It features the dialog between a human user and a computer program.
 * ELIZA has almost no intelligence whatsoever, but uses string substitution 
 * and canned responses based on entered keywords.
 *
 * @author (Manish Dhungana)
 */

public class Eliza {
	
	/**
	 * This method randomly picks one of the strings from the list. If list 
	 * is null or has 0 elements then null is returned.
	 * 
	 * @param list An array of strings to choose from.
	 * @return One of the strings from the list.
	 */		
	public static String chooseString( String [] list) {
		if ( list == null || list.length == 0) {
			return null;
		}
		int index = Config.RNG.nextInt( list.length);
		return list[ index];
	}
	
	/**
	 * If the word is found in the wordList then the index of the word
	 * is returned, otherwise -1 is returned. If there are multiple matches
	 * the index of the first matching word is returned. If either word or 
	 * wordList is null then -1 is returned. (Update 2/17) A zero length
	 * string should not be treated differently then non-zero length strings.
	 * A null value within wordList should be ignored.  
	 * 
	 * @param word  A word to search for in the list.
	 * @param wordList  The list of Strings in which to search.
	 * @return The index of list where word is found, or -1 if not found.
	 */
	public static int inList( String word, String []wordList) {
		for ( int i = 0; i < wordList.length;i++) {
			if ( word.equals(wordList[i])) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Combines the Strings in list together with a space between each
	 * and returns the resulting string. If list is null then null is
	 * returned. (Update 2/17) If the list has 0 elements then return a
	 * string with length 0 (""). If any elements within the list are null
	 * or zero length strings then ignore them since we don't want more than
	 * one space in a row.
	 * 
	 * @param list An array of words to be combined.
	 * @return A string containing all the words with a space between each.
	 */
	public static String assemblePhrase( String[] list) {
		if ( list == null) {
	        return null;
		} else {
		    String st = list[0];
		    //Checks the entire list
	        for( int i =1; i<list.length; i++) {
		        if( list[i] != null)
		        	//if element is found to not be null, adds spaces
		            st = st + " " + list[i] ;
		    }
		    return st;
		}
	}
	
	/**
	 * Returns the longest sentence, based on the String length, from 
	 * the array of sentences. Removes spaces from the beginning and
	 * end of each sentence before comparing lengths. If sentences is null
	 * or has a length of 0 then null is returned. (Update 2/16) In the 
	 * case of equal length longest strings, return the string that has
	 * the lowest array index.
	 * 
	 * Note: String trim method may be useful.
	 * 
	 * @param sentences The array of sentences passed in.
	 * @return The longest sentence without spaces at the beginning or end.
	 */
	public static String findLongest( String [] sentences) {
		if ( sentences != null && sentences.length != 0) {
			int index = 0;
			//Keeps track of the length of each sentence
			int elementLength = sentences[0].length();
			for( int i=1; i < sentences.length; i++) {
				//Trims blank space so lengths are not influenced
				sentences[i] = sentences[i].trim();
				if( sentences[i].length() > elementLength) {
					index = i;
					elementLength = sentences[i].length();
				}
			}
		return sentences[ index];
		} else {
	        return null;
		}
	}
	
	/**
	 * Counts the number of times the substring is in the str. Does not 
	 * count overlapping substrings separately. If either parameter 
	 * substring or str is null then -1 is returned.  (Update 2/17) 
	 * If the substring is the empty string ("") then return the length 
	 * of the string.
	 * 
	 * Note: String methods indexOf may be useful for finding whether
	 *       substring occurs within str. One of the indexOf methods
	 *       has a parameter that says where to start looking in the str.
	 *       This might be useful to find the substring again in str, 
	 *       once you have found a substring once.
	 * 
	 * @param str The string to be searched.
	 * @param substring The string to be searched for within str.
	 * @return The number of times substring is found within str or -1 if
	 *         either parameter is null parameter.
	 */
	public static int howMany( String substring, String str) {
		if ( substring != null && str != null) {
			int lastIndex = 0;
	    	int itemCount = 0;
	    	while ( lastIndex != -1) {
	    		//Finds the index of the substring in str and stores it in
	    		//lastIndex
	    		lastIndex = str.indexOf( substring, lastIndex);
	    		if ( lastIndex != -1) {
	    			itemCount++;
	    			lastIndex += substring.length();
	    		}
	    	}
	    	return itemCount;
		} else {
			return -1;
		}
	}

	/**
	 * This method performs the following processing to the userInput.
	 * - substitute spaces for all characters but (alphabetic characters, 
	 *   numbers, and ' , ! ? .).
	 * - Change (,!?.) to (!). Parenthesis not included.
	 * (Update 2/17) If the userInput is null then return null otherwise 
	 * the array returned should be the same length as the userInput.
	 * 
	 * @param userInput The characters that the user typed in.
	 * @return The character array containing the valid characters.
	 */
	public static char [] filterChars( String userInput) {
		if ( userInput != null) {
			for( int i=1; i < userInput.length(); i++) {
				//Replaces all but the characters identified, first to spaces
				//and then to "!".
				userInput = userInput.replaceAll( "[^A-Za-z0-9,'!?.]", " ");
				userInput = userInput.replaceAll( "[,!?.]", "!");
			}
		} else {
			return null;
		}
		return userInput.toCharArray();
	}
	
	/**
	 * Reduces all sequences of 2 or more spaces to 1 space within the 
	 * characters array. If any spaces are removed then the same number
	 * of Null character '\u0000' will fill the elements at the end of the
	 * array.
	 * 
	 * @param characters The series of characters that may have more than one
	 *     space in a row when calling. On return the array of characters will
	 *     not have more than one space in a row and there may be '\u0000'
	 *     characters at the end of the array.
	 */
	public static void removeDuplicateSpaces( char [] characters) {
		 boolean copyThis = false;
		 boolean lastSpace = false;
		 int j = 0;
		    for ( int i = 0; i < characters.length; i++) {
		    	//Checks if there is a sequence of >= 2 spaces
		        if ( Character.isWhitespace(characters[i])) {
		            copyThis = !lastSpace;
		            lastSpace = true;
		        } else {
		            copyThis = true;
		            lastSpace = false;
		        }
		        if ( copyThis) {
		            if ( i != j) {
		                characters[j] = characters[i];
		            }
		            j++;
		        }
		    }
		    for ( int i = j; i < characters.length; i++) {
		    	//Adds the amount of spaces removed as null character at the
		    	//end of the array
		        characters[i] = '\u0000';
		    }
		}
	
	/**
	 * Looks for each word in words within the wordList. 
	 * If any of the words are found then true is returned, otherwise false.
	 * 
	 * @param words List of words to look for.
	 * @param wordList List of words to look through.
	 * @return Whether one of the words was found in wordList.
	 */
	public static boolean findAnyWords(String[] words, String []wordList ) {
		boolean findWord = false;
		for( int i = 0; i < wordList.length; i++) {
			if( i >= words.length)
				return false;
			//Uses inList to find if word occurs in wordList
			int listCheck = inList( words[i], wordList);
	        if( listCheck != -1) {
	            findWord = true;
	            return findWord;
	        }
		}
		return false;
	}
	
	/**
	 * This method performs the following processing to the userInput and 
	 * returns the longest resulting sentence.
	 * 1) Converts all characters to lower case  
	 * 		See String toLowerCase() method.
	 * 2) Removes any extra spaces at the beginning or end of the input
	 * 		See String trim() method.
	 * 3) Substitute spaces for all characters but alphabetic characters, 
	 *    numbers, and ',.!? and then substitute ! for ,.?
	 *      See filterChars method.
	 * 4) Reduce all sequences of 2 or more spaces to be one space.
	 *      See removeDuplicateSpaces method.
	 * 5) Divide input into separate sentences based on !
	 *      Construct a String from a character array with 
	 *      	String str = new String( arrayName);
	 *      See String split method.
	 *      Example: String[] sentences = str.split("!");
	 * 6) Determine which sentence is longest in terms of characters and
	 *    return it. 
	 *      See findLongest method.
	 *     
	 * (Update 2/17) If the userInput is null then null should be returned.
	 * 
	 * @param userInput The characters that the user typed in.
	 * @return The longest sentence from the input.
	 */
	public static String initialProcessing( String userInput) {
		userInput = userInput.toLowerCase();
		userInput = userInput.trim();
		char [] UserArray = filterChars( userInput);
		removeDuplicateSpaces( UserArray);
		String str = new String( UserArray);
		//Splits the sentences array string at the points where "!" is found
		String [] sentences = str.split( "!");
		str = findLongest( sentences);
		return str;
	}
	
	/**
	 * This method creates a new words list replacing any words it finds in
	 * the beforeList with words in the afterList. An array of the resulting
	 * words is returned.  
	 * 
	 * (Update 2/17) If any parameter is null, return null.
	 * 
	 * @param words List of words to look through.
	 * @param beforeList List of words to look for.
	 * @param afterList List of words to replace, if the corresponding word in 
	 * the before list is found in the words array.
	 * @return The new list of words with replacements.
	 */
	public static String[] replacePairs(String []words, String [] beforeList, 
			String [] afterList) {
		for ( int i = 0; i < words.length; i++) {
	        int listCheck = inList(words[i], beforeList);
	        //If word exists in the word list, it replaces word with after list
	        if ( listCheck != -1) {
	            words[i] = afterList[listCheck];
	        }
	    }
		//Removes [] with "" and replaces extra commas with ""
		return Arrays.toString(words).replaceAll("\\[|\\]|,", "").split(" ");
	}
	
	/**
	 * Checks to see if the pattern matches the sentence. In other words, 
	 * checks to see that all the words in the pattern are in the sentence 
	 * in the same order as the pattern. If the pattern matches then this
	 * method returns the phrases before, between and after the 
	 * pattern words. If the pattern does not match then null is returned.
	 * (Update 2/17) If any parameter is null, return null.
	 * 
	 * @param pattern The words to match, in order, in the sentence.
	 * @param sentence Each word in the sentence.
	 * @return The phrases before, between and after the pattern words
	 * 		or null if the pattern does not match the sentence.
	 */	
	public static String [] findPatternInSentence( String [] pattern, 
			String [] sentence) {
		String[] userPhrase = new String[pattern.length+1];
		Arrays.fill(userPhrase, "");
		int userPhraseIndex = 0, patternIndex = 0;
		for(String word : sentence) {
			//If pattern index is greater than pattern length,there will be no
			//pattern to check, so increments userPhraseIndex to create 
			//new phrase.
			if ( patternIndex < pattern.length && 
				word.equals(pattern[patternIndex])) {
				userPhraseIndex++;
				patternIndex++;
			} else {
				//Creates userPhrase
				userPhrase[userPhraseIndex] += " " + word;
			}
			//Removes extra spaces
			userPhrase[userPhraseIndex] = userPhrase[userPhraseIndex].trim();
		}
		//Uses conditional operator to return null if pattern mismatched in 
		//the sentence
		return (patternIndex != pattern.length) ? null : userPhrase;
	}

	/**
	 * Replaces words in the phrase if it is in the Config.POST_PROCESS_BEFORE
	 * with the corresponding words from Config.POST_PROCESS_AFTER.
	 * (Update 2/17) If the parameter is null then return null.
	 * 
	 * @param phrase One or more words separated by spaces.
	 * @return A string composed of the words from phrase with replacements.
	 */
	public static String prepareUserPhrase( String phrase) {
		return assemblePhrase(replacePairs(phrase.split(" "), 
				Config.POST_PROCESS_BEFORE, Config.POST_PROCESS_AFTER));
	}
	
	/**
	 * Prepares a response based on the draftResponse. If draftResponse
	 * contains <1>, <2>, <3> or <4> then the corresponding userPhrase
	 * is substituted for the <1>, <2>, etc.  The userPhrase however must
	 * be prepared by exchanging words that are in Config.POST_PROCESS_BEFORE
	 * with the corresponding words from Config.POST_PROCESS_AFTER.
	 * (Update 2/17) If draftResponse is null, then return null. If 
	 * userPhrases is null then return draftResponse.
	 * 
	 * @param draftResponse A response sentence potentially containing <1>, 
	 *             <2> etc.
	 * @param userPhrases An array of phrases from the user input.
	 * @return A string composed of the words from sentence with replacements.
	 */
	public static String prepareResponse( String draftResponse, 
			String []userPhrases) {
		if ( userPhrases == null)
			return draftResponse;
		boolean flag = false;
		String response =  null;
		//Checks draftResponse from <1> to <4>
		for ( int i = 1; i <= 4; i++)
			//If an draft response exits, it changes it with userPhrase
			if(draftResponse.contains(String.format("<%d>", i))) {
				response = draftResponse.replaceFirst(String.format("(<%d>)", i), 
						prepareUserPhrase(userPhrases[i-1]));
				//Sets flag if draft response is not empty
				flag = true;
			}
		if ( !flag)
			return draftResponse;
		return response;
	}
	
	/**
	 * Looks through Config.RESPONSE_TABLE to find the first pattern 
	 * that matches the words. When a pattern is matched then a response 
	 * is randomly chosen from the corresponding list of responses.
	 * If the response has a parameter denoted with <1>, <2> 
	 * etc. the parameter is replaced with the 0th, 1st, etc String
	 * from the user phrases returned by the findPatternInSentence method.
	 * (Update 2/17) If words parameter is null then choose a response 
	 * from NO_MATCH_RESPONSES and return.
	 * 
	 * @param words The words of a sentence.
	 * @return The completed response ready to be shown to the user.
	 */
	public static String matchResponse( String [] words) {
		String phrase[] = null, draftResponse = null;
		int i = 0;
		for ( i = 0; i < Config.RESPONSE_TABLE.length; i++)
			if ((phrase = findPatternInSentence(Config.RESPONSE_TABLE[i][0], 
					words)) != null) {
				draftResponse  = chooseString(Config.RESPONSE_TABLE[i][1]);
				//If the phrase exists it breaks the loop
				break;
			}
		//If the phrase exist it prepares response
		if ( phrase != null) {
			return prepareResponse(draftResponse, phrase);
		}
		return null;
	}

	/**
	 * Takes the input as a parameter and returns a response. If any of the
	 * QUIT_WORDS are found then null is returned. 
	 * (Update 2/17) If userInput is null then return null;
	 * 
	 * @param userInput The problem sentence(s) the user types in.
	 * @return A response string to be shown to the user or null if a QUIT_WORD
	 *         is found.
	 */
	public static String processInput(String userInput) {
		userInput = initialProcessing(userInput);
		if ( findAnyWords(userInput.split(" "), Config.QUIT_WORDS)) {
			return null;
		}
		return matchResponse(replacePairs(userInput.split(" "), 
				Config.PRE_PROCESS_BEFORE, Config.PRE_PROCESS_AFTER));
	}

	/**
	 * This method displays an INITIAL_MESSAGE, accepts typed input, calls 
	 * the processInput method, and prints out the response (of processInput)
	 * until the response is null at which point the FINAL_MESSAGE is shown
	 * and the program terminates.
	 */
	public static void interactive() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(Config.INITIAL_MESSAGE);
		while (true) {
			String line = scanner.nextLine();
			System.out.println("Patient:" + line);
			System.out.println("Eliza: " + processInput(line));
			if ( processInput(line) == null){
				System.out.println(Config.FINAL_MESSAGE);
				break;
			}
		}
	}
	
	/**
	 * Program execution starts here.
	 * @param args unused
	 */  	
	public static void main(String[] args) {

		interactive();

	}
}