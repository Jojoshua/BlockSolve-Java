import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

class Set_info
{
    public ArrayList<Integer> chemical_set = new ArrayList<Integer>();
    public HashSet<Integer> protein_set = new HashSet<Integer>();
}

class Input_info
{
	public Integer key = 0;
    public HashSet<Integer> input_chemicals = new HashSet<Integer>();
    public Integer index = 0;      
}

public class BlockSolve {
    static ArrayList<Input_info> main_map = new ArrayList<Input_info>();
    static HashMap<String, Set_info> all_sets = new HashMap<String, Set_info>();
    static int skip = 0; 
    static int start_at = 0; 
    static int stop_at = 0; 
	public static void main(String[] args) throws InterruptedException { 	    
		start_at = Integer.parseInt(args[0]);		
		stop_at = Integer.parseInt(args[1]);			
		
		try {
			load_input();			
		} catch (IOException e) {			
			e.printStackTrace();
		}   	  	    
		
		//System.out.println("Starting at " + start_at + " Stopping at " +stop_at);				
		   
	    Iterator<Input_info> it_1 = main_map.iterator();			
	    while (it_1.hasNext()) {	   
	    	Input_info a_kvp = it_1.next();
			//System.out.println("\t A " + a_kvp);	 	    	   	
	    	//System.out.println("Loop A " + a_kvp.key);	
	    	
	    	//If I reached the index to stop at, be done
	    	if (a_kvp.index == stop_at)
	        break;
	    	
			
			skip+=1;
			
			Iterator<Input_info> it_2 = main_map.iterator();				
	    	for (int i = 0; i < skip; i++) {
	    		Input_info kvp = it_2.next();
	    		//System.out.println("\t Loop B - Skipping " + kvp.key);	    		
			}	
	    	
			//System.out.println("New Loop B");
		    while (it_2.hasNext()) {			    
				Input_info b_kvp = it_2.next();
				//System.out.println("\t B " + b_kvp.key);
				
				//Should try guava intersection (small,large) sometime
				ArrayList<Integer> intersection = new ArrayList<Integer>(a_kvp.input_chemicals);	
				intersection.retainAll(b_kvp.input_chemicals);
				Collections.sort(intersection);      
				
				if (intersection.size() > 1) {
					// Made a block
					Set_info set_info = new Set_info();
					
					String key = intersection.toString();	
					
					if (all_sets.containsKey(key)) {
						set_info = all_sets.get(key);
	                    set_info.protein_set.add(a_kvp.key);
	                    set_info.protein_set.add(b_kvp.key);
					}
					else
					{
		                set_info.chemical_set = intersection;
		                set_info.protein_set.add(a_kvp.key);
		                set_info.protein_set.add(b_kvp.key);
		                all_sets.put(key,set_info);
					}					

					//System.out.println(a_kvp + "-" + b_kvp + " - " + key);									
				}			
			}
		}  
	    
	    
	    
	    
	 //Initialize forkjoinpool
/*	   ForkJoinPool pool = new ForkJoinPool(4);
	   //List<IntersectProcessor> tasks = new ArrayList<IntersectProcessor>();	   
	   Integer start_at = 0;
	   Integer stop_at = 0;
	   
	   Integer parts = main_map.size()/4;
	   
	   start_at = 0;
	   stop_at = parts;
	   IntersectProcessor ip1 = new IntersectProcessor(main_map,start_at,stop_at);	 
	   pool.execute(ip1);
	   
	   start_at = stop_at;
	   stop_at = stop_at + parts;
	   IntersectProcessor ip2 = new IntersectProcessor(main_map,start_at,stop_at);	 
	   pool.execute(ip2);
	   
	   start_at = stop_at;
	   stop_at = stop_at + parts;
	   IntersectProcessor ip4 = new IntersectProcessor(main_map,start_at,stop_at);	 
	   pool.execute(ip4);
	   
	   start_at = stop_at;
	   stop_at = 0; // The last one should always take the rest(0) will never break
	   IntersectProcessor ip3 = new IntersectProcessor(main_map,start_at,stop_at);	 
	   pool.execute(ip3);
	   
	   do {
		
	   } while (!ip1.isDone() || !ip2.isDone() || !ip3.isDone() || !ip4.isDone()
			   );
	   
	   pool.shutdown();
	   all_sets.putAll(ip1.join());
	   all_sets.putAll(ip2.join());
	   all_sets.putAll(ip3.join());
	   all_sets.putAll(ip4.join());*/
	   
	  
	   
	    
	    
	   /* Iterator<Entry<Integer, Input_info>> it_1 = main_map.entrySet().iterator();
	    while (it_1.hasNext()) {	   
	    	Entry<Integer, Input_info> a_kvp = it_1.next();
			//System.out.println("\t A " + a_kvp);
			
			skip+=1;
			
			Iterator<Entry<Integer, Input_info>> it_2 = main_map.entrySet().iterator();				
	    	for (int i = 0; i < skip; i++) {
	    		//System.out.println("\t Loop B - Skipping 1");
	    		it_2.next();
			}	
	    	
			//System.out.println("New Loop B");
		    while (it_2.hasNext()) {	
		    	//System.out.println("\t Begin Loop B");
		    
				Entry<Integer, Input_info> b_kvp = it_2.next();
				//System.out.println("\t B " + b_kvp);
				
				
				ArrayList<Integer> intersection = new ArrayList<Integer>(a_kvp.getValue().input_chemicals);	
				intersection.retainAll(b_kvp.getValue().input_chemicals);
				
				Collections.sort(intersection);      
				
				if (intersection.size() > 1) {
					// Made a block
					Set_info set_info = new Set_info();
					
					String key = intersection.toString();	
					
					
					
					if (all_sets.containsKey(key)) {
						set_info = all_sets.get(key);
	                    set_info.protein_set.add(a_kvp.getKey());
	                    set_info.protein_set.add(b_kvp.getKey());
					}
					else
					{
		                set_info.chemical_set = intersection;
		                set_info.protein_set.add(a_kvp.getKey());
		                set_info.protein_set.add(b_kvp.getKey());
		                all_sets.put(key,set_info);
					}					

					//System.out.println(a_kvp + "-" + b_kvp + " - " + key);									
				}			
			}		
			
		}*/
	    
	    
/*	    for (String key : all_sets.keySet()) {
	    	System.out.println(key);
		}    */
	    
	    
	    //Round 2. Assign any left out subsets          
        Iterator<Entry<String, Set_info>> it_a = all_sets.entrySet().iterator();
       
        
/*	    while (it_a.hasNext()) {	   
	    	Entry<String, Set_info> a_kvp = it_a.next();
	    	
	    	int a_len = a_kvp.getValue().chemical_set.size();
	    	
	    	for (Map.Entry<String, Set_info> b_kvp : all_sets.entrySet()) {
		    	if (a_len < b_kvp.getValue().chemical_set.size()){		    			
			    	if (b_kvp.getValue().chemical_set.containsAll(a_kvp.getValue().chemical_set)) {
			    	    // Make sure all the proteins of the superset are also in the subset
	                    //a_kvp.getValue().protein_set.UnionWith(b_kvp.getValue().protein_set);			    	
	                    b_kvp.getValue().protein_set.addAll(a_kvp.getValue().protein_set);	                    
	                    b_kvp.setValue(b_kvp.getValue());	                    
			    	} 	    	
				}		    	    
	    	}*/
	    	
/*	    	Iterator<Entry<String, Set_info>> it_b = all_sets.entrySet().iterator();		
		    while (it_b.hasNext()) {			    
		    	Entry<String, Set_info> b_kvp = it_b.next();
		    	
		    	if (a_len < b_kvp.getValue().chemical_set.size()){		    			
			    	if (b_kvp.getValue().chemical_set.containsAll(a_kvp.getValue().chemical_set)) {
			    	    // Make sure all the proteins of the superset are also in the subset
	                    //a_kvp.getValue().protein_set.UnionWith(b_kvp.getValue().protein_set);
	                    b_kvp.getValue().protein_set.addAll(a_kvp.getValue().protein_set);
	                    
	                    b_kvp.setValue(b_kvp.getValue());
	                    
			    	} 
			    			    	
				}						
			}*/	
		//}
	    
       write_blocks();	    
	    
	}
	
	public static void write_blocks(){
		File file = new File("output");
		if (!file.exists()) {
			if (file.mkdir()) {
				//System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
		
		 PrintWriter writer = null;
			try {
				writer = new PrintWriter( "output\\" + start_at +"-" + stop_at + ".txt", "UTF-8");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}               
	        
		    for (Entry<String,Set_info> item : all_sets.entrySet()) {
				ArrayList<Integer> p_list = new ArrayList<Integer>(item.getValue().protein_set);	
			    Collections.sort(p_list); 
			    
			    writer.println(item.getKey());
				
		    	//System.out.println("C" + item.getKey() + " P" + p_list);
		    	//System.out.println("C" + item.getKey());
			}
		    writer.close();
	}
	

	public static void load_input() throws IOException {
		FileInputStream fis = new FileInputStream("input.txt");
	 
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		
		Integer index = 0;
		while ((line = br.readLine()) != null) {
			index++;			
			if (index < start_at)
			continue;
			
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
			
            Input_info input_info = new Input_info();
            input_info.input_chemicals = c_set;
            input_info.index = index;
            input_info.key = key;
			
			main_map.add(input_info);			
		}
	 
		br.close();
	}
}

