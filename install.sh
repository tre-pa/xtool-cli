#!/bin/bash

# Global variables
XTOOL_GITREPO="https://github.com/tre-pa/xtool-cli.git"

if [ -z "$XTOOL_DIR" ]; then
    XTOOL_DIR="$HOME/.xtool"
fi

# Local variables
xtool_bin_folder="${XTOOL_DIR}/bin"
xtool_src_folder="${XTOOL_DIR}/src"
xtool_tmp_folder="${XTOOL_DIR}/tmp"
xtool_repo_folder="${XTOOL_DIR}/repository"
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
if [ -z $(which curl) ]; then
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

echo "Buscando pelo java..."
if [ -z $(which java) ]; then
	echo "Não encontrado."
	echo ""
	echo "======================================================================================================"
	echo " Please install java on your system using your favourite package manager."
	echo ""
	echo " Restart after installing java."
	echo "======================================================================================================"
	echo ""
	exit 1
fi

echo "Buscando pelo maven..."
if [ -z $(which mvn) ]; then
	echo "Não encontrado."
	echo ""
	echo "======================================================================================================"
	echo " Please install maven on your system using your favourite package manager."
	echo ""
	echo " Restart after installing maven."
	echo "======================================================================================================"
	echo ""
	exit 1
fi

echo "Buscando pelo git..."
if [ -z $(which git) ]; then
	echo "Não encontrado."
	echo ""
	echo "======================================================================================================"
	echo " Please install git on your system using your favourite package manager."
	echo ""
	echo " Restart after installing git."
	echo "======================================================================================================"
	echo ""
	exit 1
fi

echo "Criando diretórios..."
mkdir -p "$xtool_bin_folder"
mkdir -p "$xtool_src_folder"
mkdir -p "$xtool_tmp_folder"
mkdir -p "$xtool_repo_folder"

rm -rf "$xtool_tmp_folder/*"
cd $xtool_tmp_folder
git clone "$XTOOL_GITREPO"

if [ -z "$XTOOL_BRANCH" ]; then
  git checkout $XTOOL_BRANCH
fi

cp $xtool_tmp_folder/xtool-cli/installation/bin/xtool-init.sh $xtool_bin_folder
cp $xtool_tmp_folder/xtool-cli/installation/src/xtool-main.sh $xtool_src_folder


if [[ $darwin == true ]]; then
  touch "$xtool_bash_profile"
  echo "Attempt update of login bash profile on OSX..."
  if [[ -z $(grep 'xtool-init.sh' "$xtool_bash_profile") ]]; then
    echo -e "\n$xtool_init_snippet" >> "$xtool_bash_profile"
    echo "Added xtool init snippet to $xtool_bash_profile"
  fi
else
  echo "Attempt update of interactive bash profile on regular UNIX..."
  touch "${xtool_bashrc}"
  if [[ -z $(grep 'xtool-init.sh' "$xtool_bashrc") ]]; then
      echo -e "\n$xtool_init_snippet" >> "$xtool_bashrc"
      echo "Added xtool init snippet to $xtool_bashrc"
  fi
fi

echo "Attempt update of zsh profile..."
touch "$xtool_zshrc"
if [[ -z $(grep 'xtool-init.sh' "$xtool_zshrc") ]]; then
    echo -e "\n$xtool_init_snippet" >> "$xtool_zshrc"
    echo "Updated existing ${xtool_zshrc}"
fi

echo -e "\n\n\nAll done!\n\n"

echo "Please open a new terminal, or run the following in the existing one:"
echo ""
echo "    source \"${XTOOL_DIR}/bin/xtool-init.sh\""
echo ""
echo "Then issue the following command:"
echo ""
echo "    sdk help"
echo ""
echo "Enjoy!!!"