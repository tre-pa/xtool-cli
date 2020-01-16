# Script para comunicação com o Keycloak

import requests, json

from representations.ClientRepresentations import ClientRepresentation
from representations.ResourceRepresentarion import ResourceRepresentarion
from representations.PolicieRepresentation import PolicieRepresentation
from representations.PolicieRoleRepresentation import PolicieRoleRepresentation
from representations.PermissionRepresentation import PermissionRepresentation
from representations.ScopeRoleRepresentation import ScopeRoleRepresentation
from representations.GroupRepresentation import GroupRepresentation
from representations.RoleRepresentation import RoleRepresentation
from representations.RealmRepresentation import RealmRepresentation
from requests import ConnectionError
from utils import key_dict_to_camel_case


class Keycloak(object):

    def __init__(self, keycloak_url='http://localhost:8085/', keycloak_admin_user='admin', keycloak_admin_password='admin', keycloak_realms= 'TRE-PA'):
        self.keycloak_url = keycloak_url
        self.keycloak_admin_user = keycloak_admin_user
        self.keycloak_admin_password = keycloak_admin_password
        self.keycloak_realms = keycloak_realms
        self.access_token = None
        self.headers = None
        self.__create_realm(keycloak_realms)
    
    def __authenticate(self) -> None:
        """
            Pega o token de acesso do keycloak
            Lista os cliente da realm
            :return: list
        """
        print("Autenticando em "+self.keycloak_url)
        url = self.keycloak_url+'auth/realms/master/protocol/openid-connect/token'
        authorization_redirect_url = {
            'grant_type': 'password',
            'username': self.keycloak_admin_user,
            'password': self.keycloak_admin_password,
            'client_id': 'admin-cli'
        }
        try:
            response = requests.post(url=url, data=authorization_redirect_url)
            self.access_token = response.json()['access_token']
        except requests.exceptions.ConnectionError:
            print("Conexão com o servidor keycloak recusada")
            exit()


    def __check_authenticate(self) -> None:
        if self.access_token is None:
            self.__authenticate()
            self.headers = {'Authorization': 'Bearer ' + self.access_token}
    
    def find_by_client_id(self, clientId: str) -> list:
        """
        Procura um cliente pelo clientId
        :param clientId: str
        :return: list
        """
        return list(filter(lambda c: c.get("clientId") == clientId, self.list_clients()))

    def list_clients(self) -> list:
        """
            Lista os cliente da realm
            :return: list
        """
        self.__check_authenticate()
        url = self.keycloak_url+'auth/admin/realms/'+self.keycloak_realms+'/clients'
        response = requests.get(url=url, headers=self.headers)
        return list(response.json())

    def __create_realm(self, realmName: str):
        """
        Cria um realm com o nome informado
        :param realmaName:
        :return:
        """
        self.__check_authenticate()
        url = self.keycloak_url+"auth/admin/realms"
        realm = RealmRepresentation(id=realmName,realm=realmName)
        response = requests.post(url=url, headers=self.headers, json=realm.__dict__)
        if(response.status_code == 409):
            print("Realm: "+realm.id+" já existe")
        elif(response.status_code == 201):
            print("Realm: "+realm.id+" criado")
        else:
            print(response.content)

    def create_client(self, clientDict: dict ) -> None:
        """
        Cria um cliente no realm keycloak
        :param clientDict: dict
        :return: None
        """
        self.__check_authenticate()
        client = ClientRepresentation.from_dict(clientDict)
        print("Criando Cliente: "+client.clientId)
        url = self.keycloak_url+'auth/admin/realms/'+self.keycloak_realms+'/clients'
        response = requests.post(url=url, headers=dict(self.headers), json=client.__dict__)
        if(response.status_code == 409):
            print("Cliente: "+client.clientId+" já existe")
        elif(response.status_code == 201):
            print("Cliente: "+client.clientId+" criado")
        else:
            print(response.content)

    def list_role_client(self, client: ClientRepresentation) -> list:
        """
        Lista as roles de um cliente
        :param client: ClientRepresentation
        :return: list
        """
        self.__check_authenticate()
        url = self.keycloak_url+'auth/admin/realms/'+self.keycloak_realms+'/clients/'+str(client.id)+'/roles'
        response = requests.get(url, headers=self.headers)
        return response.json()

    def find_role_name_client(self, client: ClientRepresentation, role_name: str) -> list:
        client = ClientRepresentation.from_dict(self.find_by_client_id(client.clientId)[0])
        return list(filter(lambda role: role.get("name") == role_name, self.list_role_client(client)))


    def create_client_role(self, clientDict: dict, role: dict) -> None:
        """
        Cria role para um cliente
        :param clientDict: dict
        :param role: dict
        :return:
        """
        self.__check_authenticate()
        client = ClientRepresentation.from_dict(clientDict)
        client = ClientRepresentation.from_dict(self.find_by_client_id(client.clientId)[0])
        role = RoleRepresentation.from_dict(role)
        url = self.keycloak_url+'auth/admin/realms/'+self.keycloak_realms+'/clients/'+client.id+'/roles'
        response = requests.post(url=url, headers=self.headers, json=role.__dict__)
        if(response.status_code == 409):
            print("Role: "+role.name+" já existe para o cliente "+client.clientId)
        elif(response.status_code == 201):
            print("Role: "+role.name+" criado para o cliente "+client.clientId)
        else:
            print(response.content)

    def get_patch_token(self, project_name: str, project_secret: str) -> str:
        """
        Obtem o token de autorização de um cliente
        :param project_name: str
        :param project_secret: str
        :return: str
        """
        url = self.keycloak_url+'auth/realms/'+self.keycloak_realms+'/protocol/openid-connect/token'
        data = {
            "grant_type": "client_credentials",
            "client_id": project_name,
            "client_secret": project_secret
        }
        response = requests.post(url=url, data=data)
        return response.json().get("access_token")

    def list_client_resources(self, client: dict)-> list:
        """
        Lista os recursos de um cliente
        :param client: dict
        :return: list
        """
        patch_token = self.get_patch_token(client["clientId"],client["secret"])
        url = self.keycloak_url+"auth/realms/"+self.keycloak_realms+"/authz/protection/resource_set"
        response = requests.get(url=url, headers={"Authorization": 'Bearer '+str(patch_token), "Content-Type": "application/json"})
        return list(response.json())

    def find_client_resource_by_id(self, client: dict, id: str) -> dict:
        """
        Procura o recurso do cliente pelo id(hash)
        :param client: dict
        :param id: str
        :return: dict
        """
        patch_token = self.get_patch_token(client["clientId"],client["secret"])
        url = self.keycloak_url+"auth/realms/"+self.keycloak_realms+"/authz/protection/resource_set/"+id
        response = requests.get(url=url, headers={"Authorization": 'Bearer '+str(patch_token), "Content-Type": "application/json"})
        print(response.json())

    def create_client_resource(self, client: dict, resourceDict: dict) -> None:
        """
        Cria um recurso para um cliente
        :param client: dict
        :param resourceDict: dict
        :return: None
        """
        client = ClientRepresentation.from_dict(client)
        resource = ResourceRepresentarion.from_dict(resourceDict)
        patch_token = self.get_patch_token(client.clientId,client.secret)
        url = self.keycloak_url+"auth/realms/"+self.keycloak_realms+"/authz/protection/resource_set/"
        response = requests.post(url=url, json=resource.__dict__, headers={"Authorization": 'Bearer '+str(patch_token), "Content-Type": "application/json"})
        if(response.status_code == 409):
            print("Resource: "+resource.name+" já existe para o cliente "+client.clientId)
        elif(response.status_code == 201):
            print("Resource: "+resource.name+" criado para o cliente "+client.clientId)
        else:
            print(response.content)

    def list_client_policies(self, client: dict) -> list:
        """
        Lista as policies de um cliente
        :param client: dict
        :return: list
        """
        patch_token = self.get_patch_token(client["clientId"],client["secret"])
        url = self.keycloak_url+"auth/realms/"+self.keycloak_realms+"/authz/protection/uma-policy"
        response = requests.get(url=url, headers={"Authorization": 'Bearer '+str(patch_token), "Content-Type": "application/json"})
        return list(response.json())

    def create_client_policy(self, client: dict, policy: dict) -> None:
        """
        Cria uma policie para um cliente
        :param client: dict
        :param policy: dict
        :return: None
        """
        client = ClientRepresentation.from_dict(client)
        c = {}
        c = self.find_by_client_id(client.clientId)[0]
        p = PolicieRoleRepresentation.from_dict(policy)
        p.roles = list(map(lambda r: {"id": client.clientId+"/"+r.get("id"), "required": r.get("required")}, p.roles))
        url = self.keycloak_url+"auth/admin/realms/"+self.keycloak_realms+"/clients/"+c.get("id")+"/authz/resource-server/policy/role"
        response = requests.post(url=url, headers=self.headers, json=p.__dict__)
        if(response.status_code == 409):
            print("Resource: "+p.name+" já existe para o cliente "+client.clientId)
        elif(response.status_code == 201):
            print("Resource: "+p.name+" criado para o cliente "+client.clientId)
        else:
            print(response)
    
    def create_client_permission(self, client: dict, permission: dict) -> None:
        """
        Cria uma permission para um cliente
        :param client: dict
        :param permission: dict
        :return: None
        """
        url = ""
        client = ClientRepresentation.from_dict(client)
        c = {}
        c = self.find_by_client_id(client.clientId)[0]
        p: PermissionRepresentation = PermissionRepresentation.from_dict(permission)
        if p.scopes is not None:
            p.type = "scope"
            url = self.keycloak_url+"auth/admin/realms/"+self.keycloak_realms+"/clients/"+c.get("id")+"/authz/resource-server/permission/scope"
        else:
            p.type = "resource"
            url = self.keycloak_url+"auth/admin/realms/"+self.keycloak_realms+"/clients/"+c.get("id")+"/authz/resource-server/permission/resource"
        response = requests.post(url=url, headers=self.headers, json=p.__dict__)
        if(response.status_code == 409):
            print("Permission: "+p.name+" já existe para o cliente "+client.clientId)
        elif(response.status_code == 201):
            print("Permission: "+p.name+" criado para o cliente "+client.clientId)
        else:
            print(response)

    def associate_role_scope(self, client_id_source: str, client_id_target: str, roles: list) -> None:
        """
        Associa uma lista de roles de um cliente a outro cliente
        :param client_id_source: client_id (não id!)
        :param client_id_target: client_id (não id!)
        :param roles: Lista de string com o nome das roles
        :return: None
        """
        destino = {}
        origem = {}
        if roles:
            origem = self.find_by_client_id(client_id_source)[0]
            destino = self.find_by_client_id(client_id_target)[0]
            scope_roles = list(map(lambda role: ScopeRoleRepresentation.from_dict({"name":role}).__dict__, roles))
            url = self.keycloak_url+"auth/admin/realms/"+self.keycloak_realms+"/clients/"+destino.get("id")+"/scope-mappings/clients/"+origem.get("id")
            response = requests.post(url=url, headers=self.headers, json=scope_roles)
            if(response.status_code == 409):
                print("Roles já estão associada")
            elif(response.status_code == 204):
                print("Roles associadas com sucesso")
            else:
                print(response)

    def composite_roles_client(self, client_id: str, role_pai_name: str, roles: list) -> None:
        """
        associa um conjunto de roles a uma role pai
        :param client_id: client_id (não id!)
        :param role_pai_name: O nome da role
        :param roles: Lista de string com o nome das roles
        :return: None
        """
        c = self.find_by_client_id(client_id)[0]
        for role in roles:
            r = RoleRepresentation.from_dict(self.find_role_name_client(ClientRepresentation.from_dict(c), role)[0])
            url = self.keycloak_url+"auth/admin/realms/"+self.keycloak_realms+"/clients/"+c.get("id")+"/roles/"+role_pai_name+"/composites"
            json = [{
                "id": r.id,
                "name": r.name
            }]
            response = requests.post(url=url, json=list(json), headers=self.headers)
            if response.status_code == 204:
                print("role "+role_pai_name+" foi composta com "+role)
            else:
                print(response)

    def list_groups(self):
        url = self.keycloak_url+"auth/admin/realms/"+self.keycloak_realms+"/groups/"
        response = requests.get(url=url, headers=self.headers)
        return list(response.json())
    
    def find_groups_by_name(self, name: str):
        return list(filter(lambda group: group.get("name") == name, self.list_groups()))

    def create_group(self, groupDict: dict):
        """
        Cria um grupo com base em um dict
        :param groupDict:
        :return:
        """
        group = GroupRepresentation.from_dict(groupDict)
        url = self.keycloak_url+"auth/admin/realms/"+self.keycloak_realms+"/groups/"
        response = requests.post(url=url, json=group.__dict__, headers=self.headers)
        if(response.status_code == 409):
            print("Grupo "+group.name+" já existe")
        elif(response.status_code == 204 or response.status_code == 201):
            print("Grupo "+group.name+" foi criado")
        else:
            print(response)

    def associate_roles_group(self,role_name: str, group_name: str, client_id: str):
        """
        Associa uma role de um client ao scope de group

        :param role_name: Nome da role
        :param group_name: Nome do grupo
        :param client_id: client id (Não id!)
        :return:
        """
        client = ClientRepresentation.from_dict(self.find_by_client_id(client_id)[0])
        role = self.find_role_name_client(client,role_name)[0]
        group = GroupRepresentation.from_dict(self.find_groups_by_name(group_name)[0])
        url = self.keycloak_url+"auth/admin/realms/"+self.keycloak_realms+"/groups/"+group.id+"/role-mappings/clients/"+client.id
        scope = ScopeRoleRepresentation(id=role.get("id"),name=role.get("name"),containerId=client.id)
        response = requests.post(url=url, headers=self.headers, json=[scope.__dict__])
        if(response.status_code == 204 or response.status_code == 201):
            print("Role "+role_name+" associada ao grupo "+group_name)
        else:
            print(response)
