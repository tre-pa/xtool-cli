#!/bin/bash
# Author: SDS/COSIS/TRE-PA
# Version: v0.0.1
# Date: 19/03/2019
# Description: Registra a aplicação Spring Boot no Keycloak.
# Usage: ./register.sh 


KC_HOME=$HOME/opt/keycloak-4.8.3.Final
KC_SERVER=http://localhost:8085
KC_REALM=TRE-PA
KC_ADMIN_USER=admin
KC_ADMIN_PASS=admin
KC_CLIENT_ID=${projectName}-service
KC_CLIENT_SECRET=863b7f77-18b7-44a3-9c65-245f35a9a40c #Mesmo client_secret do application.properties
KC_REALM_ROLES=()
KC_CLIENT_ROLES=("USER" "REPORT_MANAGER")
__NORMAL__="\e[0m\e[39m"
__YELLOW__="\e[93m"
__RED__="\e[91m"
__BLUE__="\e[1m\e[96m"
__GREEN__="\e[92m"
__BG_BLUE__="\e[1m\e[44m"

check_jq() {
	#echo "Buscando pelo utilitário 'jq'..."
	if [ -z $(which jq) ]; then
		echo 
		echo "Utilitário 'jq' (https://stedolan.github.io/jq/) não encontrado."
		echo "======================================================================================================"
		echo " Instale o 'jq' com seu gerenciador de pacotes. No fedora: "
		echo " 	$ sudo dnf install jq "
		echo " ${__RED__}Execute o script novamente após a instalação.${__NORMAl__}"
		echo "======================================================================================================"
		echo ""
		exit 0
	fi
}
# Inicia o keycloak caso esteja parado.
#start() {
#	
#}

change_dir() {
	if [ ! -d "$KC_HOME/bin" ]; then
		echo ""
		echo "Não foi encontrada nenhuma instalação válida do keycloak em '$KC_HOME' "
		exit 1
	fi
	cd $KC_HOME/bin
}

# Realiza a autenticação no keycloak para a execução dos comandos.
authenticate() {
	echo -e "* ${__BLUE__}Iniciando autenticação no keycloak com usuário '$KC_ADMIN_USER'.${__NORMAL__}"
	./kcadm.sh config credentials --server ${KC_SERVER}/auth --realm master --user ${KC_ADMIN_USER} --client admin-cli --password ${KC_ADMIN_PASS} > /dev/null 2>&1
	if [ "$?" == 0 ]; then
		echo -e " - ${__GREEN__}Autenticação realizada com sucesso.${__NORMAL__}"
	else
		echo -e " - ${__RED__}Erro ao autenticar no keycloak.${__NORMAL__}"
		exit 1
	fi
}

# Verifica a existência do realm $KC_REALM.
#
check_realm() {
	echo ""
	echo -e "* ${__BLUE__}Verificando a existência do realm '$KC_REALM'...${__NORMAL__}"
	local v=$(./kcadm.sh get realms | jq '.[] | select(.id == '\""$KC_REALM\""') | has("id")') 
	if [ "${v}" == "true" ]; then
		echo -e " - ${__GREEN__}Realm '$KC_REALM' encontrado.${__NORMAL__}"
		return 0
	fi		
	echo -e " - ${__YELLOW__}Realm '$KC_REALM' não encontrado. Proceda com criação do realm e execute o script novamente.${__NORMAL__}"
	exit 1
}

