rem ===========================================================================
rem Created:	2015/08/10 - https://github.com/yes4me/
rem Author:		Thomas Nguyen - thomas_ejob@hotmail.com
rem Purpose:	Make a jar file from java file
rem ===========================================================================

@ECHO OFF
@CLS
rem jar -cf myfile.jar *.java
jar -cvmf MANIFEST.MF app1.jar *.class
dir /oe