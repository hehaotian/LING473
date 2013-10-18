####################
#
# Haotian He UW NetID haotianh
# LING473 Project 5
#
####################

Universe   = vanilla

Environment = PATH=/usr/local/bin:/usr/bin:/bin:/condor/bin:/opt/ANT/bin;LC_ALL=en_US.iso88591

Executable  = run.sh
Arguments   = train.txt /dropbox/12-13/473/project5/*
Log         = project5.log
Output      = output.txt
Error       = project5.err
Notification=Error
Queue