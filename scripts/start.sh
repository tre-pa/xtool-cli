#!/bin/bash

sudo docker rm -f xtool > /dev/null 2>&1

sudo docker run -v $(pwd):/opt/workspace -v ~/xtool/:/opt/repository -it --name xtool xtool:2.0 bin/bash