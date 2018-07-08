'use strict';
var astUtil = require('@schematics/angular/utility/ast-utils');
var Change = require('@schematics/angular/utility/change');
var schematics = require('@angular-devkit/schematics');
var fs = require('fs');
var ts = require('typescript');
var findModule = require('@schematics/angular/utility/find-module');
var path = require('path');
const args = require('yargs').argv;

function applyChanges(path, content, changes) {
  const tree = new schematics.FileSystemTree()
  tree.create(path, content);
  const exportRecorder = tree.beginUpdate(path);
  for (const change of changes) {
    if (change instanceof Change.InsertChange) {
      exportRecorder.insertLeft(change.pos, change.toAdd);
    }
  }
  tree.commitUpdate(exportRecorder);
  const fileEntry = tree.get(path);
  if (!fileEntry) {
    throw new Error(`The file (${path}) does not exist.`);
  }
  return fileEntry.content.toString();
}

var moduleContent = fs.readFileSync(args['module-path'], 'UTF-8');
var tsSource = ts.createSourceFile(args['module-name'], moduleContent, ts.ScriptTarget.ES5, true);

if(args['component-path']) {
	var importPath = path.parse(findModule.buildRelativePath(args['module-path'], args['component-path']));
	var changes = astUtil.addDeclarationToModule(tsSource, args['module-path'], args['component-name'], `${importPath.dir}/${importPath.name}`);
	var out = applyChanges(args['module-path'],moduleContent,changes);
  fs.writeFileSync(args['module-path'], out, 'UTF-8');
  return;
}

if(args['service-path']) {
	var importPath = path.parse(findModule.buildRelativePath(args['module-path'], args['service-path']));
	var changes = astUtil.addProviderToModule(tsSource, args['module-path'], args['service-name'], `${importPath.dir}/${importPath.name}`);
	var out = applyChanges(args['module-path'],moduleContent,changes);
  fs.writeFileSync(args['module-path'], out, 'UTF-8');
  return;
}


