#!/bin/bash


if [ -z $1 ];then
	echo "É necessário definir o diretório de trabalho. ex:" 
	echo "$ ./run.sh ~/git/ "  
	exit 1;
fi

echo "Iniciando xtool... Aguarde."
mvn clean install -Dmaven.test.skip=true && clear && java -Xms32m -Xmx256m  -jar target/xtool-cli-0.0.1-SNAPSHOT.jar --workspace=$1