#!/bin/bash

# Global variables
XTOOL_SERVICE="https://raw.githubusercontent.com/tre-pa/xtool-cli/${XTOOL_BRANCH:master}"
#XTOOL_VERSION="2.0"
XTOOL_PLATFORM=$(uname)

if [ -z "$XTOOL_DIR" ]; then
    XTOOL_DIR="$HOME/.xtool"
fi

# Local variables
xtool_bin_folder="${XTOOL_DIR}/bin"
xtool_src_folder="${XTOOL_DIR}/src"
xtool_tmp_folder="${XTOOL_DIR}/tmp"
xtool_repository_folder="${XTOOL_DIR}/repository"
#xtool_config_file="${xtool_etc_folder}/config"
xtool_bash_profile="${HOME}/.bash_profile"
xtool_profile="${HOME}/.profile"
xtool_bashrc="${HOME}/.bashrc"
xtool_zshrc="${HOME}/.zshrc"

xtool_init_snippet=$( cat << EOF
export XTOOL_DIR="$XTOOL_DIR"
[[ -s "${XTOOL_DIR}/bin/xtool-init.sh" ]] && source "${XTOOL_DIR}/bin/xtool-init.sh"
EOF
)

# OS specific support (must be 'true' or 'false').
cygwin=false;
darwin=false;
solaris=false;
freebsd=false;
case "$(uname)" in
    CYGWIN*)
        cygwin=true
        ;;
    Darwin*)
        darwin=true
        ;;
    SunOS*)
        solaris=true
        ;;
    FreeBSD*)
        freebsd=true
esac

echo "Buscando pelo curl..."
if [ -z $(which curl > /dev/null 2>&1) ]; then
	echo "Não encontrado."
	echo ""
	echo "======================================================================================================"
	echo " Please install curl on your system using your favourite package manager."
	echo ""
	echo " Restart after installing curl."
	echo "======================================================================================================"
	echo ""
	exit 1
fi

echo "Criando diretórios..."
mkdir -p "$xtool_bin_folder"
mkdir -p "$xtool_src_folder"
mkdir -p "$xtool_tmp_folder"
mkdir -p "$xtool_repository_folder"