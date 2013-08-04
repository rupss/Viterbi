For CS161 (Algorithms), Spring 2013. 

USAGE: 
- Compile by calling make
- To pass in a directory with an input.txt as a parameter (as with the test cases and the run_all.sh script): ./Midterm <input file path>
- To read directly from "input.txt", comment out line 30 and uncomment line 31: ./Midterm or make run

For the midterm assignment, we had to implement the Viterbi algorithm (although we didn't know it at the time) using
dynamic programming. 

Assignment spec: http://stanford.edu/class/archive/cs/cs161/cs161.1136/hws/midterm.pdf

Problem: We're given a directed graph G that models a probabilistic process, starting from a uniform distribution over the vertices. Each Edge e_i has the form (a_i, b_i, l_i, p_i), meaning that if we are at vertex a_i, there is a probability of p_i of generating label l_i and moving to vertex b_i. Given a sequence of labels, the goal is to find the most likely starting point and path taken through graph G. 

Input format: 

First line: N M T L 

- N -- vertices can take on values from {0, ... , N}
- M -- there are m edges
- T -- the trace (second line) has T labels
- L -- labels can take on values from {0, ... , L}

Second line: int[] trace -- T space separated 0's and 1's. E.g if T = 5, the second line could be: 0 1 1 0 0

Each subsequent line encodes an Edge. Format: edge.from edge.to edge.label edge.weight

Output: Writes 2 files to the same directory as the input file. 
- output1.txt: contains the minimum weight of a path satisfying labels. 
- output2.txt: contains the vertices of a path that has the minimum weight. 
