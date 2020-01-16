from utils import is_none, key_dict_to_camel_case

class ScopeRoleRepresentation:
    name: str
    id: str
    composite: bool
    clientRole: bool
    containerId: str

    def __init__(self, id: str, name: str, containerId: str, composite: bool = False, clientRole: bool = True) -> None:
        self.name = name
        self.composite = composite
        self.clientRole = clientRole
        self.containerId = containerId
        self.id = id

    @staticmethod
    def from_dict(obj) -> 'ScopeRoleRepresentation':
        assert isinstance(obj, dict)
        obj = key_dict_to_camel_case(obj)
        id = obj.get("id")
        name = obj.get("name")
        containerId = obj.get("containerId")
        composite = is_none(obj.get("composite"), False)
        clientRole = is_none(obj.get("clientRole"), True)
        return ScopeRoleRepresentation(id,name, containerId, composite, clientRole)

    def to_dict(self) -> dict:
        result: dict = {}
        result["id"] = self.id
        result["name"] = self.name
        result["containerId"] = self.containerId
        result["composite"] = self.composite
        result["clientRole"] = self.clientRole
        return result