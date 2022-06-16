#!/bin/sh
awslocal sqs create-queue --queue-name vttanhua-todo-sharing
awslocal ses verify-email-identity --email-address vttanhua@icloud.com