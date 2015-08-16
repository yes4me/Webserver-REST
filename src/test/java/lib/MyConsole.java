/* ===========================================================================
Created:	2015/08/13 - https://github.com/yes4me/
Author:		Thomas Nguyen - thomas_ejob@hotmail.com
Purpose:	My library
=========================================================================== */

package lib;

import java.util.Scanner;

public class MyConsole {
	private static Scanner reader;

	public static int consoleInputInt()
	{
		reader = new Scanner(System.in);
		int result = reader.nextInt();
		return result;
	}
	public static String consoleInputString()
	{
		reader = new Scanner(System.in);
		String result = reader.nextLine();
		return result;
	}
	public static boolean close() {
		if(reader!=null)
		{
			reader.close();
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.print("==>");
		int i = consoleInputInt();
		System.out.println("i="+ i);
	}
}