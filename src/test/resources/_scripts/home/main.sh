#!/usr/bin/env bash

OUTPUT="$(echo $0 | sed 's#sh#json#g' | sed 's#^#../#g')"

curl --silent \
    -H 'Authorization: Bearer '${CPA_TOKEN}  \
    'https://preview.contentful.com/spaces/'${SPACE_ID}'/entries?sys.id=2uNOpLMJioKeoMq8W44uYc&include=5&locale=*' \
    | python -m json.tool \
    > ${OUTPUT}
