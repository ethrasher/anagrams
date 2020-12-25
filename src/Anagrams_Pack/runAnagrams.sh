#!/bin/sh
#This script should run the Anagrams program based on the Main function in the
#file Test.java

#Use bourne shell for portability. Bourne is on almost every unix based 
#computer.


DICT_PATH="./Dicts/RaceInc.txt"
#this is the file_path to the dictionary you would like to use. I provided
#this one as an example. Make sure that whatever dictionary you use does not have
#spaces in the file name.

WORD="race"       #case in-sensitive, however must be one word. 

MULTIPLE="-multiple" #use "" if this option is not wanted
                    #Or use "-multiple" if you want to find anagrams 
                    #with multiple words in the solution

javac -cp ../ Test.java #can comment this out if you don't need to compile


java -cp ../ Anagrams_Pack/Test $MULTIPLE $WORD $DICT_PATH
