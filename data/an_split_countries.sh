#!/bin/bash

for country in $(cat countryInfo.txt | cut -f 1 | grep -v '^#')
do
	cat allCountries_purged.txt | grep -e "${country}\$" > ${country}_purged.txt
	echo "country ${country}"
done