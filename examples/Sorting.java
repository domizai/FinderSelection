/**
 * FinderSelection
 * A native Java/Processing library which reads, sorts and filters Mac-Finder selections.
 *
 * Example: Sorting
 * Make sure to select something in the Mac-Finder before you run this sketch.
 *
 * @author      Dominique Schmitz http://www.domizai.ch
 */

import domizai.finderSelection.*;
import static java.lang.System.out;


public class Sorting{


	public static void main(String [] args){
		
		FinderSelection selection = new FinderSelection();

		String [] mySelection;


	  	mySelection = selection.sortByDate().getPaths();  
		out.println("SORTED BY LAST MODIFIED DATE:");
		printAll( mySelection );
		  

		mySelection = selection.sortByName().getNames();  
		out.println("SORTED BY NAMES:");
  		printAll( mySelection );
  }


  static void printAll(String [] s){
	  for(int i = 0; i < s.length; i++) out.println( (i+1) +": "+ s[i]);
	  out.println();
	}
}

