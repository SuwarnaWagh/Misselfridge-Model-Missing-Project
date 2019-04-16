package pkgmainclass;

import java.util.ArrayList;
import java.util.List;

public class SplitArray {
	
	public static List<List<String>> splitArray(List<String> arrayToSplit, int chunkSize){
	    if(chunkSize<=0){
	        return null;  // just in case :)
	    }
	    // first we have to check if the array can be split in multiple 
	    // arrays of equal 'chunk' size
 	    int rest = arrayToSplit.size() % chunkSize;  // if rest>0 then our last array will have less elements than the others 
	    // then we check in how many arrays we can split our input array
	    int chunks = arrayToSplit.size() / chunkSize + (rest > 0 ? 1 : 0); // we may have to add an additional array for the 'rest'
	    // now we know how many arrays we need and create our result array
	    List<String> arrays = new  ArrayList<String>();
	    List<List<String>> testobj = new ArrayList<List<String>>();
	    // we create our resulting arrays by copying the corresponding 
	    // part from the input array. If we have a rest (rest>0), then
	    // the last array will have less elements than the others. This 
	    // needs to be handled separately, so we iterate 1 times less.
	    for(int i = 0; i < (rest > 0 ? chunkSize - 1 : chunkSize); i++){
	        // this copies 'chunk' times 'chunkSize' elements into a new array
	        //arrays[i] = Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize);
	        arrays = arrayToSplit.subList((i * chunks),(i * chunks + chunks));
	        testobj.add(arrays);
	        
	        
	    }
	    if(rest > 0){ // only when we have a rest
	        // we copy the remaining elements into the last chunk
	        //arrays = arrayToSplit.subList(((chunks - 1) * chunkSize), ((chunks - 1) * chunkSize + rest));
	    	arrays = arrayToSplit.subList((chunks*(chunkSize-1))+1, arrayToSplit.size());
	        testobj.add(arrays);
	    }
	    return testobj; // that's it
	}

}
