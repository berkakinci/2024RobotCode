#! /usr/bin/env python
# Reads CAN IDs used in software to make documentation table.

import json
import csv
from pathlib import Path
from warnings import warn

##########
# Config
##########

jsondirs=(
    './main/deploy/swerve/neo',
)

csvfilename='../build/docs/can_ids.csv'

shorten_to={
    'angle'    : 'Sw',
    'drive'    : 'Dr',
    'encoder'  : 'Enc',
    'modules/' : '',
    'front'    : 'F',
    'back'     : 'B',
    'right'    : 'R',
    'left'     : 'L',
}

##########
# Supporting Functions
##########

def key_from_path(path, relative_to=''):
    "Generate a concise key from a Path object."
    path=path.relative_to(relative_to)
    path=path.with_suffix('') # Remove extensions
    return str(path)

def find_in_nested_dict(data, search_for='id', parents=[]):
    """
    Find a "search_for" key recursively in a nested dictionary.
    Yields tuples of (key, value).  Keys are returned as a list preserving hierarchy.
    Note that if the "search_for" key has a dictionary value, it will:
    - be returned as a dictionary value.
    - also be subsequency searched recursively.
    """
    if search_for in data:
        keys=parents.copy()
        keys.append(search_for)
        yield (keys, data[search_for])
    for (key, value) in data.items():
        keys=parents.copy()
        keys.append(key)
        if isinstance(value, dict):
            yield from find_in_nested_dict(value, search_for, keys)
    return

def shorten_string(string):
    "Quick and dirty shorten a string using our lookup table."
    for (pattern, replacement) in shorten_to.items():
        string=string.replace(pattern, replacement)
    return string

##########
# Main
##########

# Find and load json deployment config files.
json_data={}
paths=[Path(dirname) for dirname in jsondirs]
for path in paths:
    jfiles=[filepath
            for filepath in path.glob('**/*.json')
            if filepath.is_file()]
    for jfile in jfiles:
        key=key_from_path(jfile, relative_to=path)
        with jfile.open() as jf:
            json_data[key]=json.load(jf)

# Find configs that appear to contain CAN IDs.
with Path(csvfilename).open('w') as csvfile:
    print(f"Writing to {csvfile.name}")
    outcsv=csv.writer(csvfile)
    outcsv.writerow(['KeyPath', 'ShortKey', 'Value']) # Header

    for (key, value) in find_in_nested_dict(json_data, 'id'):
        keypath=key.copy()
        keypath='.'.join(keypath)
        
        shortkey=key.copy()
        shortkey.pop() # Always 'id'; discard.
        shortkey='.'.join(shortkey)
        shortkey=shorten_string(shortkey)

        outcsv.writerow([keypath, shortkey, value])

# Warn for unimplemented documentation.
for (key, value) in find_in_nested_dict(json_data, 'canbus'):
    if value is not None:
        warn(f"Config contains non-default CAN bus at: {str(key)}\n"
             f"\tThis script doesn't document that!")

# TODO: Process any java Constants that specify CAN IDs.
