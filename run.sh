#!/bin/bash

echo "Buscando pelo maven..."
if [ -z $(which mvn) ]; then
	echo "Não encontrado."
	echo "======================================================================================================"
	echo " Instale o mvn com seu gerenciador de pacotes."
	echo ""
	echo " Reinicie após a instalação do mvn."
	echo "======================================================================================================"
	echo ""
	exit 0
fi

echo "Buscando pelo git..."
if [ -z $(which git) ]; then
	echo "Não encontrado."
	echo "======================================================================================================"
	echo " Instale o git com seu gerenciador de pacotes."
	echo ""
	echo " Reinicie após a instalação do git."
	echo "======================================================================================================"
	echo ""
	exit 0
fi

if [ -z $1 ];then
	echo "É necessário definir o diretório de trabalho. ex:" 
	echo "$ ./run.sh ~/git/ "  
	exit 1;
fi

echo "Iniciando xtool... Aguarde."

mvn spring-boot:run -Dworkspace=$1
