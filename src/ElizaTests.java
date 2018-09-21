import java.util.Arrays;

//Test file for Eliza.java, makes sure that the methods in Eliza.java
//are working properly.

public class ElizaTests {
	
	public static void testChooseString() {
		
		//is one of the 3 strings chosen?
		String [] strList = {"The", "happy", "cat"};
		String choice = Eliza.chooseString( strList);
		if ( choice != null && (choice.equals("The") || choice.equals("happy") 
				|| choice.equals("cat"))) {
			System.out.println("testChooseString 1 passed.");
		} else {
			System.out.println("testChooseString 1 failed.");
		}
		
		//if I call it 100 times, are the choices approximately random?
		int countThe = 0;
		int countHappy = 0;
		int countCat = 0;
		for ( int i = 1; i <= 100; i++) {
			choice = Eliza.chooseString( strList);
			if ( choice != null) {
				if ( choice.equals("The")) {
					countThe++;
				} else if ( choice.equals("happy")) {
					countHappy++;
				} else if ( choice.equals("cat")) {
					countCat++;
				}
			}
		}
		if ( countThe >=20 && countThe <= 45 
				&& countHappy >= 20 && countHappy <= 45
				&& countCat >= 20 && countCat <= 45) {
			System.out.println("testChooseString 2 passed. " + countThe
					+ "," + countHappy + "," + countCat);
		} else {
			System.out.println("testChooseString 2 failed. " + countThe
					+ "," + countHappy + "," + countCat);
		}
		
	}
	
	public static void testInList() {
		String [] quitWords = {"bye","goodbye","quit", "bye"};		
		int index = Eliza.inList( "bye", quitWords);
		if ( index >= 0) {
			System.out.println("testInList 1 passed.");
		} else {
			System.out.println("testInList 1 failed.");
		}
		
		index = Eliza.inList( "seeya", quitWords);
		if ( index == -1) {
			System.out.println("testInList 2 passed.");
		} else {
			System.out.println("testInList 2 failed.");
		}
		
	}
	
	public static void testAssemblePhrase() {
		String [] words = {"This", "is a", "sentence"};
		String sentence = Eliza.assemblePhrase( words);

		String expectedSentence = "This is a sentence";
		
		if ( sentence.equals( expectedSentence)) {
			System.out.println("testAssembleSentence 1 passed.");
		} else {
			System.out.println("testAssembleSentence 1 failed '" + sentence 
					+ "'");
		}
	}
	
	public static void testFindLongest() {
		String [] sentences = {"short", "This is longer.", 
				"This is the longest one.", "s"};
		String longest = Eliza.findLongest( sentences);
		if ( longest == sentences[2]) {
			System.out.println("testFindLongest 1 passed.");
		} else {
			System.out.println("testFindLongest 1 failed.");
		}
	}
	
	public static void testHowMany() {
		int countSpaces = Eliza.howMany( " ", " you me ");
		if ( countSpaces == 3) {
			System.out.println( "testHowMany 1 passed.");
		} else {
			System.out.println( "testHowMany 1 failed.  countSpaces == " 
					+ countSpaces);
		}
		
		int countNum = Eliza.howMany("<2>", "What makes you think I am <2>?");
		if ( countNum == 1) {
			System.out.println( "testHowMany 2 passed.");
		} else {
			System.out.println( "testHowMany 2 failed.  countNum == " 
					+ countNum);
		}
	}		

	public static void testFilterChars() {
		String userInput = "w? #t   i't e   4t m-s!";
		char [] expectedChars = {'w','!',' ',' ','t',' ',' ',' ','i','\'','t',
				' ','e',' ',' ',' ','4','t',' ','m',' ','s','!'};
		char [] characters = Eliza.filterChars( userInput);
		if ( userInput.length() == characters.length) {
			System.out.println("testFilterChars 1 passed.");
		} else {
			System.out.println("testFilterChars 1 failed.");
		}
		boolean error = false;
		for ( int i = 0; i < expectedChars.length; i++) {
			if ( expectedChars[i] != characters[i]) {
				System.out.print( String.format("characters[%d] '%c' expected "
						+ "'%c'\n", i, characters[i], expectedChars[i]));
				error = true;
			}
		}
		if ( error) {
			System.out.println("testFilterChars 2 failed.");
		} else {
			System.out.println("testFilterChars 2 passed.");
		}
	}
	
