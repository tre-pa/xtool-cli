#!/bin/bash

__NORMAL__="\e[0m\e[39m"
__YELLOW__="\e[93m"
__RED__="\e[91m"
REPOSITORY_HOME=~/xtool/

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

echo "Buscando pelo python..."
if [ -z $(which git) ]; then
  echo "Não encontrado."
  echo "======================================================================================================"
  echo " Instale o python com seu gerenciador de pacotes."
  echo ""
  echo " Reinicie após a instalação do python."
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

echo "Buscando pelo graphviz..."
if [ -z $(which dot) ]; then
  echo "Não encontrado."
  echo "======================================================================================================"
  echo " Para visualizar o PlantUml, Instale o graphviz com o comando 'sudo dnf install graphviz'."
  echo "visite (https://apps.fedoraproject.org/packages/graphviz/updates/)"
  echo ""
  echo " Reinicie após a instalação do graphviz."
  echo "======================================================================================================"
  echo ""
fi

LOCAL=$(git rev-parse @)
REMOTE=$(git rev-parse origin/master)
BASE=$(git merge-base @ master)
if [ $LOCAL = $REMOTE ]; then
  echo "Tudo atualizado"
elif [ $LOCAL = $BASE ]; then
  echo "Há atualizações! É necessario 'git pull'"
fi

if [ ! -d "$1" ]; then
  echo -e "${__RED__}"
  echo -e "======================================================================================================"
  echo -e "É necessário definir o diretório de trabalho. ex:"
  echo -e "$ ./run.sh ~/git/ "
  echo -e "======================================================================================================"
  echo -e "${__NORMAL__}"
  exit 1
fi

if [ ! -d "$REPOSITORY_HOME" ]; then
  mkdir -p $REPOSITORY_HOME
  echo -e "${__YELLOW__}"
  echo -e "Diretório de repositório de componentes '$REPOSITORY_HOME' criado com sucesso. "
  echo -e "${__NORMAL__}"
fi

echo "Iniciando xtool... Aguarde."

mvn spring-boot:run -Dworkspace=$1 -Drepository.home=$REPOSITORY_HOME -DskipTests
