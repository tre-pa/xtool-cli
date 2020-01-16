from utils import is_none, key_dict_to_camel_case

class RoleRepresentation:
    id: str
    name: str

    def __init__(self, id: str, name: str) -> None:
        self.id = id
        self.name = name

    @staticmethod
    def from_dict(obj) -> 'RoleRepresentation':
        assert isinstance(obj, dict)
        obj = key_dict_to_camel_case(obj)
        id = obj.get("id")
        name = obj.get("name")
        return RoleRepresentation(id, name)

    def to_dict(self) -> dict:
        result: dict = {}
        result["id"] = self.id
        result["name"] = self.name
        return result