####################
#
# Haotian He UW NetID haotianh
# LING473 Project 4
#
####################
 
Universe   = vanilla
 
Environment = PATH=/usr/local/bin:/usr/bin:/bin:/condor/bin:/opt/ANT/bin;LC_ALL=en_US.UTF-8
 
Executable  = run.sh
Arguments   = ./content/targets /dropbox/12-13/473/project4/hg19-GRCh37/ 
Log         = project4.log
Output      = output.txt
Error       = project4.err
Notification=Error
Queue