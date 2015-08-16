/* ===========================================================================
Created:	2015/08/07 - https://github.com/yes4me/
Author:		Thomas Nguyen - thomas_ejob@hotmail.com
Purpose:	My library

	List => ALLOW DUPLICATE
		Arraylists			immutable			(GOOD: read/search is faster, operation at the end of array)
							PS: maintain an index internally
		LinkedList			mutable				(GOOD: only sequencial reading, operation faster)
	Set(interface) => NO DUPLICATE
		HashSet:			data in NO order	(search is faster)
		TreeSet:			data in sorted		(operation is faster)
		LinkedHashSet:		preserve insertion order
	Map(interface) => key/value
		HashMap:			data in NO order	& unsynchronized = better performance	(search is fasteST)
		HashTable:			data in NO order	& synchronized = thread safer			(search is fast)
		TreeMap:			data in sorted (by sorted keys)								(operation is faster)
=========================================================================== */

package lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyCollection {
	//========================================================
	// CONVERSION
	//========================================================

	public static String convertString(Integer[] numbers)
	{
		return Arrays.toString(numbers);
	}
	public static String convertString(String[] numbers)
	{
		return Arrays.toString(numbers);
	}
	public static void displayString(Map<Integer, Integer> numbers)
	{
		for (Map.Entry<Integer, Integer> data : numbers.entrySet())
		{
			System.out.println("key:"+ data.getKey() +"___value:"+ data.getValue());
		}
	}


	public static int[] convertInt(List<Integer> numbers)
	{
		int size = numbers.size();
		int[] result = new int[size];
		for (int i=0; i<size; i++) {
			result[i] = numbers.get(i);
		}
		//return numbers.toArray( new int[numbers.size()] ); <== NOT ALLOWED FOR PRIMARY
		return result;
	}
	public static String[] convertString(String words, String token)
	{
		return words.split(token);
	}


	public static Integer[] convertInteger(List<Integer> numbers)
	{
		return numbers.toArray( new Integer[numbers.size()] );
	}
	public static Integer[] convertInteger(Set<Integer> numbers)
	{
		return numbers.toArray( new Integer[numbers.size()] );
	}


	public static List<Integer> convertList(Integer[] numbers)
	{
		return Arrays.asList(numbers);
	}
	public static List<String> convertList(String[] numbers)
	{
		return Arrays.asList(numbers);
	}


	public static Set<Integer> convertSetInteger(Integer[] numbers)
	{
		Set<Integer> result = new HashSet<Integer>( convertList(numbers) );
		return result;
	}
	public static Set<Integer> convertSetInteger(List<Integer> numbers)
	{
		Set<Integer> result = new HashSet<Integer>( numbers );
		return result;
	}

	public static Set<String> convertSetString(String[] string)
	{
		Set<String> result = new HashSet<String>( Arrays.asList(string) );
		return result;
	}


	public static String[] convertString(Set<String> string)
	{
		return string.toArray(new String[string.size()]);
	}

	//========================================================
	// INTERVIEW METHODS
	//========================================================

	//Get the sort integer values.
	public static Integer[] sortArray(Integer[] numbers) 
	{
		Integer[] sortedNumbers = new Integer[numbers.length];
		System.arraycopy( numbers, 0, sortedNumbers, 0, numbers.length);	//COPY ARRAYLIST

		Arrays.sort(sortedNumbers); 
		return sortedNumbers;
	}
	//Check for duplicates
	public static boolean checkDuplicate(Integer[] numbers) 
	{
		Set<Integer> duplicate = convertSetInteger(numbers);
		return (duplicate.size() != numbers.length)? true : false;
	}
	//Get the unique numbers
	public static Integer[] getUniqueInteger(Integer[] numbers)
	{
		Set<Integer> uniqueValues = new LinkedHashSet<Integer>();
		for (int value : numbers)
		{
			//if ( !uniqueValues.contains(value) )
			uniqueValues.add(value);
		}
		return convertInteger( uniqueValues );
	}
	public static String[] getUniqueString(String[] numbers)
	{
		Set<String> uniqueValues = new LinkedHashSet<String>();
		for (String value : numbers)
		{
			//if ( !uniqueValues.contains(value) )
			uniqueValues.add(value);
		}
		return convertString(uniqueValues);
	}

	//Get frequency of numbers
	public static Map<Integer, Integer> getFrequency1(Integer[] numbers)
	{
		Map<Integer, Integer> duplicate = new HashMap<Integer, Integer>();
		for (int key : numbers)
		{
			Integer value = duplicate.get(key); 
			duplicate.put(key, (value==null)? 1:value+1 );
		}
		return duplicate;
	}
	//Get frequency of numbers (using Collections.frequency)
	public static Map<Integer, Integer> getFrequency2(Integer[] numbers)
	{
		List<Integer> listNumbers		= convertList(numbers); 
		Map<Integer, Integer> duplicate	= new HashMap<Integer, Integer>();
		for (Integer key : listNumbers) {
			if ( !duplicate.containsKey(key) )
			{
				Integer value = Collections.frequency(listNumbers, key);
				duplicate.put(key, value);
			}
		}
		return duplicate;
	}

	//series of numbers: 0, 1, 1, 2, 3, 5, 8, 13... The next number is found by adding up the two numbers before it
	public static boolean checkFibonacci(Integer[] numbers)
	{
		if (numbers.length<3)
			return false;
		for (int i=0; i<numbers.length-2; i++)
		{
			if (numbers[i+1]+numbers[i] != numbers[i+2])
				return false;
		}
		return true;
	}

	//Palindrome = word, phrase, number, or other sequence of characters which reads the same backward or forward
	public static boolean checkPalindrome(Integer[] numbers)
	{
		int length = numbers.length;
		for (int i=0; i<length; i++)
		{
			if (numbers[i]-numbers[length-i-1] != 0)
				return false;
		}
		return true;
	}

	//========================================================
	// OTHERS
	//========================================================
	//Get only odd numbers.
	public static Integer[] getOddArray(Integer[] numbers) 
	{
		//List is an ordered sequence of elements
		List<Integer> result = new ArrayList<Integer>();
		for (int value : numbers)
		{
			if (value % 2==1)
				result.add(value);
		}
		return convertInteger(result);
	}
	//Move all even numbers to the beginning of the array
	public static Integer[] getEvenOddArray(Integer[] numbers)
	{
		List<Integer> odd	= new ArrayList<Integer>();
		List<Integer> even	= new ArrayList<Integer>();
		for (int value : numbers)
		{
			if (value % 2==1)
				odd.add(value);
			else
				even.add(value);
		}
		odd.addAll(even);			//JOIN ARRAYLIST
		return convertInteger( odd );
	}

	//========================================================
	public static void main(String[] args) {
		Integer[] numbers = {0,3,4,3,-2,5,2};
		System.out.println("ALL ARRAYS: "+ convertString(numbers) );

		/*
		1) Given an array of integers, sort the integer values.
		2) Given an array of integers, print only odd numbers.
		3) Given an array of integers move all even numbers to the beginning of the array.
		4) Print the unique numbers and also print the number of occurrences of duplicate numbers
		5) Given an array of integers check the Fibonacci series.
		 */
		System.out.println("sort: "+	convertString( sortArray(numbers) ));
		System.out.println("odd: "+		convertString( getOddArray(numbers) ));
		System.out.println("odd & even: "+  convertString( getEvenOddArray(numbers) ));
		System.out.println("check duplicate: "+  checkDuplicate(numbers) );
		System.out.println("Get unique: "+  convertString( getUniqueInteger(numbers) ));

		System.out.println("Frequency method1:");
		displayString( getFrequency1(numbers) );
		System.out.println("Frequency method2:");
		displayString( getFrequency2(numbers) );

		System.out.println("check Fibonacci: "+ checkFibonacci(numbers) );
		System.out.println("check Palindrome: "+ checkPalindrome(numbers) );
	}
}