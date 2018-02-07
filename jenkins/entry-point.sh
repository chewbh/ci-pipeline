#!/bin/bash
set -e

echo "Genarate JENKINS SSH KEY"
source /usr/local/bin/generate-ssh-key.sh

echo "start JENKINS"
# if `docker run` first argument start with `--` the user is passing jenkins launcher arguments
if [[ $# -lt 1 ]] || [[ "$1" == "--"* ]]; then
    exec /sbin/tini -- /usr/local/bin/jenkins.sh "$@"
fi
exec "$@"