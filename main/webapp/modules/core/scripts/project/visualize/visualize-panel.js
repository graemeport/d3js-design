/*

 */

function VisualizePanel(div, dataviewdiv) {
  this._div = div;
  this._dataviewdiv = dataviewdiv;
  
  this._div.html(DOM.loadHTML("core", "scripts/project/visualize/visualize-panel.html"));
  this._elmts = DOM.bind(this._div);
  
  this.resize();
  
}

VisualizePanel.prototype.resize = function() {
  var self = this;
  
  var width = this._div.width();
  var height = this._div.height();
  var controlPanelHeight = 150;
	  
  this._elmts.visualizeDisplayPanel
  .css("left", "0px")
  .css("top", "0px")
  .css("width", width + "px")
  .css("height", (height - controlPanelHeight) + "px");
	  
  this._elmts.visualizeControlPanel
  .css("left", "0px")
  .css("top", (height - controlPanelHeight) + "px")
  .css("width", width + "px")
  .css("height", controlPanelHeight + "px");
};

VisualizePanel.prototype.showVisualize = function() {
	this._dataviewdiv.hide();
	this._div.show();
}

VisualizePanel.prototype.hideVisualize = function() {
	this._dataviewdiv.show();
	this._div.hide();
}