	public static void testRemoveDuplicateSpaces() {
		char [] chars1 = {'a', 't', ' ', '.', ' ', ' ', 'g', ' ', ' ', 'h', ' '};
		Eliza.removeDuplicateSpaces( chars1);
		char [] expectedResult = {'a', 't', ' ', '.', ' ', 'g', ' ', 'h', ' ',
				'\u0000', '\u0000'};
		
		boolean error = false;
		String errorStr = "";
		for ( int i = 0; i < expectedResult.length; i++) {
			if ( chars1[i] != expectedResult[i]) {
				errorStr += String.format("chars1[%d] '%c' expected '%c'\n", i,
						chars1[i], expectedResult[i]);
				error = true;
			}
		}
		if ( error) {
			System.out.println("testRemoveDuplicateSpaces 1 failed. "
		+ errorStr);
		} else {
			System.out.println("testRemoveDuplicateSpaces 1 passed.");
		}
	}
	
	public static void testFindAnyWords() {
		String[] someWords = {"Going", "now", "goodbye"};
		boolean found = Eliza.findAnyWords( someWords, Config.QUIT_WORDS);
		if ( found) {
			System.out.println("testFindAnyWords 1 passed.");
		} else {
			System.out.println("testFindAnyWords 1 failed.");
		}
		
		String[] someMoreWords = {"Hello", "how", "are", "you"};
		found = Eliza.findAnyWords( someMoreWords, Config.QUIT_WORDS);
		if ( !found) {
			System.out.println("testFindAnyWords 2 passed.");
		} else {
			System.out.println("testFindAnyWords 2 failed.");
		}
	}

	public static void testInitialProcessing() {
		String sentence = Eliza.initialProcessing("What? This isn't the "
					+ "    4th messy-sentence!");
		if ( sentence != null 
				&& sentence.equals( "this isn't the 4th messy sentence")){
			System.out.println("testInitialProcessing 1 passed.");
		} else {
			System.out.println("testInitialProcessing 1 failed:" + sentence);
		}
	}
	
	public static void testReplacePairs() {
		String [] someWords = {"i'm", "cant", "recollect"};
		String [] beforeList = {"dont", "cant", "wont", "recollect", "i'm"};
		String [] afterList = {"don't", "can't", "won't", "remember", "i am"};
		String [] result = Eliza.replacePairs( someWords, beforeList, afterList);
		if ( result != null && result[0].equals("i") && result[1].equals("am") 
				&& result[2].equals("can't") && result[3].equals("remember")) {
			System.out.println("testReplacePairs 1 passed.");
		} else {
			System.out.println("testReplacePairs 1 failed.");
		}
	}

	public static void testFindPatternInSentence() {
		String [] pattern1 = { "computer"};
		String [] sentence1 = {"are", "you", "a", "computer"};
		
		String [] matches = Eliza.findPatternInSentence( pattern1, sentence1);
		if ( matches != null && matches.length == 2 
				&& matches[0].equals( "are you a") && matches[1].equals("")) {
			System.out.println("testFindPatternInSentence 1 passed.");
		} else {
			System.out.println("testFindPatternInSentence 1 failed.");
			System.out.println( Arrays.toString(matches));
		}
		
		String [] pattern2 = { "computer"};
		String [] sentence2 = {"computer", "are", "you"};
		
		matches = Eliza.findPatternInSentence( pattern2, sentence2);
		if ( matches != null && matches.length == 2 && matches[0].equals("") 
				&& matches[1].equals( "are you")) {
			System.out.println("testFindPatternInSentence 2 passed.");
		} else {
			System.out.println("testFindPatternInSentence 2 failed.");
			System.out.println( Arrays.toString(matches));
		}
		
		String [] pattern5 = { "computer"};
		String [] sentence5 = {"does", "that", "computer", "on", "your", 
					"desk", "work"};
		matches = Eliza.findPatternInSentence( pattern5, sentence5);
		if ( matches != null && matches.length == 2 
				&& matches[0].equals( "does that") 
				&& matches[1].equals( "on your desk work")) {
			System.out.println("testFindPatternInSentence 3 passed.");
		} else {
			System.out.println("testFindPatternInSentence 3 failed.");
			System.out.println( Arrays.toString(matches));
		}

		String [] pattern6 = {"you", "me"};
		String [] sentence6 = {"why", "don't", "you", "like",  "me"};
		matches = Eliza.findPatternInSentence( pattern6, sentence6);
		if ( matches != null && matches.length == 3 
				&& matches[0].equals( "why don't") 
				&& matches[1].equals( "like") && matches[2].equals("")) {
			System.out.println("testFindPatternInSentence 4 passed.");
		} else {
			System.out.println("testFindPatternInSentence 4 failed.");
			System.out.println( Arrays.toString(matches));
		}
		
		String [] pattern7 = {"you", "me"};
		String [] sentence7 = {"me", "don't", "like", "you"};
		matches = Eliza.findPatternInSentence( pattern7, sentence7);
		if ( matches == null) {
			System.out.println("testFindPatternInSentence 5 passed.");
		} else {
			System.out.println("testFindPatternInSentence 5 failed.");
			System.out.println( Arrays.toString(matches));
		}
	}
	
