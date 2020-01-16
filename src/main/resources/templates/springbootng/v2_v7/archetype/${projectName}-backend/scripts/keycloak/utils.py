import re

def is_none(value, isValueNone):
    return (value,isValueNone)[value is None]

def to_snake_case(name):
    s1 = re.sub('(.)([A-Z][a-z]+)', r'\1_\2', name)
    return re.sub('([a-z0-9])([A-Z])', r'\1_\2', s1).lower()

def to_camel_case(snake_str):
    components = snake_str.split('_')
    return components[0] + ''.join(x.title() for x in components[1:])


def key_dict_to_camel_case(obj: dict):
    new_obj = {}
    for key in obj.keys():
        new_obj[to_camel_case(key)] = obj[key]
        if isinstance(obj.get(key), dict):
            new_obj[to_camel_case(key)] =  key_dict_to_camel_case(obj.get(key))
    return new_obj