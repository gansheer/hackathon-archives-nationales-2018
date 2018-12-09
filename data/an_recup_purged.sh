#!/bin/bash

for file in $(ls *_purged.txt)
do
  cp /home/rocha/Documents/hackathon/${file} ./
done