	public static void testPrepareUserPhrase()  {
		String someWords = "i'm happy";
		String result = Eliza.prepareUserPhrase( someWords);
		if ( result != null && result.equals("you are happy")) {
			System.out.println("testPrepareUserPhrase 1 passed.");
		} else {
			System.out.println("testPrepareUserPhrase 1 failed. '" + result 
					+ "'");
		}
	}
	
	public static void testPrepareResponse() {
		String draftResponse = "Really, <3>?";
		String []userPhrases = {"", "", "about my dog"};
		String response = Eliza.prepareResponse( draftResponse, userPhrases);
		
		String expectedResponse = "Really, about your dog?";
		
		if ( response.equals( expectedResponse)) {
			System.out.println("testPrepareResponse 1 passed.");
		} else {
			System.out.println("testPrepareResponse 1 failed. '" + response 
					+ "'");
		}
	}
	
	public static void testMatchResponse() {
		String []words1 = {"are", "you", "a", "computer"};
		String response1 = Eliza.matchResponse( words1);
		if ( Eliza.inList( response1, Config.RESPONSE_TABLE[0][1]) >= 0) {
			System.out.println("testMatchResponse 1 passed.");
		} else {
			System.out.println("testMatchResponse 1 failed.");
		}
		
		String []words2 = {"you", "are", "like", "my", "father"};
		String response2 = Eliza.matchResponse( words2);
		if ( response2 != null && response2.contains( "like your father")) {
			System.out.println("testMatchResponse 2 passed.");
		} else {
			System.out.println("testMatchResponse 2 failed.");
		}
	}
	
	private static void testProblem(String problem) {
		//supporting method for testProcessInput
		System.out.println("Patient:  " + problem);
		System.out.println("Eliza: " + Eliza.processInput( problem));
	}
	
	public static void testProcessInput() {
		//note: the responses may vary as they are randomly selected and the 
		//random generator results will vary based on the previous times it 
		//has been called. Therefore, see if each response is appropriate.

		//updated 2/17
		testProblem("I like computers.");
		testProblem("What is your name?");
		testProblem("You remind me of a grumpy uncle.");
		testProblem("You don't seem to say much.");
		testProblem("You reflect me.");
		testProblem("I wish to believe you.");
		testProblem("I dreamed of going into space.");
		testProblem("I apologize for boring you.");
		testProblem("Because it seems the polite thing to do.");
		testProblem("Yes.");
		testProblem("I remember being in 1st grade.");
		testProblem("No, not often. Sometimes I remember being on a boat.");
		testProblem("My family liked to play cards.");
		testProblem("Do you remember growing up?");
		testProblem("Are you a Packers fan?");
		testProblem("I am sad to hear that.");
		testProblem("I cannot explain this.");
		testProblem("You seem to have a different perspective than many.");
		testProblem("I'm talking to my dog.");
		testProblem("goodbye");
	}
	
	public static void main(String[] args) {
		//feel free to comment out tests that are not of current interest
		//in order to focus on certain tests.  Eventually, all the tests
		//should run successfully.

		testChooseString();
		
		testInList();
		testAssemblePhrase();
		testFindLongest();
		testHowMany();
//
		testFilterChars();
		testRemoveDuplicateSpaces();
		testFindAnyWords();
		testInitialProcessing();
//
		testReplacePairs();
		testFindPatternInSentence();
		testPrepareUserPhrase();
		testPrepareResponse();
//
		testMatchResponse();
		testProcessInput();

	}
}
