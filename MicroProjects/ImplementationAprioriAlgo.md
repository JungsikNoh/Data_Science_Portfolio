# Implementation of Apriori Algorithm from scratch in Python
### *Jungsik Noh*

Apriori algorithm is used for association rule learning and market basket analysis. It finds frequent itemsets from large-scale transaction data recorded at the point-of-sale (POS).

## Apriori algorithm from scratch


The implementation consists of three functions:
- *Apriori(transactionDict, threshold)* 
  - The main loop to implement a 'bottom up' search of itemsets with $K \geq 1$ items which frequently appear among transactions    
- *Apriori_gen_nextItemsets(freqItemset_k_1, K)*
  - Find candidate itemsets with $K$ items based on $K-1$ frequent itemsets
- *findBaskets(itemList, transactionDict)*
  - Find a list of transactions that contain a given frequent itemset


```python
import numpy as np
```


```python
def Apriori(transactionDict, threshold):
    """
    Implementation of Apriori-algorithm, 
    which calculates frequent itemsets from a transaction list 
    for Market-basket analysis.
    Input is a transaction data in a format of dictionary and 
    the support threshold (minimal # of transactions).
    Input example:
        transactionDict = {1: ['1', '2', '4', '7'],  2: ['2', '4', '5']}
        threshold       = 2
    """
    
    allFreqItemsetDicts = list()
    
    # Extract unique singletons in transactionDict values
    itemlist = list(sorted({ele for val in transactionDict.values() for ele in val}))
    print("== unique singletons ==")
    print(itemlist)
    
    # level K denotes num of items in an itemset
    K = 1   
    print('\n' + '== Current level = ' + str(K) )

    # Count supports of single items (K=1)
    # and make a dictionary of frequent items (support >= threshold)
    # freqItemset = {itemset: support}
    freqItemset_k_1 = dict()
    for i in range(len(itemlist)):
        item = itemlist[i]
        n = 0
        for dictVal in list(transactionDict.values()):
            if item in dictVal:
                n += 1            
        if n >= threshold:
            freqItemset_k_1[item] = n
    print(freqItemset_k_1)                      
    allFreqItemsetDicts.append(freqItemset_k_1)
    
    while (len(freqItemset_k_1) > 0):
        K = K + 1                     # Increase level K = num of items in an itemset
        print('\n' + '== Current level = ' + str(K) )
        
        # 'keysets' are itemsets generated based on the previous frequent itemsets
        keysets = Apriori_gen_nextItemsets(freqItemset_k_1, K)       

        # Count supports of level K itemsets
        # and make a dictionary of frequent itemsets (support >= threshold)
        freqItemset_k = dict()
        for i in range(len(keysets)):
            items = keysets[i]
            n = 0
            for dictVal in list(transactionDict.values()):
                indic = list(map(lambda x: x in dictVal, items))
                if all(indic):
                    n += 1
            if n >= threshold:
                freqItemset_k[tuple(items)] = n
                
        print(freqItemset_k)        
        if len(freqItemset_k) > 0:
            allFreqItemsetDicts.append(freqItemset_k)
        freqItemset_k_1 = freqItemset_k
     
    print('Last level = ' + str(K-1) )
    return allFreqItemsetDicts
```


```python
def Apriori_gen_nextItemsets(freqItemset_k_1, K):
    """
    Generate next level itemsets.
    """     
    # Extract unique singletons in freqItemset_k_1 keys
    if (K == 2):
        singletons = list(sorted({val for val in list(freqItemset_k_1.keys())}))         
    if (K >= 3):
        singletons = list(sorted({ele for val in list(freqItemset_k_1.keys()) for ele in val}))  
    
    # Generate new itemsets with K items
    keysets = list()
    
    for k in freqItemset_k_1.keys():
        k1 = set()     
        
        if isinstance(k, str):
            k1.add(k)
            
        if isinstance(k, tuple):
            for ele in k:
                k1.add(ele)
        
        for i in range(len(singletons)):
            s = singletons[i]
            k2 = k1.copy()        
            k2.add(s)
            if ((len(k2) == K)):
                indic = list()
                for k in keysets:
                    indic.append( (k != sorted(k2)) )       
                if all(indic):
                    keysets.append(sorted(k2))

    return keysets
```


```python
def findBaskets(itemList, transactionDict):
    """
    Find a basket key list containing an itemset.
    """
    basketKeys = list()
    B = len(transactionDict)
    for b in range(B):
        dictVal = list(transactionDict.values())[b]
        indic = list(map(lambda x: x in dictVal, itemList))
        if all(indic):
            key = list(transactionDict.keys())[b]
            basketKeys.append(key)
            
    return basketKeys
```

## An example

Suppose there are 150 items, numbered 1 to 150, and also 150 baskets, also numbered 1 to 150.
An item $i$ is in a basket $b$ if and only if $i$ divides $b$ with no remainder.

Then construct a transaction list.



```python
# Generate a dictionary with (basket, items) pairs
transactionDict = dict()

for b in range(1, 151):
    items = list()
    for i in range(1, b+1):
        if (b % i == 0):
            items.append(str(i))
    transactionDict[b] = items
```

The full list of transactions or baskets is as follows. 


