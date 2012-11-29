/*


 */

function VisualizeManager(panel) {
  var thepanel = panel;
  var visualizeButton = $("#visualize-button");
  var updateButton = $("#update-display-button");
  var returnButton = $("#return-to-data-button");

  visualizeButton.click(function (evt) { thepanel.showVisualize(); return false;});
  returnButton.click(function (evt) { thepanel.hideVisualize(); return false;} );

  var updateDisplay = function() {
	  var form = document.createElement("form");
	  $(form)
	  .css("display", "none")
	  .attr("method", "post")
	  .attr("action", "command/core/visualise-project)
	  .attr("target", "refine-visualize");
	  $('<input />')
	  .attr("name", "project")
	  .attr("value", theProject.id)
	  .appendTo(form);

	  document.body.appendChild(form);

	  window.open("about:blank", "refine-export");
	  form.submit();
	  
	  window.open("about:blank", "refine-export");
	  form.submit();

	  document.body.removeChild(form);
	  
	  return false;
	};

  updateButton.click(updateDisplay);
	
}

VisualizeManager.updateDisplay = function() {
  var form = document.createElement("form");
  $(form)
  .css("display", "none")
  .attr("method", "post")
  .attr("action", "command/core/visualise-project)
  .attr("target", "refine-visualize");
  $('<input />')
  .attr("name", "project")
  .attr("value", theProject.id)
  .appendTo(form);

  document.body.appendChild(form);

  window.open("about:blank", "refine-export");
  form.submit();
  
  window.open("about:blank", "refine-export");
  form.submit();

  document.body.removeChild(form);
};

