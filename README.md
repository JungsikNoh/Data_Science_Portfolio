# Data Science Portfolio - Jungsik Noh
This portfolio contains my data science projects for self-learning purposes.
It will be updated on a regular basis.

<p>&nbsp;</p> 

> **LinkedIn**: https://www.linkedin.com/in/jungsik-noh-8a33ab129/ 

<p>&nbsp;</p>

## Research Projects

<img align="left" width="250" height="150" src="https://github.com/JungsikNoh/Data_Science_Portfolio/blob/main/doc/photo-COVID-unsplash-1585858228804-7caf9961c49d.jpg"> **[Estimation of the fraction of COVID-19 infected people in U.S. states and countries worldwide](https://journals.plos.org/plosone/article?id=10.1371/journal.pone.0246772)** 
published in 2021 at PLOS ONE ([**GitHub**](https://github.com/JungsikNoh/COVID19_Estimated-Size-of-Infectious-Population))

This study estimated actual daily numbers of COVID infections including unconfirmed or unreported cases. 
Our estimates of cumulative incidence were in line with the seroprevalence rates in 46 U.S. states sampled in 2020. 
We estimated that the COVID cases in the U.S. were likely to be nearly four times more than the reported total cases as of Oct. 2020. 
This study demonstrated that the regional severity of the COVID-19 pandemic had been misguided by the under-reported numbers of COVID infections.

Its [GitHub repository](https://github.com/JungsikNoh/COVID19_Estimated-Size-of-Infectious-Population) had reported daily estimates of COVID-19 cases from August 22, 2020 to October 7, 2022 on a daily or weekly basis. 

##

<img align="left" width="250" height="150" src="https://github.com/JungsikNoh/Data_Science_Portfolio/blob/main/doc/Combined%20Stacks1121-2.png"> [**Granger-causal inference of the lamellipodial actin regulator hierarchy by live cell imaging without perturbation**](https://www.cell.com/cell-systems/pdfExtended/S2405-4712(22)00224-1) published in 2022 at Cell Systems ([**GitHub**](https://github.com/JungsikNoh/Granger-Causality-Analysis-of-Lamellipodia))

The goal of this paper is to determine cause-effect relationships between protein activities that drive cell motion based on their activity time series extracted from microscopic videos of moving cells. 
I developed an innovative time series model and causal inference pipeline equipped with GUI to analyze tens of thousands of the local protein activity time courses. 

##

<br />


## Micro Projects

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

