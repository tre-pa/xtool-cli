"use strict";
exports.__esModule = true;
/**
 * An operation that does nothing.
 */
var NoopChange = /** @class */ (function () {
    function NoopChange() {
        this.description = 'No operation.';
        this.order = Infinity;
        this.path = null;
    }
    NoopChange.prototype.apply = function () { return Promise.resolve(); };
    return NoopChange;
}());
exports.NoopChange = NoopChange;
/**
 * Will add text to the source code.
 */
var InsertChange = /** @class */ (function () {
    function InsertChange(path, pos, toAdd) {
        this.path = path;
        this.pos = pos;
        this.toAdd = toAdd;
        if (pos < 0) {
            throw new Error('Negative positions are invalid');
        }
        this.description = "Inserted " + toAdd + " into position " + pos + " of " + path;
        this.order = pos;
    }
    /**
     * This method does not insert spaces if there is none in the original string.
     */
    InsertChange.prototype.apply = function (host) {
        var _this = this;
        return host.read(this.path).then(function (content) {
            var prefix = content.substring(0, _this.pos);
            var suffix = content.substring(_this.pos);
            return host.write(_this.path, "" + prefix + _this.toAdd + suffix);
        });
    };
    return InsertChange;
}());
exports.InsertChange = InsertChange;
/**
 * Will remove text from the source code.
 */
var RemoveChange = /** @class */ (function () {
    function RemoveChange(path, pos, toRemove) {
        this.path = path;
        this.pos = pos;
        this.toRemove = toRemove;
        if (pos < 0) {
            throw new Error('Negative positions are invalid');
        }
        this.description = "Removed " + toRemove + " into position " + pos + " of " + path;
        this.order = pos;
    }
    RemoveChange.prototype.apply = function (host) {
        var _this = this;
        return host.read(this.path).then(function (content) {
            var prefix = content.substring(0, _this.pos);
            var suffix = content.substring(_this.pos + _this.toRemove.length);
            // TODO: throw error if toRemove doesn't match removed string.
            return host.write(_this.path, "" + prefix + suffix);
        });
    };
    return RemoveChange;
}());
exports.RemoveChange = RemoveChange;
/**
 * Will replace text from the source code.
 */
var ReplaceChange = /** @class */ (function () {
    function ReplaceChange(path, pos, oldText, newText) {
        this.path = path;
        this.pos = pos;
        this.oldText = oldText;
        this.newText = newText;
        if (pos < 0) {
            throw new Error('Negative positions are invalid');
        }
        this.description = "Replaced " + oldText + " into position " + pos + " of " + path + " with " + newText;
        this.order = pos;
    }
    ReplaceChange.prototype.apply = function (host) {
        var _this = this;
        return host.read(this.path).then(function (content) {
            var prefix = content.substring(0, _this.pos);
            var suffix = content.substring(_this.pos + _this.oldText.length);
            var text = content.substring(_this.pos, _this.pos + _this.oldText.length);
            if (text !== _this.oldText) {
                return Promise.reject(new Error("Invalid replace: \"" + text + "\" != \"" + _this.oldText + "\"."));
            }
            // TODO: throw error if oldText doesn't match removed string.
            return host.write(_this.path, "" + prefix + _this.newText + suffix);
        });
    };
    return ReplaceChange;
}());
exports.ReplaceChange = ReplaceChange;
