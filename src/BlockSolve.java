import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;


public class BlockSolve {
    static HashMap<Integer, HashSet<Integer>> main_map = new HashMap<Integer, HashSet<Integer>>();
    static HashMap<String, Integer> all_sets = new HashMap<String, Integer>();
    
	public static void main(String[] args) { 	    
	    try {
			load_input();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	    
	    int skip = 0;
	    
	    Iterator<Entry<Integer, HashSet<Integer>>> it_1 = main_map.entrySet().iterator();
	    while (it_1.hasNext()) {	   
	    	Entry<Integer, HashSet<Integer>> a_kvp = it_1.next();
			//System.out.println("\t A " + a_kvp);
			
			skip+=1;
			
			Iterator<Entry<Integer, HashSet<Integer>>> it_2 = main_map.entrySet().iterator();				
	    	for (int i = 0; i < skip; i++) {
	    		//System.out.println("\t Loop B - Skipping 1");
	    		it_2.next();
			}	
	    	
			//System.out.println("New Loop B");
		    while (it_2.hasNext()) {	
		    	//System.out.println("\t Begin Loop B");
		    
				Entry<Integer, HashSet<Integer>> b_kvp = it_2.next();
				//System.out.println("\t B " + b_kvp);
				
				
				ArrayList<Integer> intersection = new ArrayList<Integer>(a_kvp.getValue());	
				intersection.retainAll(b_kvp.getValue());
				Collections.sort(intersection);
				
				//if (intersection.size() > 1) {
					String key = intersection.toString();	
					
					if (all_sets.containsKey(key)) {
						continue;
					}
					
					//System.out.println(a_kvp + "-" + b_kvp + " - " + key);
					all_sets.put(key, 1);					
				//}			
			}		
			
		}
	    
	    
	    for (String key : all_sets.keySet()) {
	    	System.out.println(key);
		}
	    
	      
	    
	    
	}

	public static void load_input() throws IOException {
		FileInputStream fis = new FileInputStream("input.txt");
	 
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		while ((line = br.readLine()) != null) {
			int count = 0;
			String[] split = line.split(",");
			Integer key = Integer.parseInt(split[0]);
						
			HashSet<Integer> c_set = new HashSet<Integer>();
			
			for (String s : split) {
				if (count == 0){
					count+=1;
					continue;						
				}
				c_set.add(Integer.parseInt(s));
				
			}
			
			main_map.put(key, c_set);			
		}
	 
		br.close();
	}
}
