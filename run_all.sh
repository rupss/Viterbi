#!/bin/bash
 
src="../testcases/"
inp="/input.txt"
one="/output1.txt"
two="/output2.txt"
    
for dir in `ls "$src/"`
do
  echo "Processing directory $dir"
  path=$src$dir
  java Midterm $path
  echo "Diff-ing 1"
  o=$src$dir$one
  e="1errors"$dir".txt"
  diff "output1.txt" $o > $e
  echo "Diff 1 DONE"
  echo "Diff-ing 2"
   t=$src$dir$two
   e="2errors"$dir".txt"
  diff "output2.txt" $t > $e
done