```python
# Baskets
transactionDict
```




    {1: ['1'],
     2: ['1', '2'],
     3: ['1', '3'],
     4: ['1', '2', '4'],
     5: ['1', '5'],
     6: ['1', '2', '3', '6'],
     7: ['1', '7'],
     8: ['1', '2', '4', '8'],
     9: ['1', '3', '9'],
     10: ['1', '2', '5', '10'],
     11: ['1', '11'],
     12: ['1', '2', '3', '4', '6', '12'],
     13: ['1', '13'],
     14: ['1', '2', '7', '14'],
     15: ['1', '3', '5', '15'],
     16: ['1', '2', '4', '8', '16'],
     17: ['1', '17'],
     18: ['1', '2', '3', '6', '9', '18'],
     19: ['1', '19'],
     20: ['1', '2', '4', '5', '10', '20'],
     21: ['1', '3', '7', '21'],
     22: ['1', '2', '11', '22'],
     23: ['1', '23'],
     24: ['1', '2', '3', '4', '6', '8', '12', '24'],
     25: ['1', '5', '25'],
     26: ['1', '2', '13', '26'],
     27: ['1', '3', '9', '27'],
     28: ['1', '2', '4', '7', '14', '28'],
     29: ['1', '29'],
     30: ['1', '2', '3', '5', '6', '10', '15', '30'],
     31: ['1', '31'],
     32: ['1', '2', '4', '8', '16', '32'],
     33: ['1', '3', '11', '33'],
     34: ['1', '2', '17', '34'],
     35: ['1', '5', '7', '35'],
     36: ['1', '2', '3', '4', '6', '9', '12', '18', '36'],
     37: ['1', '37'],
     38: ['1', '2', '19', '38'],
     39: ['1', '3', '13', '39'],
     40: ['1', '2', '4', '5', '8', '10', '20', '40'],
     41: ['1', '41'],
     42: ['1', '2', '3', '6', '7', '14', '21', '42'],
     43: ['1', '43'],
     44: ['1', '2', '4', '11', '22', '44'],
     45: ['1', '3', '5', '9', '15', '45'],
     46: ['1', '2', '23', '46'],
     47: ['1', '47'],
     48: ['1', '2', '3', '4', '6', '8', '12', '16', '24', '48'],
     49: ['1', '7', '49'],
     50: ['1', '2', '5', '10', '25', '50'],
     51: ['1', '3', '17', '51'],
     52: ['1', '2', '4', '13', '26', '52'],
     53: ['1', '53'],
     54: ['1', '2', '3', '6', '9', '18', '27', '54'],
     55: ['1', '5', '11', '55'],
     56: ['1', '2', '4', '7', '8', '14', '28', '56'],
     57: ['1', '3', '19', '57'],
     58: ['1', '2', '29', '58'],
     59: ['1', '59'],
     60: ['1', '2', '3', '4', '5', '6', '10', '12', '15', '20', '30', '60'],
     61: ['1', '61'],
     62: ['1', '2', '31', '62'],
     63: ['1', '3', '7', '9', '21', '63'],
     64: ['1', '2', '4', '8', '16', '32', '64'],
     65: ['1', '5', '13', '65'],
     66: ['1', '2', '3', '6', '11', '22', '33', '66'],
     67: ['1', '67'],
     68: ['1', '2', '4', '17', '34', '68'],
     69: ['1', '3', '23', '69'],
     70: ['1', '2', '5', '7', '10', '14', '35', '70'],
     71: ['1', '71'],
     72: ['1', '2', '3', '4', '6', '8', '9', '12', '18', '24', '36', '72'],
     73: ['1', '73'],
     74: ['1', '2', '37', '74'],
     75: ['1', '3', '5', '15', '25', '75'],
     76: ['1', '2', '4', '19', '38', '76'],
     77: ['1', '7', '11', '77'],
     78: ['1', '2', '3', '6', '13', '26', '39', '78'],
     79: ['1', '79'],
     80: ['1', '2', '4', '5', '8', '10', '16', '20', '40', '80'],
     81: ['1', '3', '9', '27', '81'],
     82: ['1', '2', '41', '82'],
     83: ['1', '83'],
     84: ['1', '2', '3', '4', '6', '7', '12', '14', '21', '28', '42', '84'],
     85: ['1', '5', '17', '85'],
     86: ['1', '2', '43', '86'],
     87: ['1', '3', '29', '87'],
     88: ['1', '2', '4', '8', '11', '22', '44', '88'],
     89: ['1', '89'],
     90: ['1', '2', '3', '5', '6', '9', '10', '15', '18', '30', '45', '90'],
     91: ['1', '7', '13', '91'],
     92: ['1', '2', '4', '23', '46', '92'],
     93: ['1', '3', '31', '93'],
     94: ['1', '2', '47', '94'],
     95: ['1', '5', '19', '95'],
     96: ['1', '2', '3', '4', '6', '8', '12', '16', '24', '32', '48', '96'],
     97: ['1', '97'],
     98: ['1', '2', '7', '14', '49', '98'],
     99: ['1', '3', '9', '11', '33', '99'],
     100: ['1', '2', '4', '5', '10', '20', '25', '50', '100'],
     101: ['1', '101'],
     102: ['1', '2', '3', '6', '17', '34', '51', '102'],
     103: ['1', '103'],
     104: ['1', '2', '4', '8', '13', '26', '52', '104'],
     105: ['1', '3', '5', '7', '15', '21', '35', '105'],
     106: ['1', '2', '53', '106'],
     107: ['1', '107'],
     108: ['1', '2', '3', '4', '6', '9', '12', '18', '27', '36', '54', '108'],
     109: ['1', '109'],
     110: ['1', '2', '5', '10', '11', '22', '55', '110'],
     111: ['1', '3', '37', '111'],
     112: ['1', '2', '4', '7', '8', '14', '16', '28', '56', '112'],
     113: ['1', '113'],
     114: ['1', '2', '3', '6', '19', '38', '57', '114'],
     115: ['1', '5', '23', '115'],
     116: ['1', '2', '4', '29', '58', '116'],
     117: ['1', '3', '9', '13', '39', '117'],
     118: ['1', '2', '59', '118'],
     119: ['1', '7', '17', '119'],
     120: ['1',
      '2',
      '3',
      '4',
      '5',
      '6',
      '8',
      '10',
      '12',
      '15',
      '20',
      '24',
      '30',
      '40',
      '60',
      '120'],
     121: ['1', '11', '121'],
     122: ['1', '2', '61', '122'],
     123: ['1', '3', '41', '123'],
     124: ['1', '2', '4', '31', '62', '124'],
     125: ['1', '5', '25', '125'],
     126: ['1', '2', '3', '6', '7', '9', '14', '18', '21', '42', '63', '126'],
     127: ['1', '127'],
     128: ['1', '2', '4', '8', '16', '32', '64', '128'],
     129: ['1', '3', '43', '129'],
     130: ['1', '2', '5', '10', '13', '26', '65', '130'],
     131: ['1', '131'],
     132: ['1', '2', '3', '4', '6', '11', '12', '22', '33', '44', '66', '132'],
     133: ['1', '7', '19', '133'],
     134: ['1', '2', '67', '134'],
     135: ['1', '3', '5', '9', '15', '27', '45', '135'],
     136: ['1', '2', '4', '8', '17', '34', '68', '136'],
     137: ['1', '137'],
     138: ['1', '2', '3', '6', '23', '46', '69', '138'],
     139: ['1', '139'],
     140: ['1', '2', '4', '5', '7', '10', '14', '20', '28', '35', '70', '140'],
     141: ['1', '3', '47', '141'],
     142: ['1', '2', '71', '142'],
     143: ['1', '11', '13', '143'],
     144: ['1',
      '2',
      '3',
      '4',
      '6',
      '8',
      '9',
      '12',
      '16',
      '18',
      '24',
      '36',
      '48',
      '72',
      '144'],
     145: ['1', '5', '29', '145'],
     146: ['1', '2', '73', '146'],
     147: ['1', '3', '7', '21', '49', '147'],
     148: ['1', '2', '4', '37', '74', '148'],
     149: ['1', '149'],
     150: ['1', '2', '3', '5', '6', '10', '15', '25', '30', '50', '75', '150']}



