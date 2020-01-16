from utils import is_none, key_dict_to_camel_case
class PolicieRoleRepresentation:
    decisionStrategy: str
    name: str
    type: str
    roles: list
    logic: str

    def __init__(self, name: str, roles: list, type: str = "role", decisionStrategy: str = "UNANIMOUS", logic: str = "POSITIVE") -> None:
        self.decisionStrategy = decisionStrategy
        self.name = name
        self.type = type
        self.roles = roles
        self.logic = logic

    @staticmethod
    def from_dict(obj) -> 'PolicieRoleRepresentation':
        assert isinstance(obj, dict)
        obj = key_dict_to_camel_case(obj)
        decisionStrategy = is_none(obj.get("decisionStrategy"), "UNANIMOUS")
        name = obj.get("name")
        type = is_none(obj.get("type"), "role")
        roles = obj.get("roles")
        logic = is_none(obj.get("logic"), "POSITIVE")
        return PolicieRoleRepresentation(name, roles, type, decisionStrategy, logic)

    def to_dict(self) -> dict:
        result: dict = {}
        result["decisionStrategy"] = is_none(self.decisionStrategy, "UNANIMOUS")
        result["name"] = self.name
        result["type"] = is_none(self.type, "role")
        result["roles"] = self.roles
        result["logic"] = is_none(self.logic, "POSITIVE")
        return result

    @staticmethod
    def from_dicts(objs: list) -> list:
        lista = []
        for obj in objs:
            lista.append(PolicieRoleRepresentation.from_dict(obj))
        return lista