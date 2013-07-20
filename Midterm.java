import java.io.*;
import java.util.*;

public class Midterm {

  public static class LP {
    public int node; 
    public double weight;

    //stores (node, weight) pairs to store in labels
    public LP(int n, double w) {
      node = n; 
      weight = w;       
    }
  }
  
  //Stores (weight sum, prev_node) pairs to store in V
  public static class SB {
    public double sum; 
    public int prev_node; 
    public SB(double s, int p) {
      sum = s; 
      prev_node = p; 
    }
  }

  public static void main(String[] args) {
    //INPUT 
    try {
      //System.out.println("ARG = " + args[0]);
      
      //Scanner s = new Scanner(new BufferedReader(new FileReader(args[0] + "/input.txt")));
      Scanner s = new Scanner(new BufferedReader(new FileReader("input.txt")));
      int N, M, T, L;
      N = s.nextInt(); M = s.nextInt(); T = s.nextInt(); L = s.nextInt();
      s.nextLine();
      int trace[] = Midterm.getTrace(s, T);
      //Midterm.printTrace(trace);
      
      SB V[][] = Midterm.makeV(N, T);
      //Midterm.printV(V, N, T);
      ArrayList< ArrayList< HashSet<LP> > > labels = Midterm.makeLabels(N, L, s);
      //Midterm.printLabels(labels, N, L);
      algorithm(trace, V, labels, N, T);
    }
    catch(Exception e) {
      System.out.println("Error with file reading in main");
    }

    /* // OUTPUT
          BufferedWriter out1 = new BufferedWriter(new FileWriter ("output1.txt"));
          BufferedWriter out2 = new BufferedWriter(new FileWriter ("output2.txt"));
          out1.write(Integer.toString(14) + "\n");
          out1.close();
          out2.write(Integer.toString(3) + "\n");
          out2.write(Integer.toString(2) + "\n");
          out2.write(Integer.toString(0) + "\n");
          out2.write(Integer.toString(4) + "\n");
          out2.write(Integer.toString(0) + "\n");
          out2.write(Integer.toString(3) + "\n");
          out2.close();
        } catch (Exception e) {} */
  }
  public static int[] getTrace(Scanner s, int T) {
    int trace[] = null; 
    try{
      // INPUT
      String trace_string = s.nextLine();
      trace = new int[T];
      Scanner ts = new Scanner(trace_string);
      int i = 0; 
      while (ts.hasNextInt()) {
        trace[i] = ts.nextInt();
        i++;
      }
    }
    catch (Exception e) {
      System.out.println("Error in getTrace");
    }
    return trace;
  }

  public static SB[][] makeV(int N, int T) {
    SB V[][] = new SB[N][T+1];
    for (int r = 0; r < N; r++) {
      for (int c = 0; c < T+1; c++) {
        double sum = 0; 
        if (c != 0) {
          sum = Double.POSITIVE_INFINITY;
        }
        V[r][c] = new SB(sum, -1);
        
      }
    }
    return V;
  }

  public static void printV(SB V[][], int N, int T) {
    System.out.println("V CONTENTS");
    for (int r = 0; r < N; r++) {
      String line = ""; 
      for (int c = 0; c < T+1; c++) {
        if (V[r][c].sum == Double.POSITIVE_INFINITY) {
          line += "I ";
        }
        else {
          line += "(" + V[r][c].sum + ", " + V[r][c].prev_node + ")  ";
        }
      }
      System.out.println(line);
    }
  }

  public static ArrayList< ArrayList< HashSet<LP> > > makeLabels(int N, int L, Scanner s) {
    ArrayList< ArrayList< HashSet<LP> > > labels = new ArrayList< ArrayList< HashSet<LP> > >(N);
    for (int i = 0; i < N; i++) {
      labels.add(new ArrayList< HashSet<LP> >(L));
      for (int j = 0; j < L; j++) {
        labels.get(i).add(null);
      }
    }
    //System.out.println(labels.get(0));
    while (s.hasNextLine()) {
      String line = s.nextLine();
      Scanner sc = new Scanner(line);
      //System.out.println("LINE = " + line);
      int from = sc.nextInt();
      int to = sc.nextInt();
      int label = sc.nextInt();
      int weight = sc.nextInt();
  
      if (labels.get(from).get(label) == null) {
        labels.get(from).set(label, new HashSet<LP>());
      }
      LP curr = new LP(to, weight);
      labels.get(from).get(label).add(curr);
      // System.out.println(curr.node + ", " + curr.weight);
    }

    return labels;
  }

  public static void printTrace(int trace[]) {
    String line = ""; 
    for (int i = 0; i < trace.length; i++) {
      line += trace[i] + " ";
    }
    System.out.println("Trace = " + line);
  }