#### Find all frequent itemsets with the support threshold $5$.


```python
# Run Apriori algorithm with the support threshold 5
out = Apriori(transactionDict, 5)
```

    == unique singletons ==
    ['1', '10', '100', '101', '102', '103', '104', '105', '106', '107', '108', '109', '11', '110', '111', '112', '113', '114', '115', '116', '117', '118', '119', '12', '120', '121', '122', '123', '124', '125', '126', '127', '128', '129', '13', '130', '131', '132', '133', '134', '135', '136', '137', '138', '139', '14', '140', '141', '142', '143', '144', '145', '146', '147', '148', '149', '15', '150', '16', '17', '18', '19', '2', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '3', '30', '31', '32', '33', '34', '35', '36', '37', '38', '39', '4', '40', '41', '42', '43', '44', '45', '46', '47', '48', '49', '5', '50', '51', '52', '53', '54', '55', '56', '57', '58', '59', '6', '60', '61', '62', '63', '64', '65', '66', '67', '68', '69', '7', '70', '71', '72', '73', '74', '75', '76', '77', '78', '79', '8', '80', '81', '82', '83', '84', '85', '86', '87', '88', '89', '9', '90', '91', '92', '93', '94', '95', '96', '97', '98', '99']
    
    == Current level = 1
    {'1': 150, '10': 15, '11': 13, '12': 12, '13': 11, '14': 10, '15': 10, '16': 9, '17': 8, '18': 8, '19': 7, '2': 75, '20': 7, '21': 7, '22': 6, '23': 6, '24': 6, '25': 6, '26': 5, '27': 5, '28': 5, '29': 5, '3': 50, '30': 5, '4': 37, '5': 30, '6': 25, '7': 21, '8': 18, '9': 16}
    
    == Current level = 2
    {('1', '10'): 15, ('1', '11'): 13, ('1', '12'): 12, ('1', '13'): 11, ('1', '14'): 10, ('1', '15'): 10, ('1', '16'): 9, ('1', '17'): 8, ('1', '18'): 8, ('1', '19'): 7, ('1', '2'): 75, ('1', '20'): 7, ('1', '21'): 7, ('1', '22'): 6, ('1', '23'): 6, ('1', '24'): 6, ('1', '25'): 6, ('1', '26'): 5, ('1', '27'): 5, ('1', '28'): 5, ('1', '29'): 5, ('1', '3'): 50, ('1', '30'): 5, ('1', '4'): 37, ('1', '5'): 30, ('1', '6'): 25, ('1', '7'): 21, ('1', '8'): 18, ('1', '9'): 16, ('10', '15'): 5, ('10', '2'): 15, ('10', '20'): 7, ('10', '3'): 5, ('10', '30'): 5, ('10', '4'): 7, ('10', '5'): 15, ('10', '6'): 5, ('11', '2'): 6, ('11', '22'): 6, ('12', '2'): 12, ('12', '24'): 6, ('12', '3'): 12, ('12', '4'): 12, ('12', '6'): 12, ('12', '8'): 6, ('13', '2'): 5, ('13', '26'): 5, ('14', '2'): 10, ('14', '28'): 5, ('14', '4'): 5, ('14', '7'): 10, ('15', '2'): 5, ('15', '3'): 10, ('15', '30'): 5, ('15', '5'): 10, ('15', '6'): 5, ('16', '2'): 9, ('16', '4'): 9, ('16', '8'): 9, ('18', '2'): 8, ('18', '3'): 8, ('18', '6'): 8, ('18', '9'): 8, ('2', '20'): 7, ('2', '22'): 6, ('2', '24'): 6, ('2', '26'): 5, ('2', '28'): 5, ('2', '3'): 25, ('2', '30'): 5, ('2', '4'): 37, ('2', '5'): 15, ('2', '6'): 25, ('2', '7'): 10, ('2', '8'): 18, ('2', '9'): 8, ('20', '4'): 7, ('20', '5'): 7, ('21', '3'): 7, ('21', '7'): 7, ('24', '3'): 6, ('24', '4'): 6, ('24', '6'): 6, ('24', '8'): 6, ('25', '5'): 6, ('27', '3'): 5, ('27', '9'): 5, ('28', '4'): 5, ('28', '7'): 5, ('3', '30'): 5, ('3', '4'): 12, ('3', '5'): 10, ('3', '6'): 25, ('3', '7'): 7, ('3', '8'): 6, ('3', '9'): 16, ('30', '5'): 5, ('30', '6'): 5, ('4', '5'): 7, ('4', '6'): 12, ('4', '7'): 5, ('4', '8'): 18, ('5', '6'): 5, ('6', '8'): 6, ('6', '9'): 8}
    
    == Current level = 3
    {('1', '10', '15'): 5, ('1', '10', '2'): 15, ('1', '10', '20'): 7, ('1', '10', '3'): 5, ('1', '10', '30'): 5, ('1', '10', '4'): 7, ('1', '10', '5'): 15, ('1', '10', '6'): 5, ('1', '11', '2'): 6, ('1', '11', '22'): 6, ('1', '12', '2'): 12, ('1', '12', '24'): 6, ('1', '12', '3'): 12, ('1', '12', '4'): 12, ('1', '12', '6'): 12, ('1', '12', '8'): 6, ('1', '13', '2'): 5, ('1', '13', '26'): 5, ('1', '14', '2'): 10, ('1', '14', '28'): 5, ('1', '14', '4'): 5, ('1', '14', '7'): 10, ('1', '15', '2'): 5, ('1', '15', '3'): 10, ('1', '15', '30'): 5, ('1', '15', '5'): 10, ('1', '15', '6'): 5, ('1', '16', '2'): 9, ('1', '16', '4'): 9, ('1', '16', '8'): 9, ('1', '18', '2'): 8, ('1', '18', '3'): 8, ('1', '18', '6'): 8, ('1', '18', '9'): 8, ('1', '2', '20'): 7, ('1', '2', '22'): 6, ('1', '2', '24'): 6, ('1', '2', '26'): 5, ('1', '2', '28'): 5, ('1', '2', '3'): 25, ('1', '2', '30'): 5, ('1', '2', '4'): 37, ('1', '2', '5'): 15, ('1', '2', '6'): 25, ('1', '2', '7'): 10, ('1', '2', '8'): 18, ('1', '2', '9'): 8, ('1', '20', '4'): 7, ('1', '20', '5'): 7, ('1', '21', '3'): 7, ('1', '21', '7'): 7, ('1', '24', '3'): 6, ('1', '24', '4'): 6, ('1', '24', '6'): 6, ('1', '24', '8'): 6, ('1', '25', '5'): 6, ('1', '27', '3'): 5, ('1', '27', '9'): 5, ('1', '28', '4'): 5, ('1', '28', '7'): 5, ('1', '3', '30'): 5, ('1', '3', '4'): 12, ('1', '3', '5'): 10, ('1', '3', '6'): 25, ('1', '3', '7'): 7, ('1', '3', '8'): 6, ('1', '3', '9'): 16, ('1', '30', '5'): 5, ('1', '30', '6'): 5, ('1', '4', '5'): 7, ('1', '4', '6'): 12, ('1', '4', '7'): 5, ('1', '4', '8'): 18, ('1', '5', '6'): 5, ('1', '6', '8'): 6, ('1', '6', '9'): 8, ('10', '15', '2'): 5, ('10', '15', '3'): 5, ('10', '15', '30'): 5, ('10', '15', '5'): 5, ('10', '15', '6'): 5, ('10', '2', '20'): 7, ('10', '2', '3'): 5, ('10', '2', '30'): 5, ('10', '2', '4'): 7, ('10', '2', '5'): 15, ('10', '2', '6'): 5, ('10', '20', '4'): 7, ('10', '20', '5'): 7, ('10', '3', '30'): 5, ('10', '3', '5'): 5, ('10', '3', '6'): 5, ('10', '30', '5'): 5, ('10', '30', '6'): 5, ('10', '4', '5'): 7, ('10', '5', '6'): 5, ('11', '2', '22'): 6, ('12', '2', '24'): 6, ('12', '2', '3'): 12, ('12', '2', '4'): 12, ('12', '2', '6'): 12, ('12', '2', '8'): 6, ('12', '24', '3'): 6, ('12', '24', '4'): 6, ('12', '24', '6'): 6, ('12', '24', '8'): 6, ('12', '3', '4'): 12, ('12', '3', '6'): 12, ('12', '3', '8'): 6, ('12', '4', '6'): 12, ('12', '4', '8'): 6, ('12', '6', '8'): 6, ('13', '2', '26'): 5, ('14', '2', '28'): 5, ('14', '2', '4'): 5, ('14', '2', '7'): 10, ('14', '28', '4'): 5, ('14', '28', '7'): 5, ('14', '4', '7'): 5, ('15', '2', '3'): 5, ('15', '2', '30'): 5, ('15', '2', '5'): 5, ('15', '2', '6'): 5, ('15', '3', '30'): 5, ('15', '3', '5'): 10, ('15', '3', '6'): 5, ('15', '30', '5'): 5, ('15', '30', '6'): 5, ('15', '5', '6'): 5, ('16', '2', '4'): 9, ('16', '2', '8'): 9, ('16', '4', '8'): 9, ('18', '2', '3'): 8, ('18', '2', '6'): 8, ('18', '2', '9'): 8, ('18', '3', '6'): 8, ('18', '3', '9'): 8, ('18', '6', '9'): 8, ('2', '20', '4'): 7, ('2', '20', '5'): 7, ('2', '24', '3'): 6, ('2', '24', '4'): 6, ('2', '24', '6'): 6, ('2', '24', '8'): 6, ('2', '28', '4'): 5, ('2', '28', '7'): 5, ('2', '3', '30'): 5, ('2', '3', '4'): 12, ('2', '3', '5'): 5, ('2', '3', '6'): 25, ('2', '3', '8'): 6, ('2', '3', '9'): 8, ('2', '30', '5'): 5, ('2', '30', '6'): 5, ('2', '4', '5'): 7, ('2', '4', '6'): 12, ('2', '4', '7'): 5, ('2', '4', '8'): 18, ('2', '5', '6'): 5, ('2', '6', '8'): 6, ('2', '6', '9'): 8, ('20', '4', '5'): 7, ('21', '3', '7'): 7, ('24', '3', '4'): 6, ('24', '3', '6'): 6, ('24', '3', '8'): 6, ('24', '4', '6'): 6, ('24', '4', '8'): 6, ('24', '6', '8'): 6, ('27', '3', '9'): 5, ('28', '4', '7'): 5, ('3', '30', '5'): 5, ('3', '30', '6'): 5, ('3', '4', '6'): 12, ('3', '4', '8'): 6, ('3', '5', '6'): 5, ('3', '6', '8'): 6, ('3', '6', '9'): 8, ('30', '5', '6'): 5, ('4', '6', '8'): 6}
    
    == Current level = 4
    {('1', '10', '15', '2'): 5, ('1', '10', '15', '3'): 5, ('1', '10', '15', '30'): 5, ('1', '10', '15', '5'): 5, ('1', '10', '15', '6'): 5, ('1', '10', '2', '20'): 7, ('1', '10', '2', '3'): 5, ('1', '10', '2', '30'): 5, ('1', '10', '2', '4'): 7, ('1', '10', '2', '5'): 15, ('1', '10', '2', '6'): 5, ('1', '10', '20', '4'): 7, ('1', '10', '20', '5'): 7, ('1', '10', '3', '30'): 5, ('1', '10', '3', '5'): 5, ('1', '10', '3', '6'): 5, ('1', '10', '30', '5'): 5, ('1', '10', '30', '6'): 5, ('1', '10', '4', '5'): 7, ('1', '10', '5', '6'): 5, ('1', '11', '2', '22'): 6, ('1', '12', '2', '24'): 6, ('1', '12', '2', '3'): 12, ('1', '12', '2', '4'): 12, ('1', '12', '2', '6'): 12, ('1', '12', '2', '8'): 6, ('1', '12', '24', '3'): 6, ('1', '12', '24', '4'): 6, ('1', '12', '24', '6'): 6, ('1', '12', '24', '8'): 6, ('1', '12', '3', '4'): 12, ('1', '12', '3', '6'): 12, ('1', '12', '3', '8'): 6, ('1', '12', '4', '6'): 12, ('1', '12', '4', '8'): 6, ('1', '12', '6', '8'): 6, ('1', '13', '2', '26'): 5, ('1', '14', '2', '28'): 5, ('1', '14', '2', '4'): 5, ('1', '14', '2', '7'): 10, ('1', '14', '28', '4'): 5, ('1', '14', '28', '7'): 5, ('1', '14', '4', '7'): 5, ('1', '15', '2', '3'): 5, ('1', '15', '2', '30'): 5, ('1', '15', '2', '5'): 5, ('1', '15', '2', '6'): 5, ('1', '15', '3', '30'): 5, ('1', '15', '3', '5'): 10, ('1', '15', '3', '6'): 5, ('1', '15', '30', '5'): 5, ('1', '15', '30', '6'): 5, ('1', '15', '5', '6'): 5, ('1', '16', '2', '4'): 9, ('1', '16', '2', '8'): 9, ('1', '16', '4', '8'): 9, ('1', '18', '2', '3'): 8, ('1', '18', '2', '6'): 8, ('1', '18', '2', '9'): 8, ('1', '18', '3', '6'): 8, ('1', '18', '3', '9'): 8, ('1', '18', '6', '9'): 8, ('1', '2', '20', '4'): 7, ('1', '2', '20', '5'): 7, ('1', '2', '24', '3'): 6, ('1', '2', '24', '4'): 6, ('1', '2', '24', '6'): 6, ('1', '2', '24', '8'): 6, ('1', '2', '28', '4'): 5, ('1', '2', '28', '7'): 5, ('1', '2', '3', '30'): 5, ('1', '2', '3', '4'): 12, ('1', '2', '3', '5'): 5, ('1', '2', '3', '6'): 25, ('1', '2', '3', '8'): 6, ('1', '2', '3', '9'): 8, ('1', '2', '30', '5'): 5, ('1', '2', '30', '6'): 5, ('1', '2', '4', '5'): 7, ('1', '2', '4', '6'): 12, ('1', '2', '4', '7'): 5, ('1', '2', '4', '8'): 18, ('1', '2', '5', '6'): 5, ('1', '2', '6', '8'): 6, ('1', '2', '6', '9'): 8, ('1', '20', '4', '5'): 7, ('1', '21', '3', '7'): 7, ('1', '24', '3', '4'): 6, ('1', '24', '3', '6'): 6, ('1', '24', '3', '8'): 6, ('1', '24', '4', '6'): 6, ('1', '24', '4', '8'): 6, ('1', '24', '6', '8'): 6, ('1', '27', '3', '9'): 5, ('1', '28', '4', '7'): 5, ('1', '3', '30', '5'): 5, ('1', '3', '30', '6'): 5, ('1', '3', '4', '6'): 12, ('1', '3', '4', '8'): 6, ('1', '3', '5', '6'): 5, ('1', '3', '6', '8'): 6, ('1', '3', '6', '9'): 8, ('1', '30', '5', '6'): 5, ('1', '4', '6', '8'): 6, ('10', '15', '2', '3'): 5, ('10', '15', '2', '30'): 5, ('10', '15', '2', '5'): 5, ('10', '15', '2', '6'): 5, ('10', '15', '3', '30'): 5, ('10', '15', '3', '5'): 5, ('10', '15', '3', '6'): 5, ('10', '15', '30', '5'): 5, ('10', '15', '30', '6'): 5, ('10', '15', '5', '6'): 5, ('10', '2', '20', '4'): 7, ('10', '2', '20', '5'): 7, ('10', '2', '3', '30'): 5, ('10', '2', '3', '5'): 5, ('10', '2', '3', '6'): 5, ('10', '2', '30', '5'): 5, ('10', '2', '30', '6'): 5, ('10', '2', '4', '5'): 7, ('10', '2', '5', '6'): 5, ('10', '20', '4', '5'): 7, ('10', '3', '30', '5'): 5, ('10', '3', '30', '6'): 5, ('10', '3', '5', '6'): 5, ('10', '30', '5', '6'): 5, ('12', '2', '24', '3'): 6, ('12', '2', '24', '4'): 6, ('12', '2', '24', '6'): 6, ('12', '2', '24', '8'): 6, ('12', '2', '3', '4'): 12, ('12', '2', '3', '6'): 12, ('12', '2', '3', '8'): 6, ('12', '2', '4', '6'): 12, ('12', '2', '4', '8'): 6, ('12', '2', '6', '8'): 6, ('12', '24', '3', '4'): 6, ('12', '24', '3', '6'): 6, ('12', '24', '3', '8'): 6, ('12', '24', '4', '6'): 6, ('12', '24', '4', '8'): 6, ('12', '24', '6', '8'): 6, ('12', '3', '4', '6'): 12, ('12', '3', '4', '8'): 6, ('12', '3', '6', '8'): 6, ('12', '4', '6', '8'): 6, ('14', '2', '28', '4'): 5, ('14', '2', '28', '7'): 5, ('14', '2', '4', '7'): 5, ('14', '28', '4', '7'): 5, ('15', '2', '3', '30'): 5, ('15', '2', '3', '5'): 5, ('15', '2', '3', '6'): 5, ('15', '2', '30', '5'): 5, ('15', '2', '30', '6'): 5, ('15', '2', '5', '6'): 5, ('15', '3', '30', '5'): 5, ('15', '3', '30', '6'): 5, ('15', '3', '5', '6'): 5, ('15', '30', '5', '6'): 5, ('16', '2', '4', '8'): 9, ('18', '2', '3', '6'): 8, ('18', '2', '3', '9'): 8, ('18', '2', '6', '9'): 8, ('18', '3', '6', '9'): 8, ('2', '20', '4', '5'): 7, ('2', '24', '3', '4'): 6, ('2', '24', '3', '6'): 6, ('2', '24', '3', '8'): 6, ('2', '24', '4', '6'): 6, ('2', '24', '4', '8'): 6, ('2', '24', '6', '8'): 6, ('2', '28', '4', '7'): 5, ('2', '3', '30', '5'): 5, ('2', '3', '30', '6'): 5, ('2', '3', '4', '6'): 12, ('2', '3', '4', '8'): 6, ('2', '3', '5', '6'): 5, ('2', '3', '6', '8'): 6, ('2', '3', '6', '9'): 8, ('2', '30', '5', '6'): 5, ('2', '4', '6', '8'): 6, ('24', '3', '4', '6'): 6, ('24', '3', '4', '8'): 6, ('24', '3', '6', '8'): 6, ('24', '4', '6', '8'): 6, ('3', '30', '5', '6'): 5, ('3', '4', '6', '8'): 6}
    
    == Current level = 5
    {('1', '10', '15', '2', '3'): 5, ('1', '10', '15', '2', '30'): 5, ('1', '10', '15', '2', '5'): 5, ('1', '10', '15', '2', '6'): 5, ('1', '10', '15', '3', '30'): 5, ('1', '10', '15', '3', '5'): 5, ('1', '10', '15', '3', '6'): 5, ('1', '10', '15', '30', '5'): 5, ('1', '10', '15', '30', '6'): 5, ('1', '10', '15', '5', '6'): 5, ('1', '10', '2', '20', '4'): 7, ('1', '10', '2', '20', '5'): 7, ('1', '10', '2', '3', '30'): 5, ('1', '10', '2', '3', '5'): 5, ('1', '10', '2', '3', '6'): 5, ('1', '10', '2', '30', '5'): 5, ('1', '10', '2', '30', '6'): 5, ('1', '10', '2', '4', '5'): 7, ('1', '10', '2', '5', '6'): 5, ('1', '10', '20', '4', '5'): 7, ('1', '10', '3', '30', '5'): 5, ('1', '10', '3', '30', '6'): 5, ('1', '10', '3', '5', '6'): 5, ('1', '10', '30', '5', '6'): 5, ('1', '12', '2', '24', '3'): 6, ('1', '12', '2', '24', '4'): 6, ('1', '12', '2', '24', '6'): 6, ('1', '12', '2', '24', '8'): 6, ('1', '12', '2', '3', '4'): 12, ('1', '12', '2', '3', '6'): 12, ('1', '12', '2', '3', '8'): 6, ('1', '12', '2', '4', '6'): 12, ('1', '12', '2', '4', '8'): 6, ('1', '12', '2', '6', '8'): 6, ('1', '12', '24', '3', '4'): 6, ('1', '12', '24', '3', '6'): 6, ('1', '12', '24', '3', '8'): 6, ('1', '12', '24', '4', '6'): 6, ('1', '12', '24', '4', '8'): 6, ('1', '12', '24', '6', '8'): 6, ('1', '12', '3', '4', '6'): 12, ('1', '12', '3', '4', '8'): 6, ('1', '12', '3', '6', '8'): 6, ('1', '12', '4', '6', '8'): 6, ('1', '14', '2', '28', '4'): 5, ('1', '14', '2', '28', '7'): 5, ('1', '14', '2', '4', '7'): 5, ('1', '14', '28', '4', '7'): 5, ('1', '15', '2', '3', '30'): 5, ('1', '15', '2', '3', '5'): 5, ('1', '15', '2', '3', '6'): 5, ('1', '15', '2', '30', '5'): 5, ('1', '15', '2', '30', '6'): 5, ('1', '15', '2', '5', '6'): 5, ('1', '15', '3', '30', '5'): 5, ('1', '15', '3', '30', '6'): 5, ('1', '15', '3', '5', '6'): 5, ('1', '15', '30', '5', '6'): 5, ('1', '16', '2', '4', '8'): 9, ('1', '18', '2', '3', '6'): 8, ('1', '18', '2', '3', '9'): 8, ('1', '18', '2', '6', '9'): 8, ('1', '18', '3', '6', '9'): 8, ('1', '2', '20', '4', '5'): 7, ('1', '2', '24', '3', '4'): 6, ('1', '2', '24', '3', '6'): 6, ('1', '2', '24', '3', '8'): 6, ('1', '2', '24', '4', '6'): 6, ('1', '2', '24', '4', '8'): 6, ('1', '2', '24', '6', '8'): 6, ('1', '2', '28', '4', '7'): 5, ('1', '2', '3', '30', '5'): 5, ('1', '2', '3', '30', '6'): 5, ('1', '2', '3', '4', '6'): 12, ('1', '2', '3', '4', '8'): 6, ('1', '2', '3', '5', '6'): 5, ('1', '2', '3', '6', '8'): 6, ('1', '2', '3', '6', '9'): 8, ('1', '2', '30', '5', '6'): 5, ('1', '2', '4', '6', '8'): 6, ('1', '24', '3', '4', '6'): 6, ('1', '24', '3', '4', '8'): 6, ('1', '24', '3', '6', '8'): 6, ('1', '24', '4', '6', '8'): 6, ('1', '3', '30', '5', '6'): 5, ('1', '3', '4', '6', '8'): 6, ('10', '15', '2', '3', '30'): 5, ('10', '15', '2', '3', '5'): 5, ('10', '15', '2', '3', '6'): 5, ('10', '15', '2', '30', '5'): 5, ('10', '15', '2', '30', '6'): 5, ('10', '15', '2', '5', '6'): 5, ('10', '15', '3', '30', '5'): 5, ('10', '15', '3', '30', '6'): 5, ('10', '15', '3', '5', '6'): 5, ('10', '15', '30', '5', '6'): 5, ('10', '2', '20', '4', '5'): 7, ('10', '2', '3', '30', '5'): 5, ('10', '2', '3', '30', '6'): 5, ('10', '2', '3', '5', '6'): 5, ('10', '2', '30', '5', '6'): 5, ('10', '3', '30', '5', '6'): 5, ('12', '2', '24', '3', '4'): 6, ('12', '2', '24', '3', '6'): 6, ('12', '2', '24', '3', '8'): 6, ('12', '2', '24', '4', '6'): 6, ('12', '2', '24', '4', '8'): 6, ('12', '2', '24', '6', '8'): 6, ('12', '2', '3', '4', '6'): 12, ('12', '2', '3', '4', '8'): 6, ('12', '2', '3', '6', '8'): 6, ('12', '2', '4', '6', '8'): 6, ('12', '24', '3', '4', '6'): 6, ('12', '24', '3', '4', '8'): 6, ('12', '24', '3', '6', '8'): 6, ('12', '24', '4', '6', '8'): 6, ('12', '3', '4', '6', '8'): 6, ('14', '2', '28', '4', '7'): 5, ('15', '2', '3', '30', '5'): 5, ('15', '2', '3', '30', '6'): 5, ('15', '2', '3', '5', '6'): 5, ('15', '2', '30', '5', '6'): 5, ('15', '3', '30', '5', '6'): 5, ('18', '2', '3', '6', '9'): 8, ('2', '24', '3', '4', '6'): 6, ('2', '24', '3', '4', '8'): 6, ('2', '24', '3', '6', '8'): 6, ('2', '24', '4', '6', '8'): 6, ('2', '3', '30', '5', '6'): 5, ('2', '3', '4', '6', '8'): 6, ('24', '3', '4', '6', '8'): 6}
    
    == Current level = 6
    {('1', '10', '15', '2', '3', '30'): 5, ('1', '10', '15', '2', '3', '5'): 5, ('1', '10', '15', '2', '3', '6'): 5, ('1', '10', '15', '2', '30', '5'): 5, ('1', '10', '15', '2', '30', '6'): 5, ('1', '10', '15', '2', '5', '6'): 5, ('1', '10', '15', '3', '30', '5'): 5, ('1', '10', '15', '3', '30', '6'): 5, ('1', '10', '15', '3', '5', '6'): 5, ('1', '10', '15', '30', '5', '6'): 5, ('1', '10', '2', '20', '4', '5'): 7, ('1', '10', '2', '3', '30', '5'): 5, ('1', '10', '2', '3', '30', '6'): 5, ('1', '10', '2', '3', '5', '6'): 5, ('1', '10', '2', '30', '5', '6'): 5, ('1', '10', '3', '30', '5', '6'): 5, ('1', '12', '2', '24', '3', '4'): 6, ('1', '12', '2', '24', '3', '6'): 6, ('1', '12', '2', '24', '3', '8'): 6, ('1', '12', '2', '24', '4', '6'): 6, ('1', '12', '2', '24', '4', '8'): 6, ('1', '12', '2', '24', '6', '8'): 6, ('1', '12', '2', '3', '4', '6'): 12, ('1', '12', '2', '3', '4', '8'): 6, ('1', '12', '2', '3', '6', '8'): 6, ('1', '12', '2', '4', '6', '8'): 6, ('1', '12', '24', '3', '4', '6'): 6, ('1', '12', '24', '3', '4', '8'): 6, ('1', '12', '24', '3', '6', '8'): 6, ('1', '12', '24', '4', '6', '8'): 6, ('1', '12', '3', '4', '6', '8'): 6, ('1', '14', '2', '28', '4', '7'): 5, ('1', '15', '2', '3', '30', '5'): 5, ('1', '15', '2', '3', '30', '6'): 5, ('1', '15', '2', '3', '5', '6'): 5, ('1', '15', '2', '30', '5', '6'): 5, ('1', '15', '3', '30', '5', '6'): 5, ('1', '18', '2', '3', '6', '9'): 8, ('1', '2', '24', '3', '4', '6'): 6, ('1', '2', '24', '3', '4', '8'): 6, ('1', '2', '24', '3', '6', '8'): 6, ('1', '2', '24', '4', '6', '8'): 6, ('1', '2', '3', '30', '5', '6'): 5, ('1', '2', '3', '4', '6', '8'): 6, ('1', '24', '3', '4', '6', '8'): 6, ('10', '15', '2', '3', '30', '5'): 5, ('10', '15', '2', '3', '30', '6'): 5, ('10', '15', '2', '3', '5', '6'): 5, ('10', '15', '2', '30', '5', '6'): 5, ('10', '15', '3', '30', '5', '6'): 5, ('10', '2', '3', '30', '5', '6'): 5, ('12', '2', '24', '3', '4', '6'): 6, ('12', '2', '24', '3', '4', '8'): 6, ('12', '2', '24', '3', '6', '8'): 6, ('12', '2', '24', '4', '6', '8'): 6, ('12', '2', '3', '4', '6', '8'): 6, ('12', '24', '3', '4', '6', '8'): 6, ('15', '2', '3', '30', '5', '6'): 5, ('2', '24', '3', '4', '6', '8'): 6}
    
    == Current level = 7
    {('1', '10', '15', '2', '3', '30', '5'): 5, ('1', '10', '15', '2', '3', '30', '6'): 5, ('1', '10', '15', '2', '3', '5', '6'): 5, ('1', '10', '15', '2', '30', '5', '6'): 5, ('1', '10', '15', '3', '30', '5', '6'): 5, ('1', '10', '2', '3', '30', '5', '6'): 5, ('1', '12', '2', '24', '3', '4', '6'): 6, ('1', '12', '2', '24', '3', '4', '8'): 6, ('1', '12', '2', '24', '3', '6', '8'): 6, ('1', '12', '2', '24', '4', '6', '8'): 6, ('1', '12', '2', '3', '4', '6', '8'): 6, ('1', '12', '24', '3', '4', '6', '8'): 6, ('1', '15', '2', '3', '30', '5', '6'): 5, ('1', '2', '24', '3', '4', '6', '8'): 6, ('10', '15', '2', '3', '30', '5', '6'): 5, ('12', '2', '24', '3', '4', '6', '8'): 6}
    
    == Current level = 8
    {('1', '10', '15', '2', '3', '30', '5', '6'): 5, ('1', '12', '2', '24', '3', '4', '6', '8'): 6}
    
    == Current level = 9
    {}
    Last level = 8
    

#### Find frequent single items that appear at least five baskets.


```python
# Extract frequent items (K=1)
frequentItems =  list(out[0].keys())
frequentItems2 = np.array([eval(i) for i in frequentItems])
frequentItems2.sort()

ans = str()
for i in frequentItems2:
    ans = ans + str(i) + ','
ans    
```




    '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,'



#### Find basket numbers containing a frequent itemset $(5, 20)$.


```python
items = ['5', '20']
findBaskets(items, transactionDict)
```




    [20, 40, 60, 80, 100, 120, 140]



#### Find basket numbers containing a frequent triple $(1, 2, 10)$.


```python
findBaskets(['1', '10', '2'], transactionDict)
```




    [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150]

