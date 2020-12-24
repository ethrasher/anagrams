package Allston_Interview_Pack;
import java.util.Map;
import java.util.HashMap;
import java.lang.Integer;

public class WordSet 
{
	//Will be the representation of each word/string that we are looking at
	//Instance variables
	String word;
	Map<String, Integer> freq;
	//freq is a frequency table of letters for that particular word
	
	//Constructor
	public WordSet(String c)
	{
		word = c;
		freq = new HashMap<String, Integer>();
		int AsciiA = 65;
		int AsciiZ = 90;
		//for each letter in the uppercase alphabet, add it as a key to
		//the frequency table
		for (int i=AsciiA; i<AsciiZ+1; i++)
		{
			String letter = Character.toString((char)i);
			freq.put(letter, 0);
		}
		int length = c.length();
		//for each letter in the word, increment that letter's frequency
		for (int i=0; i<length; i++)
		{
			String sub = c.substring(i, i+1);
			if (freq.get(sub) != null)
			{
				Integer val = freq.get(sub);
				freq.put(sub, val+1);
			}
		}	
	}
	
	//Public methods
	public String getWord()
	{
		return word;
	}
	public int getCount(String letter)
	{
		//return's how many of a particular letter there are
		return freq.get(letter);
	}
	
	public int lenNoSpace()
	{
		//finding the length of a word ignoring all spaces
		//AKA "ABC DE" = 5
		int origLength = word.length();
		int numOfSpaces = 0;
		for (int i=0; i<origLength; i++)
		{
			String sub = word.substring(i,  i+1);
			if (sub.equals(" "))
			{
				numOfSpaces++;
			}
		}
		return origLength-numOfSpaces;
	}
	
	public boolean isAnagram(WordSet Word2)
	{
		//checks if it is an anagram of the other word by comparing
		//lengths and then frequencies of the letters
		if (lenNoSpace()!=Word2.lenNoSpace())
		{
			return false;
		}
		for (String key : freq.keySet())
		{
			if (getCount(key)!= Word2.getCount(key))
			{
				return false;
			}
		}
		
		return true;
		
	}
	
	public boolean isPartialAnagram(WordSet Word2)
	{
		//finds if the 2nd word is a partial Anagram (has less than or 
		//equal to the length and less than or equal to each count of 
		//each letter. A potential anagram with more characters
		if (lenNoSpace()<Word2.lenNoSpace())
		{
			return false;
		}
		for (String key : freq.keySet())
		{
			if (getCount(key)< Word2.getCount(key))
			{
				return false;
			}
		}
		
		return true;	
	}
	
	
	
	
}