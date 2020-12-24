# Anagrams
## Build and Run Instructions: In a Mac or Linux terminal

I made a shell script so it would be easier to run. If you navigate down the 
folder hierarchy: "src->Allston_Interview_Pack" then there should be a file
called "runAnagrams.sh". You can either run the program as is with the 
preset options or open this shell script and alter the DICT_PATH, WORD, and
MULTIPLE variables to what you want to run the program on. When you are in this
directory, give permission to execute by typing:

$chmod +x runAnagrams.sh

Once the script has permission and the arguments are set, run the program
simply by typing:

$./runAnagrams.sh

## Design, Algorithms, Implementation

Note: Throughout this problem, I assumed that an anagram was case in-sensitive and 
therefore changed everything to uppercase. I also assumed that the words would
only contain ASCII characters A-z with no spaces, special characters, or 
characters from other languages. Furthermore, when the option multiple is 
turned on, I assumed order mattered for the words (AKA "DOG CAT" is different
than "CAT DOG"). 

Note: When running the program with the -multiple option, longer words do take
more time to run. For instance, "incredible" takes ~15s on my particular
computer when run with the Sample Dictionary.

In order to solve this problem the program must do two things:
1. make sure the length of the original and final word is the same
2. make sure the letter frequencies are the same
The simplest way to keep track of the frequencies of letters in a word is
through a HashMap (dictionary) since each letter can be a key and map to 
it count within the word. Then to compare counts, all the program must do is
look at each key in the HashMap. However, in order to not lose the original word
I decided the most practical implementation was to create a "WordSet" object 
that would contain both the word itself and the frequency HashMap. Then each
object could be treated as its own word and compared against another word. 

First I had to read out the argments from the command line and use the path 
to create a string array of the lines of text. By checking each line to see 
if it was empty or started with a "#", I could then remove these and forget 
about them entirely for the rest of the program. Once all these lines/words
were read out, I made each one into a WordSet using the constructor so I 
could easily compare it against the original string's WordSet. In order
to make it more efficient, before I appended a WordSet to the list of
WordSets, I checked for whether it was a potentially valid anagram or partial
anagram by running some of my WordSet methods. This made the list to check
though significatly shorter by removing words that were not even possible.

For the first part of the problem, it was fairly simple when my WordSet 
object had an isAnagram method (comparing length and frequencies of 
two WordSets). All I had to do for loop through each WordSet from the dictionary
and run isAnagram on it as compared to the original String. If it returned 
true, I added it to my results list. Then, after the looping, I just printed
the results list at the end.

For the extra credit portion, it was trickier. I decided to use a breadth-first
search algorithm and run to completion (so I find all possible multi-word 
anagrams). I created a queue where I added a root node of a WordSet of the
empty string. Then, while the queue was not empty, I removed one element at a 
time, checked if itself was an anagram, and if not, added another word to it. 
This "adding of words" literally concatenated them (with a space in between
that would not be counted as a character or length) and create a new WordSet
based on this new word. If this new word was a partialAnagram (less than 
or equal to length, less than or equal to each frequency for each letter) then 
I would add it back onto the end of the queue and keep going. Eventually, though
searching for every potential combination, I would find all multiple word
anagrams. However, due to the shear size of possible outcomes, I could not make
an array due to its massive size. Therefore, I created a LinkedList and
added each Anagram onto the end. At the end of the algorithm, I turned this 
result into an array and returned it, to print it out in Main. 

