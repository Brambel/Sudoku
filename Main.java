import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Main {
	public static void main(String[] args) {
		Map<Integer, Vector<Integer>> cells = new HashMap<Integer,Vector<Integer>>();
		Map<Integer, Vector<Integer>> rows = new HashMap<Integer,Vector<Integer>>();
		Map<Integer, Vector<Integer>> cols = new HashMap<Integer,Vector<Integer>>();
		Map<Integer, Vector<Integer>> blocks = new HashMap<Integer,Vector<Integer>>();
		List<Integer> validKeys = new Vector<Integer>();
		Map<Integer,Integer[]> directory = new HashMap<>();
		
		initStructs(rows,cols,blocks,validKeys,directory);
		readIn(cells, validKeys);
		
		
		//solve puzzle here
		for(int i=0;i<20;++i){	
			solver(cells, rows, cols, blocks, directory, validKeys);
		}
		printPuzzle(cells);
		
	}
	//reads in given values of puzzle and fills cells 
	public static void readIn(Map<Integer, Vector<Integer>> cells_, List<Integer> keys){

		
		try {
			String rawInput = "";
			for (String line : Files.readAllLines(Paths.get("puzzle.txt"))) {
			    rawInput+=line;
			}
			
			rawInput = rawInput.replaceAll("\t", "");
			rawInput = rawInput.replaceAll("\r","");
			
			char[] input = rawInput.toCharArray();
			Iterator<Integer> current = keys.iterator();
			Vector<Integer> fill = new Vector<>();
			
			for(int i =1;i<10;++i){
				fill.add(Integer.valueOf(i));
			}
			
			int i = 0;
			//Come back and find a better way to read in and cast
			while(current.hasNext()){
				if(input[i]!='-'){
					Vector<Integer> temp = new Vector<>();
					temp.addElement(Integer.valueOf(String.valueOf(input[i])));
					cells_.put(current.next(),new Vector<Integer>(temp));
				}
				else{
					cells_.put(current.next(),new Vector<Integer>(fill));
				}
				++i;
				
			}
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
		
			
		

	}
	//inits all other structs to start values
	public static void initStructs(Map<Integer,Vector<Integer>> rows_, Map<Integer,Vector<Integer>> cols_,
			Map<Integer,Vector<Integer>> blocks_, List<Integer> keys, Map<Integer,Integer[]> direct){
		for(int i=1;i<10;++i){
			
			rows_.put(i,new Vector<Integer>());
			blocks_.put(i,new Vector<Integer>());
			
			for(int j=10;j<100;j+=10){
				//look at how everything can be built at once
				keys.add(i+j);
				cols_.put(j,new Vector<Integer>());	
			}
		}
		Collections.sort(keys);
		for(Integer x : keys){
			Integer[] temp = {x%10,(x/10)*10,((((x/10)-1)/3)*3)+((((x%10)-1)/3)+1)};
			direct.put(x,temp);
		}
		
		
	}
	public static void printPuzzle(Map<Integer, Vector<Integer>> cells_){
		Set<Integer> keys = cells_.keySet();
		int i=1;
		for(Integer k : keys){
			if(!cells_.get(k).isEmpty() && cells_.get(k).size()==1){
				System.out.print(cells_.get(k).get(0));
			}else{
				System.out.print('-');
			}
			if(i%3==0){
				System.out.print("\t");
			}
			if(i%9==0){
				System.out.println("");
			}
			++i;
		}
		System.out.println("all done");
	}
	public static void solver(Map<Integer, Vector<Integer>> cells_, Map<Integer, Vector<Integer>> rows_, Map<Integer, Vector<Integer>> cols_,
			Map<Integer, Vector<Integer>> blocks_, 	Map<Integer,Integer[]> direct, List<Integer> validKeys){
		
		Vector<Integer> temp = new Vector<>();
		
		for(int i=0;i<validKeys.size();++i){
			
			Integer current = validKeys.get(i);
			if(cells_.get(current).size()==1){//update backing maps
				Integer known = cells_.get(current).get(0);
		        if(!rows_.get(direct.get(current)[0]).contains(known)){
		            rows_.get(direct.get(current)[0]).add(known);
		        }
		        if(!cols_.get(direct.get(current)[1]).contains(known)){
		            cols_.get(direct.get(current)[1]).add(known);
		        }
		        if(!blocks_.get(direct.get(current)[2]).contains(known)){
		            blocks_.get(direct.get(current)[2]).add(known);
		        }
		    temp.add(current); //keep track of known answers
		    }
		    else{//we update cells based on their backing row, col, and block
		        
		    	for(Integer val : rows_.get(direct.get(current)[0])){
		            if(cells_.get(current).contains(val)){
		                cells_.get(current).remove(val);
		            }
		        }
		        for(Integer val : cols_.get(direct.get(current)[1])){
		            if(cells_.get(current).contains(val)){
		                cells_.get(current).remove(val);
		            }
		        }
		        for(Integer val : blocks_.get(direct.get(current)[2])){
		            if(cells_.get(current).contains(val)){
		                cells_.get(current).remove(val);
		            }
		        }
		    }
		}
		temp.forEach(x -> validKeys.remove(x));
	}
}