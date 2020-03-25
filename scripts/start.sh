#!/bin/bash

sudo docker rm -f xtool > /dev/null 2>&1

sudo docker run \
  -v $(pwd):$(pwd) \
  -v ~/.xtool/:/opt/xtool \
  -e workspace=$(pwd) \
  -it --name xtool xtool:2.0 bin/bash