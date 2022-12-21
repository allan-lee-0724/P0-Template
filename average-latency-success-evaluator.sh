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

# Check if average success rate meets target rate
if (($(echo "$avgRate >= $targetRate" | bc -l)))
then
	echo "TARGET SUCCESS RATE of $targetRate SATISFIED"
else
	echo "TARGET SUCCESS RATE of $targetRate NOT SATISFIED"
fi

# This part is to extract latencies from the log file
httpLatencies=$(grep Response: ./logs/rollingFile.log | cut -f 2 -d : | cut -f 2 -d , | cut -f 4 -d ' ')

# Setting up variables for total lantency and number of entries
totalLatency=0
numOfEntry=0

# Setting up SLO target latency
targetLatency=200
latencySuccess=0
latencyFail=0
totalAttempts=0

for latency in $httpLatencies
do
	if (($(echo "$latency <= $targetLatency" | bc -l))) 
	then
		((latencySuccess++))
		((totalAttempts++))
	else
		((latencyFail++))
		((totalAttempts++))
	fi

	totalLatency=$(echo "scale=3; $totalLatency + $latency" | bc)
	((numOfEntry++))
done

# Setting up SLO target latency and calculate average latency
avgLatency=$(echo "scale=3; $totalLatency / $numOfEntry" | bc)

echo "NUMBER OF ATTEMPTS SATISFYING SLO: $latencySuccess / $totalAttempts"
echo "AVERAGE LATENCY: $avgLatency ms"

# Check if average latency meets SLO
if(($(echo "$avgLatency <= $targetLatency" |bc -l)))
then
	echo "TARGET LATENCY of $targetLatency SATISFIED"
else
	echo "TARGET LATENCY OF $targetLatency NOT SATISFIED"
fi


