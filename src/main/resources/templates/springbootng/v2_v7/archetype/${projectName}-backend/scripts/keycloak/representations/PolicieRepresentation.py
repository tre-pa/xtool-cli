from utils import is_none, key_dict_to_camel_case

class PolicieRepresentation:
    id: str
    name: str
    description: str
    type: str
    scopes: list

    def __init__(self, id: str, name: str, description: str, type: str, scopes: list) -> None:
        self.id = id
        self.name = name
        self.description = description
        self.type = type
        self.scopes = scopes

    @staticmethod
    def from_dict(obj) -> 'PolicieRepresentation':
        assert isinstance(obj, dict)
        obj = key_dict_to_camel_case(obj)
        id = obj.get("id")
        name = obj.get("name")
        description = obj.get("description")
        type = obj.get("type")
        scopes = obj.get("scopes")
        return PolicieRepresentation(id, name, description, type, scopes)


    def to_dict(self) -> dict:
        result: dict = {}
        result["id"] = self.id
        result["name"] = self.name
        result["description"] = self.description
        result["type"] = self.type
        result["scopes"] = self.scopes
        return result

    @staticmethod
    def from_dicts(objs: list) -> list:
        lista = []
        for obj in objs:
            lista.append(PolicieRepresentation.from_dict(obj))
        return lista