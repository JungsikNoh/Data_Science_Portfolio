import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReservoirSampling {

	public static void main(String[] args) {
		// data stream = (1:1000) and a reservoir of size 10
		int[] s = new int[1000];
		for (int i = 0; i < 1000; i++) {
			s[i] = i+1;
		}
		
		int k = 10;					// size of a reservoir
						
		List<Integer> S = sampleData(s, k);
	}
		
	public static List<Integer> sampleData(int[] inputs, int k) {		
		// Sampled data
		List<Integer> S = new ArrayList<Integer>();
		List<Integer> indexForPrint = Arrays.asList(10,50,100,500,1000);
		
		// Sample sequentially from the data stream input
		for (int j = 0; j < k; j++) {
			S.add(inputs[j]);
			System.out.println("Processed " + (j+1) + " elements. S= " + S);
		}
		
		for (int j = k; j < inputs.length; j++) {		
			// Reservoir sampling
			int[] accept_location = reservoirSampling(j, k);
			if (accept_location[0] == 1) {
				S.set(accept_location[1], inputs[j]);	
				System.out.println("Processed " + (j+1) + " elements. S= " + S);
			} else if (indexForPrint.contains(j+1)) {
				System.out.println("Processed " + (j+1) + " elements. S= " + S);
			}
		}	
			
		return S;
	}
	
	public static int[] reservoirSampling(int j, int k) {
		// Algorithm of reservoir sampling
		int[] accept_location = new int[2];
		int l = (int) (Math.random() * j);
		if (l <= k-1) {
			accept_location[0] = 1;
			accept_location[1] = l;
		}
			
		return accept_location;
	}
	
}

