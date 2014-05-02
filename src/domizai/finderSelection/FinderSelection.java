/**
 * FinderSelection
 * A native Java/Processing library which reads, sorts and filters Mac-Finder selections.
 *
 * Copyright (C) 2014 Dominique Schmitz http://www.domizai.ch
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      Dominique Schmitz http://www.domizai.ch
 * @modified    05/01/2014
 * @version     0.1 (1)
 */

package domizai.finderSelection;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Comparator;
import java.util.LinkedList;
import java.text.SimpleDateFormat;


/**
 * FinderSelection A native Java/Processing library which reads, sorts and filters Mac-Finder selections.
 */
public class FinderSelection extends LibrarySettings implements Separator{
	private boolean autoClean = false;
	String[] files;
	private List<Item> selection = new ArrayList<Item>();


	/**
	 * Constructor
	 * 
	 * @example FinderSelection selection = new FinderSelection();
	 */
	public FinderSelection(){	
		newSelection();
	}

	/**
	 * Gets the selected files in Mac-Finder.
	 */
	public FinderSelection newSelection(){
		selection.clear();

		String [] cmd = {	"osascript", "-e",
							"tell application \"Finder\" to set theSelection to (selection) as alias list", "-e",
							"try", "-e",
								"repeat with i from 1 to (length of theSelection) - 1", "-e",
									"set item i of theSelection to POSIX path of item i of theSelection & \"<%>\"", "-e",
								"end repeat", "-e",
								"set last item of theSelection to POSIX path of last item of theSelection", "-e",
							"end try", "-e",
							"if length of theSelection > 0 then return theSelection as string"
						};
		

		try{
			files = new BufferedReader ( new InputStreamReader( Runtime.getRuntime().exec(cmd).getInputStream() ) ).readLine().split("<%>");
			
			for(int i = 0; i < files.length; i++)
				selection.add( new Item(files[i]) );

		} catch (Exception e){ }

		return this;
	}


	/**
	 * Returns the extentions of the selected files.("jpg")
	 * 
	 * @return String[]
	 */
	public String [] getExtentions(){
		String [] ext = new String[ selection.size() ];

		for(int i = 0; i < selection.size(); i++)
			ext[i] = selection.get(i).getExtention();
		
		if(autoClean) clear();
		return ext;
	}

	/**
	 * Returns the absolut paths of the selected files.
	 * 
	 * @return String[]
	 */
	public String [] getPaths(){
		String [] ext = new String[ selection.size() ];

		for(int i = 0; i < selection.size(); i++)
			ext[i] = selection.get(i).getPath();
		
		if(autoClean) clear();
		return ext;
	}

	/**
	 * Returns the mimetypes of the selected files. ("image/jpeg")
	 * 
	 * @return String[]
	 */
	public String [] getMimeTypes(){
		String [] ext = new String[ selection.size() ];

		for(int i = 0; i < selection.size(); i++)
			ext[i] = selection.get(i).getMimeType();
		
		if(autoClean) clear();
		return ext;
	}

	/**
	 * Returns the names of the selected files.
	 * 
	 * @return String[]
	 */
	public String [] getNames(){
		String [] ext = new String[ selection.size() ];

		for(int i = 0; i < selection.size(); i++)
			ext[i] = selection.get(i).getName();
		
		if(autoClean) clear();
		return ext;
	}

	/**
	 * Returns the formated dates of the selected files. ("05/01/2014 01:05:58")
	 * 
	 * @return String[]
	 */
	public String [] getDates(){
		String [] dat = new String[ selection.size() ];

		for(int i = 0; i < selection.size(); i++)
			dat[i] = selection.get(i).getDate();
		

		if(autoClean) clear();
		return dat;
	}


	/**
	 * Removes filters
	 */
	public FinderSelection clear(){ 
		selection.clear();

		for(int i = 0; i < files.length; i++)
			selection.add( new Item(files[i]) );

		return this;
	}

	/**
	 * Automatically clears filters after calling a return function.
	 */
	public FinderSelection autoClear(boolean bool){
		autoClean = bool;
		return this; 
	}

	/**
	 * Sorts the selection by filename.
	 */
	public FinderSelection sortByName(){
		if(selection != null){
			List<Item> temp = new ArrayList<Item>(selection);
			List<String> names = new ArrayList<String>();

			for(int i = 0; i < selection.size(); i++)
				names.add( selection.get(i).getName() );

			sort(names, Collator.TERTIARY);

			for(int i = 0; i < selection.size(); i++)
			    selection.set( names.indexOf( temp.get(i).getName() ), temp.get(i)  ); 

		}
		return this;
	}

