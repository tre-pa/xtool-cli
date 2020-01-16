from utils import is_none, key_dict_to_camel_case

class PermissionRepresentation:
    type: str
    name: str
    description: str
    resources: list
    policies: list
    scopes: list

    def __init__(self, name: str, description: str, resources: list, policies: list, type: str, scopes: list) -> None:
        self.type = type
        self.name = name
        self.description = description
        self.resources = resources
        self.policies = policies
        self.scopes = scopes

    @staticmethod
    def from_dict(obj) -> 'PermissionResourceRepresentation':
        assert isinstance(obj, dict)
        obj = key_dict_to_camel_case(obj)
        type = obj.get("type")
        name = obj.get("name")
        description = obj.get("description")
        resources = obj.get("resources")
        policies = obj.get("policies")
        scopes = obj.get("scopes")
        return PermissionRepresentation(name, description, resources, policies, type, scopes)

    def to_dict(self) -> dict:
        result: dict = {}
        result["type"] = self.type
        result["name"] = self.name
        result["description"] = self.description
        result["resources"] = self.resources
        result["policies"] = self.policies
        result["scopes"] = self.scopes
        return result