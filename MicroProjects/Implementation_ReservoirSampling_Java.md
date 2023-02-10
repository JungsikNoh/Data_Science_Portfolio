# Implementation of Reservoir Sampling Algorithm in Java
### *Jungsik Noh*

To analyze rapidly arriving data streams where we cannot store all the data points, effective sampling strategies become critical. Among those, Reservoir Sampling allows us to keep a sample with a fixed size, say $k$, at any given time, even though the algorithm does not look back at previous data points. The produced sample satifies a nice property that it is always a simple random sample from all the data points at any given time ( $x_1, x_2, \ldots, x_t$ 
 for any $t$).

The following *Java* code implement *Resorvoir Sampling* of 10 data points from a stream of 1,000 data points, $(1, 2, \ldots, 1000)$. The sampling output is shown when the sample changes. 


```python
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
```

#### Output


```python
Processed 1 elements. S= [1]
Processed 2 elements. S= [1, 2]
Processed 3 elements. S= [1, 2, 3]
Processed 4 elements. S= [1, 2, 3, 4]
Processed 5 elements. S= [1, 2, 3, 4, 5]
Processed 6 elements. S= [1, 2, 3, 4, 5, 6]
Processed 7 elements. S= [1, 2, 3, 4, 5, 6, 7]
Processed 8 elements. S= [1, 2, 3, 4, 5, 6, 7, 8]
Processed 9 elements. S= [1, 2, 3, 4, 5, 6, 7, 8, 9]
Processed 10 elements. S= [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
Processed 11 elements. S= [1, 11, 3, 4, 5, 6, 7, 8, 9, 10]
Processed 12 elements. S= [1, 11, 3, 4, 5, 6, 12, 8, 9, 10]
Processed 14 elements. S= [1, 11, 3, 4, 5, 14, 12, 8, 9, 10]
Processed 15 elements. S= [1, 11, 3, 4, 5, 14, 12, 8, 15, 10]
Processed 16 elements. S= [1, 11, 3, 4, 16, 14, 12, 8, 15, 10]
Processed 20 elements. S= [1, 11, 3, 4, 16, 14, 20, 8, 15, 10]
Processed 22 elements. S= [1, 11, 3, 4, 16, 22, 20, 8, 15, 10]
Processed 24 elements. S= [1, 11, 24, 4, 16, 22, 20, 8, 15, 10]
Processed 27 elements. S= [1, 27, 24, 4, 16, 22, 20, 8, 15, 10]
Processed 29 elements. S= [1, 27, 24, 29, 16, 22, 20, 8, 15, 10]
Processed 31 elements. S= [1, 27, 24, 29, 16, 22, 20, 8, 15, 31]
Processed 32 elements. S= [1, 27, 24, 29, 16, 22, 20, 32, 15, 31]
Processed 35 elements. S= [1, 27, 24, 29, 16, 22, 20, 35, 15, 31]
Processed 36 elements. S= [36, 27, 24, 29, 16, 22, 20, 35, 15, 31]
Processed 37 elements. S= [36, 37, 24, 29, 16, 22, 20, 35, 15, 31]
Processed 38 elements. S= [36, 37, 24, 29, 16, 22, 20, 35, 38, 31]
Processed 39 elements. S= [36, 37, 24, 29, 39, 22, 20, 35, 38, 31]
Processed 41 elements. S= [36, 37, 24, 29, 39, 22, 20, 35, 38, 41]
Processed 44 elements. S= [36, 37, 24, 29, 44, 22, 20, 35, 38, 41]
Processed 47 elements. S= [36, 37, 24, 29, 44, 22, 47, 35, 38, 41]
Processed 48 elements. S= [36, 37, 24, 48, 44, 22, 47, 35, 38, 41]
Processed 50 elements. S= [36, 37, 24, 48, 44, 22, 47, 35, 38, 41]
Processed 51 elements. S= [36, 37, 24, 51, 44, 22, 47, 35, 38, 41]
Processed 54 elements. S= [36, 37, 24, 51, 54, 22, 47, 35, 38, 41]
Processed 65 elements. S= [36, 37, 24, 51, 54, 65, 47, 35, 38, 41]
Processed 73 elements. S= [36, 37, 24, 51, 54, 65, 47, 73, 38, 41]
Processed 93 elements. S= [36, 37, 24, 51, 54, 65, 47, 73, 38, 93]
Processed 97 elements. S= [97, 37, 24, 51, 54, 65, 47, 73, 38, 93]
Processed 100 elements. S= [97, 37, 24, 51, 54, 65, 47, 73, 38, 93]
Processed 110 elements. S= [97, 110, 24, 51, 54, 65, 47, 73, 38, 93]
Processed 113 elements. S= [97, 110, 113, 51, 54, 65, 47, 73, 38, 93]
Processed 156 elements. S= [97, 110, 113, 51, 54, 156, 47, 73, 38, 93]
Processed 239 elements. S= [97, 110, 113, 51, 54, 156, 47, 239, 38, 93]
Processed 255 elements. S= [97, 110, 113, 51, 54, 156, 255, 239, 38, 93]
Processed 328 elements. S= [97, 110, 113, 51, 54, 156, 255, 239, 328, 93]
Processed 361 elements. S= [97, 110, 113, 51, 54, 156, 255, 239, 361, 93]
Processed 391 elements. S= [97, 110, 113, 51, 54, 156, 255, 391, 361, 93]
Processed 406 elements. S= [406, 110, 113, 51, 54, 156, 255, 391, 361, 93]
Processed 500 elements. S= [406, 110, 113, 51, 54, 156, 255, 391, 361, 93]
Processed 508 elements. S= [406, 110, 113, 51, 54, 156, 255, 391, 508, 93]
Processed 585 elements. S= [406, 110, 113, 51, 54, 156, 255, 391, 585, 93]
Processed 594 elements. S= [406, 110, 113, 594, 54, 156, 255, 391, 585, 93]
Processed 612 elements. S= [406, 110, 113, 594, 54, 156, 612, 391, 585, 93]
Processed 775 elements. S= [775, 110, 113, 594, 54, 156, 612, 391, 585, 93]
Processed 778 elements. S= [775, 110, 113, 594, 54, 156, 778, 391, 585, 93]
Processed 889 elements. S= [775, 110, 113, 594, 889, 156, 778, 391, 585, 93]
Processed 918 elements. S= [775, 110, 113, 594, 918, 156, 778, 391, 585, 93]
Processed 955 elements. S= [955, 110, 113, 594, 918, 156, 778, 391, 585, 93]
Processed 1000 elements. S= [955, 110, 113, 594, 918, 156, 778, 391, 585, 93]

```
