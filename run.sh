#!/bin/bash

__NORMAL__="\e[0m\e[39m"
__YELLOW__="\e[93m"
__RED__="\e[91m"

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

echo "Buscando pelo npm..."
if [ -z $(which npm) ]; then
	echo "Não encontrado."
	echo "======================================================================================================"
	echo " Instale o npm com nvm (https://github.com/nvm-sh/nvm)."
	echo ""
	echo " Reinicie após a instalação do npm."
	echo "======================================================================================================"
	echo ""
	exit 0
fi

if [ ! -d "$1" ]; then
    echo -e "${__RED__}"
    echo -e "======================================================================================================"
	echo -e "É necessário definir o diretório de trabalho. ex:" 
	echo -e "$ ./run.sh ~/git/ "  
    echo -e "======================================================================================================"
    echo -e "${__NORMAL__}"
	exit 1;
fi

echo "Iniciando xtool... Aguarde."

mvn spring-boot:run -Dworkspace=$1 -DskipTests
