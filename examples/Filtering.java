/**
 * FinderSelection
 * A native Java/Processing library which reads, sorts and filters Mac-Finder selections.
 *
 * Example: Filtering
 * Make sure to select something in the Mac-Finder before you run this sketch.
 *
 * @author      Dominique Schmitz http://www.domizai.ch
 */

import domizai.finderSelection.*;
import static java.lang.System.out;


public class Filtering{


	public static void main(String [] args){
		
		FinderSelection selection = new FinderSelection();

		String [] mySelection;


	  	// Get only the files with extention html
		selection.withExtentions("html");
		  
		  
		mySelection = selection.getNames();  
		// or:  mySelection = selection.withExtentions("html").getNames();  
		out.println("FILTERED BY EXTENTIONS:");
		printAll( mySelection );
		  
		  
		// Clear all filters to get full selection again and for applying new filters.
		selection.clear();
		// Use clearAuto(true) if you don't always want to use clear(). Default is false 
		// selection.clearAuto(true);
		// or when instantiating: 
		// FinderSelection selection = new FinderSelection().clearAuto(true);
		  
		  
		mySelection = selection.onlyDirectories().getNames();   
		// or:  mySelection = selection.clear().onlyDirectories().getNames();    
		out.println("FILTERED BY DIRECTORIES:");
		printAll( mySelection );
		  
		  
		mySelection = selection.clear().withExtentions("jpg").includes("foo").descending().getPaths();  
		out.println("FILTERED BY EXTENTIONS AND INCLUDED STRING, IN DESCENDING ORDER:");
		printAll( mySelection );
  }


  static void printAll(String [] s){
	  for(int i = 0; i < s.length; i++) out.println( (i+1) +": "+ s[i]);
	  out.println();
	}
}

