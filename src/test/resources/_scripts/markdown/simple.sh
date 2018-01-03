#!/usr/bin/env bash

OUTPUT="$(echo $0 | sed 's#sh#md#g' | sed 's#^#../#g')"

curl --silent \
    -H 'Authorization: Bearer '${CDA_TOKEN}  \
    'https://cdn.contentful.com/spaces/'${SPACE_ID}'/entries?sys.id=58B62ubQbmIOiAkmq44kAo' \
    | jq -r .items[0].fields.copy \
    > ${OUTPUT}
