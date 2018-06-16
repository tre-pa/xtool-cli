#!/bin/bash

mvn install -Dmaven.test.skip=true && java -Xms32m -Xmx256m  -jar target/xtool-cli-0.0.1-SNAPSHOT.jar