from utils import is_none, key_dict_to_camel_case

class ResourceRepresentarion:
    name: str
    type: str
    icon_uri: str
    resource_scopes: list
    uris: list

    def __init__(self, name: str, type: str, icon_uri: str, resource_scopes: list, uris: list) -> None:
        self.name = name
        self.type = type
        self.icon_uri = icon_uri
        self.resource_scopes = resource_scopes
        self.uris = uris

    @staticmethod
    def from_dict(obj) -> 'ResourceRepresentarion':
        assert isinstance(obj, dict)
        obj = key_dict_to_camel_case(obj)
        name = obj.get("name")
        type = obj.get("type")
        icon_uri = obj.get("icon_uri")
        resource_scopes = obj.get("scopes")
        uris = obj.get("uris")
        return ResourceRepresentarion(name, type, icon_uri, resource_scopes, uris)

    @staticmethod
    def from_dicts(objs: list):
        lista = []
        for obj in objs:
            lista.append(ResourceRepresentarion.from_dict(obj))
        return lista

    def to_dict(self) -> dict:
        result: dict = {}
        result["name"] = self.name
        result["type"] = self.type
        result["icon_uri"] = self.icon_uri
        result["scopes"] = self.resource_scopes
        result["uris"] = self.uris
        return result