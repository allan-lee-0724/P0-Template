# This part is to extract status code from the log file. 100-400 codes represent success. 500 codes represent failure
httpStatusCodes=$(grep Response: ./logs/rollingFile.log | cut -f 1 -d , | cut -f 2 -d '[' | cut -f 1 -d ' ')

# Setting up variables for number of successful attempts, failed attempts, and total attempts
numOfSuccess=0
numOfFailure=0
totalAttempts=0

for codes in $httpStatusCodes
do
	if [ $codes -ge 500 ]
	then
		((numOfFailure++))
	else
		((numOfSuccess++))
	fi

	((totalAttempts++))
done

# Setting up SLO target success rate and calculate actual success rate
targetRate=99.8
actualRateDecimal=$(echo "scale=3; $numOfSuccess / $totalAttempts" | bc)
avgRate=$(echo "scale=2; $actualRateDecimal * 100" | bc)

echo "AVERAGE SUCCESS RATE: $avgRate%"

# This part is to extract latencies from the log file
httpLatencies=$(grep Response: ./logs/rollingFile.log | cut -f 2 -d : | cut -f 2 -d , | cut -f 4 -d ' ')
totalLatency=0
numOfEntry=0

for latency in $httpLatencies
do
	totalLatency=$(echo "scale=3; $totalLatency + $latency" | bc)
	((numOfEntry++))
done

avgLatency=$(echo "scale=3; $totalLatency / $numOfEntry" | bc)
echo "AVERAGE LATENCY: $avgLatency ms"


