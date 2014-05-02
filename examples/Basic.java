/**
 * FinderSelection
 * A native Java/Processing library which reads, sorts and filters Mac-Finder selections.
 *
 * Example: Basic
 * Make sure to select something in the Mac-Finder before you run this sketch.
 *
 * @author      Dominique Schmitz http://www.domizai.ch
 */

import domizai.finderSelection.*;
import static java.lang.System.out;


public class Basic{


	public static void main(String [] args){
		
		FinderSelection selection = new FinderSelection();

		String [] mySelection;


	  	mySelection = selection.getPaths();     
		out.println("ABSOLUT PATHS:");
		printAll( mySelection );
		// /Users/Path/to/file.jpg
		  
		mySelection = selection.getExtentions();
		out.println("EXTENTIONS:");
		printAll( mySelection );
		// image/jpg
		  
		mySelection = selection.getMimeTypes();
		out.println("MIMETYPES:");
		printAll( mySelection );
		// jpg
		  
		mySelection = selection.getNames();
		out.println("NAMES:");
		printAll( mySelection );
		// filename.jpg
		  
		mySelection = selection.getDates();
		out.println("DATES:");
		printAll( mySelection );
		// 05/01/2014 16:48:16
  }


  static void printAll(String [] s){
	  for(int i = 0; i < s.length; i++) out.println( (i+1) +": "+ s[i]);
	  out.println();
	}
}

