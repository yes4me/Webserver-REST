/* ===========================================================================
Created:	2015/08/10 - https://github.com/yes4me/
Author:		Thomas Nguyen - thomas_ejob@hotmail.com
Purpose:	My library
=========================================================================== */

package lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class MyFile {
	//========================================================
	// INTERVIEW METHODS
	//========================================================

	public static boolean fileExist(String filePath) {
		File file = new File(filePath);
		return ( file.exists() && !file.isDirectory() ); 
	}
	
	public static String readTextFile(String filePath) {
		StringBuffer fileContent = new StringBuffer("");
		BufferedReader br = null;
		try
		{
			String sCurrentLine;
			br = new BufferedReader(new FileReader(filePath));
			while ((sCurrentLine = br.readLine()) != null)
			{
				fileContent.append(sCurrentLine);
				fileContent.append("\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (br != null)
					br.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		String result	= fileContent.toString();
		result			= result.replaceAll("(\\s)$", "");
		return result;
	}
	public static boolean writeTextFile(String filePath, String data) {
		BufferedWriter output = null;
		try {
			File file = new File(filePath);
			output = new BufferedWriter(new FileWriter(file));
			output.write(data);

			//flush the stream
			output.flush();

			//close the stream
			output.close();

			return true;
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return false;
	}

	//========================================================
	// OTHERS
	//========================================================

	public static Reader reader(String filePath) {
		Reader reader= null;
		try {
			reader = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return reader;
	}
	
	//Read a file content and write it to a new file in reverse order.( reverse line 1-10 to line 10-1)
	public static void main(String arg[])
	{
		String readTxt = readTextFile("test.txt");
		readTxt = MyPrimitive.reverseChar(readTxt);
		writeTextFile("test.txt", readTxt);
		System.out.println("##"+ readTxt +"##");
	}
}