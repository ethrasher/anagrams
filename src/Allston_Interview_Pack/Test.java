package Allston_Interview_Pack;
import java.io.IOException;
import java.util.*;

import Allston_Interview_Pack.ReadFile;
import Allston_Interview_Pack.WordSet;
//Run this through command line with:
//Testing Dict file path: "/Users/thrasher_elizabeth/Documents/Java/Allston_Interview/src/Allston_Interview_Pack/Sample_Dictionary_File.txt"
//	$cd /Users/thrasher_elizabeth/Documents/java/Allston_Interview/src/Allston_Interview_Pack
//	$javac -cp ../ Test.java
//	$java -cp ../Allston_Interview_Pack/Test

//Read File functionality from http://www.homeandlearn.co.uk/java/read_a_textfile_in_java.html
public class Test 
{
	public static int lengthNoSpace (String s)
	{
		//finds the length of a string without including spaces
		//AKA "ABC DE" = 5
		//Subtracts the total number of spaces from the total length
		int origLength = s.length();
		int numOfSpaces = 0;
		for (int i=0; i<origLength; i++)
		{
			String sub = s.substring(i,  i+1);
			if (sub.equals(" "))
			{
				numOfSpaces++;
			}
		}
		return origLength-numOfSpaces;
	}
	
	public static String[] getWordList(String dictFilePath)
	{
		//gets an array of Strings representing each line in the file.
		//this function uses the ReadFile class to read out each line 
		//and save it in an array
		try 
		{
			ReadFile file = new ReadFile(dictFilePath);
			String[] arrayLines = file.OpenFile();
			return arrayLines;
			
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	public static WordSet[] getWordSet(String origString, String[] arrayLines)
	{
		//from the array of lines make a list of WordSets that
		//correspond to these lines. 
		WordSet[] wordArray = new WordSet[arrayLines.length];
		int newIndex = 0;
		WordSet origWord = new WordSet(origString);
		//To speed up efficiency and run-time later on, only include words that are
		//potential anagrams (less than or equal to length and isPartialAnagram is true)
		for (int i=0; i<arrayLines.length; i++)
		{
			if (origString.length()>=lengthNoSpace(arrayLines[i]))
			{
				WordSet currentWord = new WordSet(arrayLines[i]);
				if (origWord.isPartialAnagram(currentWord))
				{
					//if it is a partialAnagram, add it to the list
					wordArray[newIndex]=currentWord;
					newIndex++;
				}
			}
			
		}
		//remove blanks at the end of the list that were unfilled
		wordArray = minWordList(wordArray);
		return wordArray;
	}
	
	public static String[] singleAnagram(WordSet origWord, WordSet[] wordArray)
	{
		//in case multiple is not added in, look for only single words that are perfect anagrams
		String[] results = new String[wordArray.length];
		int currentIndex = 0;
		for (int index=0; index<wordArray.length; index++)
		{
			WordSet currentWord = wordArray[index];
			//check if they are anagrams of each other
			boolean isAna = origWord.isAnagram(currentWord);
			if (isAna)
			{
				results[currentIndex]=currentWord.getWord();
				currentIndex++;
			}
		}
		//remove blanks at the end of the array
		results = minList(results);
		return results;
	}
	
	public static WordSet catTwoWordSet (WordSet word1, WordSet word2)
	{
		//add two strings together (with a space) and make a new WordSet.
		//used to determine anagrams from multiple words
		String s1 = word1.getWord();
		String s2 = word2.getWord();
		String full = s1 + " " + s2;
		if (s1.equals(""))
		{
			full = s2;
		}
		WordSet result = new WordSet(full);
		return result;
	}
	
	public static String[] multiWordAnargams(WordSet origWord, WordSet[] wordArray)
	{
		//Using a breadth-first search technique (bfs) find all the anagrams that 
		//use multiple words to make them up. Start with the empty string as the root
		//and continue to add strings on that are partial anagrams and append them to 
		//the end of the queue. Then check if they are anagrams, or add words on if they
		//are not. Since we are looking for all cases, the bfs must run to completion (queue
		//empty. Also, since their could potentially be 2^n possible results, I made the
		//final results into a LinkedList so there would not be so much wasted space.
		LinkedList<String> results = new LinkedList<String>();
		
		int currentResultIndex = 0;
		WordSet empty = new WordSet("");
		Queue <WordSet> queue = new LinkedList<WordSet>();
		queue.add(empty);
		while(!queue.isEmpty())
		{
			WordSet currentWord = queue.remove();
			if (origWord.isAnagram(currentWord))
			{
				results.add(currentWord.word);
				currentResultIndex++;
				continue;
			}
			for (int i=0; i<wordArray.length; i++)
			{
				WordSet cat = catTwoWordSet(currentWord, wordArray[i]);
				if (origWord.isPartialAnagram(cat))
				{
					queue.add(cat);
				}
			}
			
		}
		String[] result = results.toArray(new String[currentResultIndex]);
		return result;
	}
	
	public static String[] minList(String[] wordList)
	{
		//remove the blanks from the end of a String list. 
		//makes the result more streamlined and so we do not return null
		int index = 0;
		while (wordList[index] != null)
		{
			index++;
		}
		String[] results = new String[index];
		for (int i=0; i<index; i++)
		{
			results[i]=wordList[i];
		}
		return results;
	}
	
	public static WordSet[] minWordList(WordSet[] wordList)
	{
		//similar to above but for an array of WordSet's.
		//makes the result more streamlined and so we do not return null
		int index = 0;
		while (wordList[index] != null)
		{
			index++;
		}
		WordSet[] results = new WordSet[index];
		for (int i=0; i<index; i++)
		{
			results[i]=wordList[i];
		}
		return results;
	}
	
	public static void main(String[] args) throws IOException
	{
		//Takes in optional argument -multiple origString DictionaryFilePath
		//Determining whether to include multiple word anagrams
		boolean multiple;
		
		if (args.length > 3 || args.length<2)
		{
			System.out.print("Invalid command line arguments.\n");
			System.out.print("Make sure that the dictionary file name has no spaces.\n");
			return;
		}
		else if (args.length == 3 && args[0].equals("-multiple"))
		{
			multiple = true;
		}
		else if (args.length == 2)
		{
			multiple = false;
		}
		else
		{
			System.out.print("Invalid command line arguments.");
			return;
		}
		//Determining the other two arguments
		int mult = multiple ? 1 : 0;
		String origString = args[0+mult];
		origString = origString.toUpperCase();
		String dictFilePath = args[1+mult];
		
		
		
		//Reading out the dictionary file based on the path
		String[] arrayLines = getWordList(dictFilePath);
		if (arrayLines == null || arrayLines.length<1)
		{
			System.out.println("Poorly created Dictionary");
			return;
		}
		
		
		//Finding all anagrams (either single or multiple words) that are legal
		WordSet[] wordArray = getWordSet(origString, arrayLines);
		WordSet origWord = new WordSet(origString);
		
		if (!multiple)
		{
				//Find all single word anagrams
			String[] result = singleAnagram(origWord, wordArray);
			System.out.println(Arrays.toString(result));
		}
		else{
			//Find the multi-word anagrams (will also find single anagrams)
			String[] result2 = multiWordAnargams(origWord, wordArray);
			System.out.println(Arrays.toString(result2));
		}
		//Since this is in a main Java function, I cannot return anything (void) and therefore
		//must only print out the results.
		return;
	}

}