	private static void sort(List<String> aWords, int aStrength){
	    Collator collator = Collator.getInstance();
	    collator.setStrength(aStrength);
		Collections.sort(aWords, collator);
	}

	/**
	 * Sorts the selection by the files last modified date.
	 */
	public FinderSelection sortByDate(){
		List<Item> temp = new ArrayList<Item>(selection);
		List<Long> dates = new ArrayList<Long>();

		for(int i = 0; i < selection.size(); i++)
			dates.add( selection.get(i).getRawDate() );

		Collections.sort(dates);

		for(int i = 0; i < selection.size(); i++)
			selection.set( dates.indexOf( temp.get(i).getRawDate() ), temp.get(i)  );

		return this;
	}

	/**
	 * Puts the selection in descending order.
	 */
	public FinderSelection descending(){
		if(selection != null){
			List<Item> temp = new ArrayList<Item>(selection);
			
			for(int i = 0; i < selection.size(); i++)
				selection.set(i, temp.get( selection.size()-(i+1) )  );
		}
		return this;
	}

	/**
	 * Gets only the files with the matching extentiion. ("jpg")
	 *
	 * @param extention
	 */
	public FinderSelection withExtentions(String extention){
		if(selection != null){
			for(int i = 0; i < selection.size(); i++){
				if( !extention.equals( selection.get(i).getExtention() )  ){
					selection.remove( i );
					i--;
				}
			}
		}
		return this; 
	}

	/**
	 * Gets only directories
	 *
	 * @param extention
	 */
	public FinderSelection onlyDirectories(){
		if(selection != null){
			for(int i = 0; i < selection.size(); i++){
				if( !selection.get(i).isDirectory() ){
					selection.remove( i );
					i--;
				}
			}
		}
		return this; 
	}

	/**
	 * Gets only the files with the matching mimetype. ("image/jpeg")
	 *
	 * @param type
	 */
	public FinderSelection withMimeType(String type){
		if(selection != null){
			for(int i = 0; i < selection.size(); i++){
				if( !type.equals( selection.get(i).getMimeType() ) ){
					selection.remove( i );
					i--;
				}
			}
		}
		return this; 
	}

	/**
	 * Gets only the files with the matching string in the filename.
	 *
	 * @param search  the string to search for in the filename
	 */
	public FinderSelection includes(String search){
		if(selection != null){
			for(int i = 0; i < selection.size(); i++){
				if(  !selection.get(i).getName().toLowerCase().contains( search.toLowerCase() )   ){
					selection.remove( i );
					i--;
				} 
			}
		}
		return this; 
	}

} // end of class FinderSelection


/**
 * Class LibrarySettings
 */
class LibrarySettings{
	public final static String VERSION = "0.1";

	/**
	 * Return the version of the library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}
}


/**
 * Interface Separator
 * 
 * The systems file separator. ("/")
 */
interface Separator{ final static String SEPARATOR = System.getProperty("file.separator"); }


/**
 * Class Item
 * 
 * Stores a selected files porperties.
 */
class Item implements Separator{
	private String name, extention, mimeType, path;
	private boolean directory;
	private static int totalInstances = 0;
	private int id = 0;
	private long date; 
	private File file;


	public Item(String path){
		id = totalInstances;
		totalInstances++;
		this.path = path;
		file = new File(path);
		name = file.getName();
		directory = file.isDirectory();
		setExtention();
		date = file.lastModified();
		mimeType = URLConnection.getFileNameMap().getContentTypeFor(path);
	}


	private void setExtention(){
		if(new File( path ).isDirectory()){
			extention = SEPARATOR;
		} else {
			final String lastFile = path.substring(path.lastIndexOf(SEPARATOR) + 1);						
						
			if(lastFile.lastIndexOf('.') != -1){ 
				extention = lastFile.substring(lastFile.lastIndexOf('.') + 1);
			} else {
				extention = null;
			}
		}
	}


	public void setId(int newID){ this.id = newID;}
	public String getExtention(){ return extention; }
	public String getPath(){ return path; }
	public String getName(){ return name; }
	public String getMimeType(){ return mimeType; }
	public boolean isDirectory(){ return directory; }
	public int getTotalInstanctes(){ return (totalInstances); }
	public int getId(){ return (this.id); }
	public long getRawDate(){ return (this.date); }
	public String getDate(){ SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); return ( new String( sdf.format(file.lastModified()) ) ); }
	public File getFile(){ return (this.file); }

} // end of class Item
