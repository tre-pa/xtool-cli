'use strict';
var astUtil = require('@schematics/angular/utility/ast-utils');
var Change = require('@schematics/angular/utility/change');
var schematics = require('@angular-devkit/schematics');
var fs = require('fs');
var ts = require('typescript');
const args = require('yargs').argv;