# Verifica a existência do client e cria caso não exista.
create_client() {
	echo ""
	echo -e "* ${__BLUE__}Verificando a existência do client '$KC_CLIENT_ID'...${__NORMAL__}"
	local v=$(./kcadm.sh get clients -r $KC_REALM --fields clientId | jq '.[] | select(.clientId == "'$KC_CLIENT_ID'") | has("clientId")')
	if [ "${v}" == "true" ]; then
		echo -e " - ${__GREEN__}Client '$KC_CLIENT_ID' encontrado.${__NORMAL__}"
		return 0
	fi		
	echo -e " - ${__YELLOW__}Client '$KC_CLIENT_ID' não encontrado. Iniciando criação...${__NORMAL__}\r"
	
	./kcadm.sh create clients -r $KC_REALM \
		-s clientId=$KC_CLIENT_ID \
		-s enabled=true \
		-s secret=$KC_CLIENT_SECRET \
		-s publicClient=false \
		-s 'redirectUris=["http://localhost:8080/'$KC_CLIENT_ID'/*"]' \
		-s 'webOrigins=["*"]' \
		-s directAccessGrantsEnabled=true \
		-s serviceAccountsEnabled=true \
		-s authorizationServicesEnabled=true > /dev/null 2>&1
		
	if [ "$?" == 0 ]; then
		echo -e " - ${__GREEN__}Client '$KC_CLIENT_ID' criado com sucesso.${__NORMAL__}"
	else
		echo -e " - ${__RED__} Erro ao criar client '$KC_CLIENT_ID'.${__NORMAL__}"
		exit 1
	fi
		
}

#
create_realm_roles() {
	echo ""
	for role in "${KC_REALM_ROLES[@]}"; do
		echo -e "* ${__BLUE__}Verificando existência da realm role '$role'.${__NORMAL__}..."
		local v=$(./kcadm.sh get-roles -r $KC_REALM | jq '.[] | select(.name == "'$role'") | has("name")')
		if [ "${v}" != "true" ]; then
			echo -e " - ${__YELLOW__}Realm role '$role' não encontrada. Iniciando criação...${__NORMAL__}"
			./kcadm.sh create roles -r $KC_REALM -s name=$role
		else
			echo -e " - ${__GREEN__}Realm role '$role' encontrada.${__NORMAL__}"
		fi
	done
}

#Vericia a existência dos client roles e cria em caso de inexistência.
create_client_roles() {
	echo ""
	local id=$(./kcadm.sh get clients -r $KC_REALM --fields id,clientId | jq -r '.[] | select(.clientId == "'$KC_CLIENT_ID'") | .id')
	for role in "${KC_CLIENT_ROLES[@]}"; do
		echo -e "* ${__BLUE__}Verificando existência da client role '$role'${__NORMAL__}..."
		local v=$(./kcadm.sh get-roles -r $KC_REALM --cclientid $KC_CLIENT_ID | jq '.[] | select(.name == "'$role'") | has("name")')
		if [ "${v}" != "true" ]; then
			echo -e " - ${__YELLOW__}Client role '$role' não encontrada. Iniciando criação...${__NORMAL__}"
			./kcadm.sh create clients/$id/roles -r $KC_REALM -s name=$role > /dev/null 2>&1
			if [ "$?" == 0 ]; then
				echo -e " - ${__GREEN__}Client role '$role' criada com sucesso.${__NORMAL__}"
			else
				echo -e " - ${__RED__} Erro ao criar client role '$role'.${__NORMAL__}"
				exit 1
			fi 
		else
			echo -e " - ${__GREEN__}Client role '$role' encontrada.${__NORMAL__}" 
		fi
	done
}

import_authz_alert() {
	echo ""
	echo -e "${__BLINK__}${__RED__} IMPORTANTE: ${__NORMAL__} Leia as instruções no README.adoc do projeto com os procedimentos de importação das configuração de autorização. "
}

echo -e "${__BLUE__}Iniciando o registro da aplicação Spring Boot ${__NORMAL__}${__BG_BLUE__}$KC_CLIENT_ID${__NORMAL__}${__BLUE__} no Keycloak...${__NORMAL__}\n"
check_jq
change_dir
authenticate
check_realm
create_client
create_realm_roles
create_client_roles
echo -e "${__BLUE__}Registro parcial da aplicação '$KC_CLIENT_ID' realizado com sucesso!${__NORMAL__}"
import_authz_alert
