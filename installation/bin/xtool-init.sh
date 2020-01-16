#!/usr/bin/env bash

# Determine shell
zsh_shell=false
bash_shell=false

if [[ -n "$ZSH_VERSION" ]]; then
	zsh_shell=true
else
	bash_shell=true
fi

OLD_IFS="$IFS"
IFS=$'\n'
scripts=($(find "${XTOOL_DIR}/src" "${XTOOL_DIR}/ext" -type f -name 'xtool-*'))
for f in "${scripts[@]}"; do
	source "$f"
done
IFS="$OLD_IFS"
unset scripts f

