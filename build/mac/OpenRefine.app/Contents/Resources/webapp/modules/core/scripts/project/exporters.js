/*

Copyright 2010, Google Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

 * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above
copyright notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
 * Neither the name of Google Inc. nor the names of its
contributors may be used to endorse or promote products derived from
this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,           
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY           
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

function ExporterManager(button) {
  this._button = button;
  this._initializeUI();
}

ExporterManager.handlers = {};

ExporterManager.MenuItems = [
  {
    "id" : "core/export-project",
    "label": "Export project",
    "click": function() { ExporterManager.handlers.exportProject(); }
  },
  {},
  {
    "id" : "core/export-tsv",
    "label": "Tab-separated value",
    "click": function() { ExporterManager.handlers.exportRows("tsv", "tsv"); }
  },
  {
    "id" : "core/export-csv",
    "label": "Comma-separated value",
    "click": function() { ExporterManager.handlers.exportRows("csv", "csv"); }
  },
  {
    "id" : "core/export-html-table",
    "label": "HTML table",
    "click": function() { ExporterManager.handlers.exportRows("html", "html"); }
  },
  {
	"id" : "core/export-json",
	"label": "JSON",
	"click": function() { ExporterManager.handlers.exportRows("json", "json"); }
  },
  {
    "id" : "core/export-excel",
    "label": "Excel",
    "click": function() { ExporterManager.handlers.exportRows("xls", "xls"); }
  },
  {
    "id" : "core/export-ods",
    "label": "ODF spreadsheet",
    "click": function() { ExporterManager.handlers.exportRows("ods", "ods"); }
  },
  {},
  {
    "id" : "core/export-tripleloader",
    "label": "Triple loader",
    "click": function() { ExporterManager.handlers.exportTripleloader("tripleloader"); }
  },
  {
    "id" : "core/export-mqlwrite",
    "label": "MQLWrite",
    "click": function() { ExporterManager.handlers.exportTripleloader("mqlwrite"); }
  },
  {},
  {
    "id" : "core/export-custom-tabular",
    "label": "Custom tabular exporter...",
    "click": function() { new CustomTabularExporterDialog(); }
  },
  {
    "id" : "core/export-templating",
    "label": "Templating...",
    "click": function() { new TemplatingExporterDialog(); }
  }
];

ExporterManager.prototype._initializeUI = function() {
  this._button.click(function(evt) {
    MenuSystem.createAndShowStandardMenu(
        ExporterManager.MenuItems,
        this,
        { horizontal: false }
    );

    evt.preventDefault();
    return false;
  });
};

ExporterManager.handlers.exportTripleloader = function(format) {
  if (!theProject.overlayModels.freebaseProtograph) {
    alert(
        "You haven't done any schema alignment yet,\nso there is no triple to export.\n\n" +
        "Use the Freebase > Edit Schema Alignment Skeleton...\ncommand to align your data with Freebase schemas first."
    );
  } else {
    ExporterManager.handlers.exportRows(format, "txt");
  }
};

ExporterManager.handlers.exportRows = function(format, ext) {
  var form = ExporterManager.prepareExportRowsForm(format, true, ext);
  $('<input />')
  .attr("name", "contentType")
  .attr("value", "application/x-unknown") // force download
  .appendTo(form);
  
  document.body.appendChild(form);

  window.open("about:blank", "refine-export");
  form.submit();

  document.body.removeChild(form);
};

ExporterManager.prepareExportRowsForm = function(format, includeEngine, ext) {
  var name = $.trim(theProject.metadata.name.replace(/\W/g, ' ')).replace(/\s+/g, '-');
  var form = document.createElement("form");
  $(form)
  .css("display", "none")
  .attr("method", "post")
  .attr("action", "command/core/export-rows/" + name + ((ext) ? ("." + ext) : ""))
  .attr("target", "refine-export");

  $('<input />')
  .attr("name", "project")
  .attr("value", theProject.id)
  .appendTo(form);
  $('<input />')
  .attr("name", "format")
  .attr("value", format)
  .appendTo(form);
  if (includeEngine) {
    $('<input />')
    .attr("name", "engine")
    .attr("value", JSON.stringify(ui.browsingEngine.getJSON()))
    .appendTo(form);
  }
  
  return form;
};

ExporterManager.handlers.exportProject = function() {
  var name = $.trim(theProject.metadata.name.replace(/\W/g, ' ')).replace(/\s+/g, '-');
  var form = document.createElement("form");
  $(form)
  .css("display", "none")
  .attr("method", "post")
  .attr("action", "command/core/export-project/" + name + ".openrefine.tar.gz")
  .attr("target", "refine-export");
  $('<input />')
  .attr("name", "project")
  .attr("value", theProject.id)
  .appendTo(form);

  document.body.appendChild(form);

  window.open("about:blank", "refine-export");
  form.submit();

  document.body.removeChild(form);
};
