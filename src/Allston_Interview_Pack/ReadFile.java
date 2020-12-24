package Allston_Interview_Pack;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
public class ReadFile 
//Read File functionality from http://www.homeandlearn.co.uk/java/read_a_textfile_in_java.html
{
	private String path;
	public ReadFile(String file_path)
	{
		path = file_path;
	}
	
	public String[] OpenFile() throws IOException
	{
		//Opens the file and reads line by line into an array of strings
		//containing each word.
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		FileReader nextfr = new FileReader(path);
		BufferedReader nextTextReader = new BufferedReader(nextfr);
		
		int numberOfLines = readLines();
		String[] textData1 = new String[numberOfLines];
		
		int i;
		int remLineCount = 0;
		String hash = "#";
		
		for (i=0; i < numberOfLines; i++)
		{
			//first have to go through each line and count how many lines need to be
			//removed (blank space or start with #
			String currentLine = textReader.readLine();
			if (currentLine.length() == 0 || hash.equals(currentLine.substring(0, 1)))
			{
				remLineCount ++;
			}
			textData1[i] = currentLine;
		}

		String[] textData2 = new String[numberOfLines-remLineCount];
		for (i=0; i < numberOfLines-remLineCount; i++)
		{
			//Goes through the lines a second time in order to only add
			//lines that we want to the array textData
			String currentLine = nextTextReader.readLine();
			if (currentLine.length()>0 && !hash.equals(currentLine.substring(0, 1)))
			{
				textData2[i]=currentLine.toUpperCase();
			}
			else
			{
				i=i-1;
			}
		}
		//closes the bufferedReaders we used and returns the final array
		//of strings from each line. 
		nextTextReader.close();
		textReader.close();
		return textData2;
	}
	
	int readLines() throws IOException
	{
		//counts the number of lines in the text file so that we know how
		//large to make our array when we read the lines in.
		FileReader file_to_read = new FileReader(path);
		BufferedReader bf = new BufferedReader (file_to_read);
		
		int numberOfLines = 0;
		while(bf.readLine()!= null)
		{
			numberOfLines++;
		}
		bf.close();
		
		return numberOfLines;
	}
}