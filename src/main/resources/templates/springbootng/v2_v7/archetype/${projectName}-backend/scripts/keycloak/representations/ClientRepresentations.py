from utils import is_none, key_dict_to_camel_case
class ClientRepresentation(object):
    id: int
    clientId: str
    name: str
    description: str
    enabled: bool
    redirectUris: list
    publicClient: bool
    implicitFlowEnabled: bool
    standardFlowEnabled: bool
    directAccessGrantsEnabled: bool
    serviceAccountsEnabled: bool
    secret: str
    authorizationServicesEnabled: bool
    fullScopeAllowed: bool
    baseUrl: str
    webOrigins: list


    def __init__(self, id: int, clientId: str, name: str, description: str, enabled: bool, redirectUris: list, publicClient: bool, implicitFlowEnabled: bool, standardFlowEnabled: bool, directAccessGrantsEnabled: bool, serviceAccountsEnabled: bool, secret: str, authorizationServicesEnabled: bool, fullScopeAllowed: bool = True, baseUrl: str = 'http://localhost:8080/', webOrigins: list = ["*"]) -> None:
        self.id = id
        self.clientId = clientId
        self.name = name
        self.description = description
        self.enabled = enabled
        self.redirectUris = redirectUris
        self.publicClient = publicClient
        self.implicitFlowEnabled = implicitFlowEnabled
        self.standardFlowEnabled = standardFlowEnabled
        self.directAccessGrantsEnabled = directAccessGrantsEnabled
        self.serviceAccountsEnabled = serviceAccountsEnabled
        self.secret = secret
        self.authorizationServicesEnabled = authorizationServicesEnabled
        self.fullScopeAllowed = fullScopeAllowed
        self.baseUrl = baseUrl
        self.webOrigins = webOrigins

    @staticmethod
    def from_dict(obj: dict) -> 'ClientRepresentation':
        assert isinstance(obj, dict)
        obj = key_dict_to_camel_case(obj)
        id = obj.get("id")
        clientId = obj.get("clientId")
        name = obj.get("name")
        description = obj.get("description")
        enabled = obj.get("enabled")
        redirectUris = obj.get("redirectUris")
        publicClient = obj.get("publicClient")
        implicitFlowEnabled = is_none(obj.get("implicitFlowEnabled"), False)
        standardFlowEnabled = is_none(obj.get("standardFlowEnabled"), True)
        directAccessGrantsEnabled = obj.get("directAccessGrantsEnabled")
        serviceAccountsEnabled = obj.get("serviceAccountsEnabled")
        secret = obj.get("secret")
        authorizationServicesEnabled = obj.get("authorizationServicesEnabled")
        fullScopeAllowed = obj.get("fullScopeAllowed")
        baseUrl = is_none(obj.get("baseUrl"),'http://localhost:8080/'+clientId)
        webOrigins = is_none(obj.get("webOrigins"), ["*"])
        return ClientRepresentation(id, clientId, name, description, enabled, redirectUris, publicClient, implicitFlowEnabled, standardFlowEnabled, directAccessGrantsEnabled, serviceAccountsEnabled, secret, authorizationServicesEnabled, fullScopeAllowed,baseUrl, webOrigins)

    @staticmethod
    def from_dicts(objs: list) -> list:
        lista = []
        for i in objs:
            lista.append(ClientRepresentation.from_dict(i))
        return lista

    def to_dict(self) -> dict:
        result: dict = {}
        result["id"] = self.id
        result["clientId"] = self.clientId
        result["name"] = self.name
        result["description"] = self.description
        result["enabled"] = self.enabled
        result["redirectUris"] = self.redirectUris
        result["publicClient"] = self.publicClient
        result["implicitFlowEnabled"] = self.implicitFlowEnabled
        result["standardFlowEnabled"] = self.standardFlowEnabled
        result["directAccessGrantsEnabled"] = self.directAccessGrantsEnabled
        result["serviceAccountsEnabled"] = self.serviceAccountsEnabled
        result["secret"] = self.secret
        result["authorizationServicesEnabled"] = self.authorizationServicesEnabled
        result["fullScopeAllowed"] = self.fullScopeAllowed
        return result