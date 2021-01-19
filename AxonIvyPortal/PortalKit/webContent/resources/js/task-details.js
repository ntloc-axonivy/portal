var taskDetailsGrid;
$(function () {
  setTimeout(() => {
    loadTaskDetailsGrid();
  }, 0);
})

function loadTaskDetailsGrid() {
  initTaskDetailsGrid();
  saveChangedPosition();
}

function initTaskDetailsGrid() {
  taskDetailsGrid = GridStack.init({
    column: 12,
    resizable: {
      handles: "e, se, s, sw, w"
    },
    alwaysShowResizeHandle: /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)
  });
}

function saveChangedPosition() {
  if (!taskDetailsGrid) {
    return;
  }

  taskDetailsGrid.on("change", function () {
    var serializedData = [];
    taskDetailsGrid.engine.nodes.forEach((node) => {
      let widgetType = getTaskDetailsWidgetType(node.el.getAttribute("widget-type"));
      serializedData.push({
        type: widgetType,
        id: node.id,
        axisX: node.x,
        axisY: node.y,
        width: node.width,
        height: node.height
      });
      if (widgetType === "document" || widgetType === "history") {
        responsiveATableInPanel(node.el);
      }
    });
    saveConfigurationCommand([{
      name: "nodes",
      value: JSON.stringify(serializedData, null, "")
    }]);
  });
}

function getTaskDetailsWidgetType(taskDetailsWidgetName) {
  let type = "";
  switch (taskDetailsWidgetName) {
    case "TaskDetailsInformationWidget": type = "information"; break;
    case "TaskDetailsDocumentWidget": type = "document"; break;
    case "TaskDetailsHistoryWidget": type = "history"; break;
    case "TaskDetailsCustomWidget": type = "custom"; break;
    default:
  }

  return type;
}

function responsiveATableInPanel(widgetElement) {
  let headerArr = widgetElement.getElementsByTagName("th");
  if (!headerArr || headerArr.length === 0) {
    return;
  }

  for (let i = headerArr.length - 1; i > 0; i--) {
    hideColumnWhenNotEnoughWidth(headerArr[i]);
  }
}

function hideColumnWhenNotEnoughWidth(element) {
  removeStyle(element, "width");
  let currentWidth = element.getBoundingClientRect().width;
  element.style.display = "initial";
  let fullWidth = element.getBoundingClientRect().width;
  removeStyle(element, "display");
  if (currentWidth < fullWidth) {
    element.style.width = 0;
  }
}

function removeStyle(element, styleName) {
  if (element.style.removeProperty) {
    element.style.removeProperty(styleName);
  } else {
    element.style.removeAttribute(styleName);
  }
}
