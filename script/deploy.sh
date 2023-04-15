#!/usr/bin/env bash

echo 'Copy files'

scp -i ~/.ssh/id_rsa_makar \
    ../target/midoriya.war \
    administrator@host:~/midoriya

echo 'Restart server'

ssh -i ~/.ssh/id_rsa_makar administrator@host << EOF
pgrep java | xargs kill -9
nohup java -jar ~/midoriya/midoriya.war > ~/midoriya/log.txt &
EOF

echo 'Finish'
