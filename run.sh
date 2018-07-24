#!/bin/bash


if [ -z $1 ];then
	echo "É necessário definir o diretório de trabalho. ex:" 
	echo "$ ./run.sh ~/git/ "  
	exit 1;
fi

echo "Iniciando xtool... Aguarde."

mvn spring-boot:run -Dworkspace=$1
