#
# Script com as funções para registros do Client no Keycloak.
#
import argparse
import yaml
import os
from keycloak import Keycloak

path = os.getcwd().split(os.sep)
project_name = path[len(path)-1]

parser = argparse.ArgumentParser()
parser.add_argument("-c", help="Endereço de conexão do Keycloak", default="http://localhost:8085/")
parser.add_argument("-u", help="Usuário administrador do Keycloak", default="admin")
parser.add_argument("-p", help="Senha do usuário administrador do Keycloak", default="admin")
parser.add_argument("-s", help="Secret do client", required=True)
parser.add_argument("-f", help="Arquivo .yml com as informações do client.",  default=project_name+"-backend/src/main/resources/keycloak/kc.yml")
args = parser.parse_args()

def get_clients_from_yml(yml):
    clients: list = []
    for client in yml.get("clients"):
        clients.append(client)
    return clients

def get_groups_from_yml(yml):
    groups: list = []
    if yml.get("groups") is not None:
        for group in yml.get("groups"):
            groups.append(group)
    return groups

with open(r'%s' % os.path.realpath(args.f)) as file:
    ymlMap = yaml.load(file, Loader=yaml.FullLoader)
    k = Keycloak(keycloak_url=args.c, keycloak_admin_user=args.u, keycloak_admin_password=args.p, keycloak_realms=ymlMap.get("realm"))
    secret = args.__dict__.get("s")
    for client in get_clients_from_yml(ymlMap):
        client["secret"] = secret
        k.create_client(client)
        if client.get("roles") is not None:
            roles = list(map(lambda role: {"name": role}, client["roles"]))
            for role in roles:
                k.create_client_role(client, role)
        if client.get("authorization"):
            for resource in client.get("authorization").get("resources"):
                k.create_client_resource(client, resource)
            for policy in client.get("authorization").get("policies"):
                k.create_client_policy(client, policy)
            for permission in client.get("authorization").get("permissions"):
                k.create_client_permission(client, permission)
        if client.get("client_scope_mappings"):
            k.associate_role_scope(client.get("client_scope_mappings").get( "client_id"), client.get("client_id"), client.get("client_scope_mappings").get("roles"))
        if client.get("composite_roles"):
            composites = client.get("composite_roles")
            for composite in composites:
                k.composite_roles_client(client.get("client_id"), composite.get("name"), composite.get("roles"))

    for group in get_groups_from_yml(ymlMap):
        k.create_group(group)
        if group.get("mapped_roles") is not None:
            for role in group.get("mapped_roles").get("roles"):
                  k.associate_roles_group(role_name=role,group_name=group.get("name"), client_id=group.get("mapped_roles").get("client_id"))
