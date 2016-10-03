/**
 * 
 */
package MP1v1;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author acer
 *
 */
public class FileIndex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String sk="",di="";
		Scanner s=new Scanner(System.in);
		HashMap<String,ArrayList<String>> hash=new HashMap<String,ArrayList<String>>();
		System.out.print("Enter Index directory:");
		di=s.nextLine();
		Path p=Paths.get(di);
		
		try{
			File dir=new File(di);
			float start=System.nanoTime();
			crawl(dir,hash);
			float end=System.nanoTime();
			
			System.out.println("Toatal Words index "+hash.size());
		//	System.out.println("Hash map. "+hash.toString());
			System.out.println("Finished indexing time:"+((end-start)/1000000)+" milliseconds");
			if (hash.isEmpty()){
				System.out.println("Empty");
			}
			while(true){
			System.out.print("Enter Search key:");
			sk=s.nextLine();
			System.out.println("This are the files::");
			System.out.println(search(sk,hash));
			}
		}catch(Exception ex){
			System.out.println("Error WRong paht:"+di);
		}
		
	}//end of main
	public static void crawl(File dir,HashMap<String,ArrayList<String>>hash){
		File [] files=dir.listFiles();
		for(File f:files){
			if(f.isDirectory()){
				crawl(f,hash);
			}else{
				System.out.println("path:"+f.getAbsolutePath());
				if(f.getName().endsWith(".txt"))			
					index(f.getAbsolutePath(),hash);
			}
		}

	}//end of crawl
	public static void index(String path,HashMap<String,ArrayList<String> >hash){
		BufferedReader br;
		try{
			br=new BufferedReader(new FileReader(path));
			String str;
			while((str=br.readLine())!=null){
				String[] t=str.split(" ");
				for(String s:t){
					ArrayList<String>temp;
					if(hash.containsKey(s.toLowerCase())){
						temp=hash.get(s.toLowerCase());
						//hash.
						if(!temp.contains(path))
							temp.add(path);			
					}else{
						temp=new ArrayList<String>();
						temp.add(path);
						hash.put(s.toLowerCase(),temp);
					}
					temp=null;
				}//end of for loop
			}
		}catch(Exception ex){
			System.out.println(ex);
			System.exit(0);
		}
		
	}//end of index
	public static String search(String key,HashMap<String,ArrayList<String>> hash){
		String temp="";
		float start=System.nanoTime();
		//System.out.println();
		int fc=0;
		if(hash.containsKey(key.toLowerCase())){
			for(String s:hash.get(key.toLowerCase())){
				temp+=s+"\n";
				fc++;
			}
			
		}else {
			return "No strings attached";
		}
		float end=System.nanoTime();
		temp+="Number of files:"+fc+"\n";
		temp+="Time taken to search:"+(((end-start)/1000000))+" milliseconds";
		return temp;
	}//end of search

}
