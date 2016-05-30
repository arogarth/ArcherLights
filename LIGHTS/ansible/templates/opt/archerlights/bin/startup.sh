#!/bin/bash

# GPIO Ports to activate and use
# 17 = *RED*
# 27 = *YELLOW*
# 22 = *GREEN*
# 23 = *AB*
# 24 = *CD*
# 25 = ))HORN((
#  4 = RESERVE
# 18 = RESERVE

GPIO=(17 27 22 23 24 25 4 18)

for pin in ${GPIO[@]}; do
  echo ${pin}
  echo "${pin}" > /sys/class/gpio/export
  echo "out" > /sys/class/gpio/gpio${pin}/direction
  echo "1" > /sys/class/gpio/gpio${pin}/value
done

#echo "0" > /sys/class/gpio/gpio17/value
