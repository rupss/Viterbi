For CS161 (Algorithms), Spring 2013. 

For the midterm assignment, we had to implement the Viterbi algorithm (although we didn't know it at the time) using
dynamic programming. 

Assignment spec: http://stanford.edu/class/archive/cs/cs161/cs161.1136/hws/midterm.pdf

Input format: 

First line: N M T L 

N -- vertices can take on values from {0, ... , N}
M -- there are m edges
T -- the trace (second line) has T labels
L -- labels can take on values from {0, ... , L}

Second line: int[] trace
T space separated 0's and 1's. E.g if T = 5, the second line could be: 0 1 1 0 0

Each subsequent line: 
