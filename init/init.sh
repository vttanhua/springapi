#!/bin/sh
awslocal sqs create-queue --queue-name stage-springapi-todo-sharing-queue

awslocal ses verify-email-identity --email-address vttanhua@icloud.com
awslocal ses verify-email-identity --email-address vttanhua+2@icloud.com
awslocal ses verify-email-identity --email-address vttanhua+3@icloud.com

awslocal s3api create-bucket --bucket stage-springapi-todoattachments