  public static void printHashSet(HashSet<LP> s) {
    Iterator<LP> it = s.iterator();
    while (it.hasNext()) {
      LP l = it.next();
      String line = "(" + l.node + ", " + l.weight + ")"; 
      System.out.println(line);
    }
  }

  public static void printLabels(ArrayList< ArrayList< HashSet<LP> > > labels, int N, int L) {
    System.out.println("LABELS CONTENTS");
    // Midterm.printHashSet(labels.get(4).get(0));
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < L; j++) {
        System.out.println("labels[" + i + "][" + j + "]");
        if (labels.get(i).get(j) != null) {
          Midterm.printHashSet(labels.get(i).get(j));
        }
      }
    }
  }

  public static void algorithm(int trace[], SB V[][], ArrayList< ArrayList< HashSet<LP> > > labels, 
      int N, int T) {

    //System.out.println("Running algorithm...");
    for (int i = 1; i <= T; i++) {
      int curr_label = trace[i-1];
      for (int v = 0; v < N; v++) {
        HashSet<LP> possibles = labels.get(v).get(curr_label);
        if (possibles == null) {
          continue;
        }
        Iterator<LP> it = possibles.iterator();
        while (it.hasNext()) {
          LP curr = it.next();
          if (V[v][i-1].sum + curr.weight < V[curr.node][i].sum) {
            V[curr.node][i] = new SB(V[v][i-1].sum + curr.weight, v);
          }
          
        }
      }
    }
    //Midterm.printV(V, N, T);
    Midterm.getMinAndPath(V, N, T, labels, trace);
    //System.out.println("Algorithm complete");

  }

  public static void getMinAndPath(SB V[][], int N, int T, ArrayList< ArrayList< HashSet<LP> > > labels, int trace[]) {
    double min = Double.POSITIVE_INFINITY; 
    int v = -1; 
    for (int i = 0; i < N; i++) {
      if (V[i][T].sum < min) {
        min = V[i][T].sum;
        v = i; 
      }
    }
    writeFile1((int)min);
    //System.out.println("MIN WEIGHT = " + (int)min);
   // System.out.println("V = " + v);
    Midterm.tracePath(V, v, T, labels, min, trace);
  }
  
  public static void tracePath(SB V[][], int v, int T, ArrayList< ArrayList< HashSet<LP> > > labels, double min, int trace[]) {
    ArrayList<Integer> path = new ArrayList<Integer>(T); //stores the vertices in the path backwards (first vertex in actual
    //path is the last one in the arrayList)
    int curr = v; 
    path.add(v); 
    for (int t = T; t > 0; t--) {
      int temp = V[curr][t].prev_node;
      path.add(new Integer(temp));
      curr = temp;
    }
    //Midterm.printPath(path, T);
    //Midterm.verifyPath(path, T, labels, min, trace);
    writeFile2(path, T);
  }

  public static void verifyPath(ArrayList<Integer> path, int T, ArrayList< ArrayList< HashSet<LP> > > labels, double min, int trace[]) {
    
    double total = 0; 
    for (int i = T; i > 0; i--) {
      double found = Midterm.find(trace[T-i], path.get(i), path.get(i-1), labels); 
      if (found != -1) {
        total += found; 
      }
      else {
        System.out.println("ERROR: " + path.get(i) + " --> " + path.get(i-1) + "should be " + trace[T-i] + "but is not present.");
      }
    }
    if (total == min) {
      System.out.println("Verified OK");
    }
    else {
      System.out.println("ERROR: Weight should be " + min + "but is: " + total);
    }
  }

  public static double find(int curr_trace, int from, int to, ArrayList< ArrayList< HashSet<LP> > > labels) {
    HashSet<LP> set = labels.get(from).get(curr_trace);
    if (set == null) {
      return -1.0; 
    }
    Iterator<LP> it = set.iterator(); 
    while (it.hasNext()) {
      LP curr = it.next(); 
      if (curr.node == to) {
        return curr.weight; 
      }
    }
    return -1.0; 
  }
  
  public static void writeFile2(ArrayList<Integer> path, int T) {
    try {
      BufferedWriter out1 = new BufferedWriter(new FileWriter ("output2.txt"));
      for (int i = T; i >= 0; i--) {
        out1.write(path.get(i).toString() + "\n"); 
      }
      out1.close();
    }
    catch (Exception e) {
      System.out.println("Error in writeFile2");
    }
  }
  
  public static void printPath(ArrayList<Integer> path, int T) {
    System.out.println("PATH CONTENTS");
    for (int i = T; i >= 0 ; i--) {
      System.out.println(path.get(i));
    }
  }

  public static void writeFile1(int min) {
    try {
      BufferedWriter out1 = new BufferedWriter(new FileWriter ("output1.txt"));
      out1.write(Integer.toString(min) + "\n");
      out1.close();
    }
    catch (Exception e) {
      System.out.println("Error in writeFile1");
    }
  }

}
