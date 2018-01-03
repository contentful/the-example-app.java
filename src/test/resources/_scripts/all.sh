#!/usr/bin/env bash
#
# This script will be used to regenerate all the canned test files, based on real requests.
#

export SPACE_ID="jnzexv31feqf"
export CDA_TOKEN="7c1c321a528a25c351c1ac5f53e6ddc6bcce0712ecebec60817f53b35dd3c42b"
export CPA_TOKEN="4310226db935f0e9b6b34fb9ce6611e2061abe1aab5297fa25bd52af5caa531a"

# loop through all folders
for FILE in *; do
  if [[ -d "${FILE}" ]]; then
    echo "${FILE}/"
    mkdir -p ../${FILE};

    for SCRIPT in ${FILE}/*.sh; do
        echo -n ".. ";
        basename ${SCRIPT} .sh;
        ${SCRIPT};
    done;
  fi;
done