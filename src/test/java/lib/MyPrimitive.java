/* ===========================================================================
Created:	2015/08/07 - https://github.com/yes4me/
Author:		Thomas Nguyen - thomas_ejob@hotmail.com
Purpose:	My library
=========================================================================== */

package lib;

import java.util.LinkedHashSet;
import java.util.Set;

public class MyPrimitive {
	//========================================================
	// CONVERSION
	//========================================================

	public static String convertString(char letter)
	{
		//return letter + "";
		return String.valueOf(letter);
	}
	public static String convertString(Integer number)
	{
		//return Integer.toString(number);	//Also possible but confusing to read
		return String.valueOf(number); 
	}
	public static String convertString(Set<String> string)
	{
		String sentence = "";
		for (String value : string)
			sentence = sentence.concat(value);
		return sentence; 
	}
	public static String convertString(StringBuffer stringBuffer)
	{
		return stringBuffer.toString();
	}

	//========================================================
	// INTERVIEW METHODS
	//========================================================

	public static boolean checkPalindrome(Integer number) {
		String value	= convertString(number);
		int length		= value.length();
		for (int i=0; i<length; i++)
		{
			if (value.charAt(i) != value.charAt(length-i-1))
				return false;
		}
		return true;
	}
	public static String getUniqueChar(String sentence) {
		Set<String> uniqueValues = new LinkedHashSet<String>();
		for (int i=0; i<sentence.length(); i++)
		{
			String letter = convertString( sentence.charAt(i) );
			if ( !uniqueValues.contains( letter ) )
				uniqueValues.add(letter);
		}
		return convertString(uniqueValues);
	}
	public static String[] getUniqueWord(String sentence) {
		sentence = sentence.replaceAll("[\\s]+", " ");
		String[] words = sentence.split("[\\s]");
		return MyCollection.getUniqueString(words);
	}
	public static String reverseChar(String sentence) {
		return new StringBuilder(sentence).reverse().toString();
	}
	public static String reverseWord(String sentence) {
		String[] string		= MyCollection.convertString(sentence, " ");

		String reverse = "";
		for (int i=string.length-1; i>=0; i--)
		{
			reverse = reverse.concat( string[i] ).concat(" ");
		}
		reverse = reverse.replaceAll("(\\s)*$", "");
		return reverse.toString();
	}

	//========================================================
	// OTHERS
	//========================================================

	public static double power(int number, int power)
	{
		if (number==0)
			return 0;
		if (power==0)
			return 1;

		int result	= number;
		int times	= Math.abs(power);
		for (int i=1; i<times; i++)
			result *= number;
		return (power>0)? result:1.0/result;
	}

	//========================================================
	public static void main(String[] args) {
		/*
		6) Given an integer check if it is a Palindrome
		7) Given a string print the unique words of the string.
		8) Given a string print the reverse of the string.
		9) Given a string print the string in same flow, but reversing each word of it.
		10) Read a file content and write it to a new file in reverse order.( reverse line 1-10 to line 10-1)
		11) Write a Java program to calculate a power b.
		 */
		System.out.println("Palindrome?"+ checkPalindrome(123454321) );
		System.out.println("Unique character:"+ getUniqueChar("qwe asd   zxc qwe rty") );
		System.out.println("Unique words:"+ MyCollection.convertString( getUniqueWord("qwe asd   zxc qwe rty") ));
		System.out.println("reverse:"+ reverseChar("abcde") );
		System.out.println("reverse:"+ reverseWord("qwe asd   zxc qwe rty") );
		System.out.println("power 2^-3:"+ power(2,-3) );
	}
}
