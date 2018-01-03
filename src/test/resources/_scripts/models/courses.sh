#!/usr/bin/env bash

OUTPUT="$(echo $0 | sed 's#sh#json#g' | sed 's#^#../#g')"

curl --silent \
    -H 'Authorization: Bearer '${CDA_TOKEN}  \
    'https://cdn.contentful.com/spaces/'${SPACE_ID}'/entries?content_type=course' \
    | python -m json.tool \
    > ${OUTPUT}
