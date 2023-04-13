#!/usr/bin/env bash

echo 'Copy files'

scp -i ~/.ssh/id_rsa_makar \
    ../target/midoriya.war \
    administrator@130.193.48.115:~/midoriya

echo 'Restart server'

ssh -i ~/.ssh/id_rsa_makar administrator@130.193.48.115 << EOF
pgrep java | xargs kill -9
nohup java -jar ~/midoriya/midoriya.war > ~/midoriya/log.txt &
EOF

echo 'Finish'