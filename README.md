# Data Science Portfolio - Jungsik Noh
This portfolio contains my data science projects for self-learning purposes.
It will be updated on a regular basis.

<p>&nbsp;</p> 

# Contents

- [Implementation of Apriori Algorithm in **Python**](MicroProjects/ImplementationAprioriAlgo.md)
  - Apriori algorithm is used for association rule learning and market basket analysis. 
    It finds frequent itemsets from large-scale transaction data recorded at the 
    point-of-sale (POS).
   
- [Implementation of Breadth First Search (BFS) and Girvan-Newman Algorithm for Graph analysis in **R**](https://rpubs.com/JungsikNoh/Implement_GirvanNewman_GraphAnalysis_R)
  - Girvan-Newman Algorithm is for community detection in a network, that is, a graph. 
    Its implementation first starts with BFS to find minimum spanning trees in a graph. 
    Then we can compute the edge betweenness which is the number of the shortest paths going through 
    a given edge, as a measure of centrality of an edge in a graph.
    By successively removing the edges with the highest betweenness, the algorithm detects communities
    which are disconnected components.
   
- [Implementation of Reservoir Sampling Algorithm in **Java**](MicroProjects/Implementation_ReservoirSampling_Java.md)
  - To analyze rapidly arriving data streams where we cannot store all the data points, 
    effective sampling strategies become critical.
    Among those, Reservoir Sampling allows us to keep a sample with a fixed size, say $k$, at any given time, 
    even though the algorithm does not look back at previous data points. 
    The produced sample satifies a nice property that it is always a simple random sample 
    from all the data points at any given time ( $\\{ x_1, x_2, \ldots, x_t \\}$ for any $t$ ).
  
- [Implementation of Hierarchical Clustering (HCL) Algorithm in **R**](https://rpubs.com/JungsikNoh/ImplementHCLinR)
  - HCL is one of the most useful clustering algorithms. 
    It can also provide an order of data points based on their pairwise distances, 
    which is often useful for various data visualizations. 
 


# Contact
Jungsik Noh (jungsik.noh@utsouthwestern.edu)

