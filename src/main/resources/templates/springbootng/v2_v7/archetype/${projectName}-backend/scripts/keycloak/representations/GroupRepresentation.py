from utils import is_none, key_dict_to_camel_case

class GroupRepresentation:
    id: str
    name: str
    path: str
    attributes: dict
    realmRoles: list
    subGroups: list

    def __init__(self, id: str, name: str, path: str, attributes: dict = {}, realmRoles: list = [], subGroups: list = []) -> None:
        self.id = id
        self.name = name
        self.path = path
        self.attributes = attributes
        self.realmRoles = realmRoles
        self.subGroups = subGroups

    @staticmethod
    def from_dict(obj: dict) -> 'GroupRepresentation':
        assert isinstance(obj, dict)
        obj = key_dict_to_camel_case(obj)
        id = obj.get("id")
        name = obj.get("name")
        path = obj.get("path")
        attributes = is_none(obj.get("attributes"), {})
        realmRoles = is_none(obj.get("realmRoles"), [])
        subGroups = is_none(obj.get("subGroups"), [])
        return GroupRepresentation(id, name, path, attributes, realmRoles, subGroups)

    def to_dict(self) -> dict:
        result: dict = {}
        result["id"] = self.id
        result["name"] = self.name
        result["path"] = self.path
        result["attributes"] = self.attributes
        result["realmRoles"] = self.realmRoles
        result["subGroups"] = self.subGroups
        return result