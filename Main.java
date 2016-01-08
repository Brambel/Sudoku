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
		System.out.println(directory.get(19)[0]);
		System.out.println(directory.get(19)[1]);
		System.out.println(directory.get(19)[2]);
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
			// TODO Auto-generated catch block
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
				//remember we still are not filling direct correctly.
				keys.add(i+j);
				cols_.put(j,new Vector<Integer>());
				Integer[] temp = {i,j,i};
				direct.put(i+j,temp);
				//i=1;i<82
				//{i%9,(i/10)*10,?}
			}
		}
		Collections.sort(keys);
		
		
	}
	public static void printPuzzle(Map<Integer, Vector<Integer>> cells_){
		Set<Integer> keys = cells_.keySet();
		int i=1;
		for(Integer k : keys){
			if(!cells_.get(k).isEmpty()){
				System.out.print(cells_.get(k).get(0));
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
}
