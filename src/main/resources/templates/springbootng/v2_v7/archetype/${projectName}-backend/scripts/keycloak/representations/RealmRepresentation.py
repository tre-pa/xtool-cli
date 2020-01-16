from utils import is_none, key_dict_to_camel_case

class RealmRepresentation:
    enabled: bool
    id: str
    realm: str

    def __init__(self, id: str, realm: str, enabled: bool = True) -> None:
        self.enabled = enabled
        self.id = id
        self.realm = realm

    @staticmethod
    def from_dict(obj) -> 'RealmRepresentation':
        assert isinstance(obj, dict)
        obj = key_dict_to_camel_case(obj)
        enabled = is_none(obj.get("enabled"), True)
        id = obj.get("id")
        realm = obj.get("realm")
        return RealmRepresentation(enabled, id, realm)

    def to_dict(self) -> dict:
        result: dict = {}
        result["enabled"] = self.enabled
        result["id"] = self.id
        result["realm"] = self.realm
        return result