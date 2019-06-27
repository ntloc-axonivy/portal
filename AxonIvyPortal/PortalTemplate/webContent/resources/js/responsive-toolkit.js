 "use strict";
var animateDuration = 0;
function ResponsiveToolkit(largeScreenToolkit, mediumScreenToolkit, smallScreenToolkit) {
  var VALID_METHOD = "updateMainContainer";
  
  function validateArguments(){
	  
	  var errorMessageFormat = "{0} have to implement {1} method";
	  
	  if(!largeScreenToolkit.hasOwnProperty(VALID_METHOD)){
		  throw Error(errorMessageFormat.replace("{0}", "largeScreenToolkit").replace("{1}", VALID_METHOD));
	  }
	  
	  if(!mediumScreenToolkit.hasOwnProperty(VALID_METHOD)){
		  throw Error(errorMessageFormat.replace("{0}", "mediumScreenToolkit").replace("{1}", VALID_METHOD));
		  
	  }
	  
	  if(!smallScreenToolkit.hasOwnProperty(VALID_METHOD)){
		  throw Error(errorMessageFormat.replace("{0}", "smallScreenToolkit").replace("{1}", VALID_METHOD));
	  }
  }
	
  function updateLayout() {
	validateArguments();
	if (viewPort.isLargeScreen()) {
		largeScreenToolkit.updateMainContainer();
	}
	    
	if (viewPort.isMediumScreen()) {
	    mediumScreenToolkit.updateMainContainer();
	}
	    
	if (viewPort.isSmallScreen()) {
	    smallScreenToolkit.updateMainContainer();
	}
    
  }
  
  return {
    updateLayoutWithAnimation : function () {
      animateDuration = 800;
      updateLayout();
    },
    
    updateLayoutWithoutAnimation: function(){
    	animateDuration = 0;
    	updateLayout();
    }
  };
};

/***************************Handle responsive for Task list**********************************/
function TaskListLargeScreenHandler() {
    var marginValues = {
      marginValWhenAllMenuClose : 70,
      marginValWhenMainMenuOpen : 210,
      marginValWhenSecondMenuOpen : 280,
      marginValWhenAllMenuOpen : 410,
    }

    
    function animateTaskList($widget, properties) {
      $widget.animate(properties, animateDuration, function() {
        var taskListToolKit = TaskListToolKit();
          taskListToolKit.responsive();
      });
   }
    
    this.updateMainContainer = function(){
      var $mainMenu = $('.js-left-sidebar');
      var $secondLevelMenu = $('#second-level-menu');
      var $announcement = $('.js-announcement-message');
      var $taskListContainer = $('.js-task-list-container');
      var isDisplaySecondLevelMenu = $secondLevelMenu.hasClass('on') && !$secondLevelMenu.hasClass('hide');

      var isSearchResultPage = $taskListContainer.hasClass('js-for-search-result');

      if ($mainMenu.hasClass('in')) {
        if (isDisplaySecondLevelMenu) {
          animateTaskList($announcement, { marginLeft : marginValues.marginValWhenAllMenuOpen });
          animateTaskList($taskListContainer, { marginLeft : marginValues.marginValWhenAllMenuOpen });
        } else {
          animateTaskList($announcement, { marginLeft : marginValues.marginValWhenMainMenuOpen });
          animateTaskList($taskListContainer, { marginLeft : marginValues.marginValWhenMainMenuOpen });  
          if (isSearchResultPage) {
            var simpleLargeScreen = new SimpleLargeScreen();
            simpleLargeScreen.updateMainContainer();
          }
        }
      } else {
        if (isDisplaySecondLevelMenu) {
          animateTaskList($announcement, { marginLeft : marginValues.marginValWhenSecondMenuOpen });
          animateTaskList($taskListContainer, { marginLeft : marginValues.marginValWhenSecondMenuOpen });
        } else {
          animateTaskList($announcement, { marginLeft : marginValues.marginValWhenAllMenuClose });
          animateTaskList($taskListContainer, { marginLeft : marginValues.marginValWhenAllMenuClose });
          if (isSearchResultPage) {
            var simpleLargeScreen = new SimpleLargeScreen();
            simpleLargeScreen.updateMainContainer();
          }
        }
      }
    }
}

function TaskListMediumScreenHandler() {
     var marginValues = {
        marginValWhenAllMenuClose : 60,
        marginValWhenMainMenuOpen : 190,
        marginValWhenSecondMenuOpen : 260,
        marginValWhenAllMenuOpen : 380,
    }

    function animateTaskList($widget, properties) {
        $widget.animate(properties, animateDuration, function() {
        	var taskListToolKit = TaskListToolKit();
            taskListToolKit.responsiveInMediumScreen();
        });
     }

    this.updateMainContainer = function(){
        var $mainMenu = $('.js-left-sidebar');
        var $secondLevelMenu = $('#second-level-menu');
        var $announcement = $('.js-announcement-message');
        var $taskListContainer = $('.js-task-list-container');
        var isSecondLevelMenuOpen = $secondLevelMenu.hasClass('on') && !$secondLevelMenu.hasClass('hide');

        var isSearchResultPage = $taskListContainer.hasClass('js-for-search-result');

        if ($mainMenu.hasClass('in')) {
          if (isSecondLevelMenuOpen) {
              // Open main menu when second menu is opened
            animateTaskList($announcement, {marginLeft : marginValues.marginValWhenAllMenuOpen});
            animateTaskList($taskListContainer, {marginLeft : marginValues.marginValWhenAllMenuOpen});
          } else {
              // Open main menu when second menu is closed
            animateTaskList($announcement, {marginLeft : marginValues.marginValWhenMainMenuOpen});
            animateTaskList($taskListContainer, {marginLeft : marginValues.marginValWhenMainMenuOpen});
            if (isSearchResultPage) {
              var simpleMediumScreen = new SimpleMediumScreen();
              simpleMediumScreen.updateMainContainer();
            }
          }
        } else {
          if (isSecondLevelMenuOpen) {
            // Close main menu when second menu is opened
            animateTaskList($announcement, {marginLeft : marginValues.marginValWhenSecondMenuOpen});
            animateTaskList($taskListContainer, {marginLeft : marginValues.marginValWhenSecondMenuOpen});
          } else {
            // Close main menu when second menu is closed
            animateTaskList($announcement, {marginLeft : marginValues.marginValWhenAllMenuClose});
            animateTaskList($taskListContainer, {marginLeft : marginValues.marginValWhenAllMenuClose});
            if (isSearchResultPage) {
              var simpleMediumScreen = new SimpleMediumScreen();
              simpleMediumScreen.updateMainContainer();
            }
          }
        }
        
        var isTaskDetailsOpened = $('.js-task-details').length !== 0 ? true : false;
        if (isTaskDetailsOpened) {
          updateTaskDetails();
        }
    }
    
    function updateTaskDetails() {
        var replacedClass = 'replaced';
        var isSelectedClass = 'is-selected';
        var openedClass = 'opened';
        var $mainMenu = $('.js-left-sidebar');
        var $secondLevelMenu = $('#second-level-menu');
        var isDisplaySecondLevelMenu = $secondLevelMenu.hasClass('on') && !$secondLevelMenu.hasClass('hide');
        var $taskItem = taskItem();
        var $responsiveHandleContainer = $('.js-responsive-handle-container', $taskItem);
        var $itemColumns = $('.js-task-details-item', $taskItem);
        var $dataResponsiveButton = $('.js-data-column-responsive-button', $taskItem);
        var $responsiveButtons = $('.js-responsive-handle-button', $taskItem);
        var $taskDetails = $('.js-task-details', $taskItem);
        var isAlreadyLoaded = $taskDetails.hasClass(openedClass);
        if (!isAlreadyLoaded) {
          var $documentColumn = $('.js-document-column', $taskItem);
          $documentColumn.addClass(replacedClass);
        }
        var $hiddenColumn = $('.js-task-details-item:not(.' + replacedClass + '):last', $taskItem);

        $itemColumns.show().css('opacity', 1);
        $dataResponsiveButton.hide();
        if ($mainMenu.hasClass('in') || isDisplaySecondLevelMenu) {
          $responsiveHandleContainer.show();
          $hiddenColumn.hide();
          $responsiveButtons.removeClass(isSelectedClass);
          responsiveButton($('.' + replacedClass, $taskItem), $taskItem).addClass(isSelectedClass);
        } else {
          $responsiveHandleContainer.hide();
          $itemColumns.show().css('opacity', 1);
          $responsiveButtons.removeClass(isSelectedClass);
        }
        
        $taskDetails.addClass(openedClass);
        if (!$dataResponsiveButton.is(":visible") && $dataResponsiveButton.hasClass(isSelectedClass)) {
          var $documentResponsiveButton = $('.js-document-column-responsive-button', $taskItem);
          $documentResponsiveButton.addClass(isSelectedClass);
        }
      }
    
    function taskItem() {
        var $taskItem = $('.show-task-details-mode').has('.js-task-details:not(.opened)');
        if ($taskItem.length === 0) {
          $taskItem = $('.show-task-details-mode');
        }
        return $taskItem;
      }
      
      function responsiveButton($itemColumn, $taskItem) {
        var theClass = $itemColumn.attr('class').match(/js[\w-]*[\w-]column\b/);
        return $('.' + theClass + '-responsive-button', $taskItem);
      }
};

function TaskListSmallScreenHandler() {
    var marginValues = {
        marginValWhenAllMenuClose : 60,
        marginValWhenMainMenuOpen : 190,
        marginValWhenSecondMenuOpen : 260,
        marginValForLogoWhenAllMenuOpen : 310,
        marginValWhenAllMenuOpen : 380,
    }

    function animateTaskList($widget, properties) {
        $widget.animate(properties, animateDuration, function() {
        	var taskListToolKit = TaskListToolKit();
        	taskListToolKit.responsiveInSmallScreen();
        });
     }

    this.updateMainContainer = function(){
        var $mainMenu = $('.js-left-sidebar');
        var $secondLevelMenu = $('#second-level-menu');
        var $announcement = $('.js-announcement-message');
        var $taskListContainer = $('.js-task-list-container');
        var $logo = $('.js-logo');
        var isSecondLevelMenuOpen = $secondLevelMenu.hasClass('on') && !$secondLevelMenu.hasClass('hide');

        var isSearchResultPage = $taskListContainer.hasClass('js-for-search-result');

        if ($mainMenu.hasClass('in')) {
          if (isSecondLevelMenuOpen) {
            // Open main menu when second menu is opened
            animateTaskList($announcement, {marginLeft : marginValues.marginValWhenAllMenuOpen});
            animateTaskList($taskListContainer, {marginLeft : marginValues.marginValWhenAllMenuOpen});
            animateTaskList($logo, {marginLeft : marginValues.marginValForLogoWhenAllMenuOpen});
          } else {
            // Open main menu when second menu is closed
            animateTaskList($announcement, {marginLeft : marginValues.marginValWhenMainMenuOpen});
            animateTaskList($taskListContainer, {marginLeft : marginValues.marginValWhenMainMenuOpen});
            animateTaskList($logo, {marginLeft : marginValues.marginValWhenMainMenuOpen});
            if (isSearchResultPage) {
              var simpleSmallScreen = new SimpleSmallScreen();
              simpleSmallScreen.updateMainContainer();
            }
          }
        } else {
          if (isSecondLevelMenuOpen) {
            // Close main menu when second menu is opened
            animateTaskList($announcement, {marginLeft : marginValues.marginValWhenSecondMenuOpen});
            animateTaskList($taskListContainer, {marginLeft : marginValues.marginValWhenSecondMenuOpen});
            animateTaskList($logo, {marginLeft : marginValues.marginValWhenMainMenuOpen});
          } else {
            // Close main menu when second menu is closed
            animateTaskList($announcement, {marginLeft : marginValues.marginValWhenAllMenuClose});
            animateTaskList($taskListContainer, {marginLeft : marginValues.marginValWhenAllMenuClose});
            animateTaskList($logo, {marginLeft : marginValues.marginValWhenAllMenuClose});
            if (isSearchResultPage) {
              var simpleSmallScreen = new SimpleSmallScreen();
              simpleSmallScreen.updateMainContainer();
            }
          }
        }
        
        var isTaskDetailsOpened = $('.js-task-details').length !== 0 ? true : false;
        if (isTaskDetailsOpened) {
          updateTaskDetails(isSearchResultPage);
        }
    }
    
    function updateTaskDetails(isSearchResultPage) {
        typeof(isSearchResultPage) == "undefined" ? false : isSearchResultPage;
        var replacedClass = 'replaced';
        var isSelectedClass = 'is-selected';
        var openedClass = 'opened';
        var $mainMenu = $('.js-left-sidebar');
        var $secondLevelMenu = $('#second-level-menu');
        var isDisplaySecondLevelMenu = $secondLevelMenu.hasClass('on') && !$secondLevelMenu.hasClass('hide');
        var $taskItem = taskItem();
        var $responsiveHandleContainer = $('.js-responsive-handle-container', $taskItem);
        var $itemColumns = $('.js-task-details-item', $taskItem);
        var $responsiveButtons = $('.js-responsive-handle-button', $taskItem);
        var $dataResponsiveButton = $('.js-data-column-responsive-button', $taskItem);
        var $dataColumn = $('.js-data-column', $taskItem);
        var $documentColumn = $('.js-document-column', $taskItem);
        
        var $taskDetails = $('.js-task-details', $taskItem);
        var isAlreadyLoaded = $taskDetails.hasClass(openedClass);
        if (!isAlreadyLoaded) {
          if ($mainMenu.hasClass('in') || isDisplaySecondLevelMenu || isSearchResultPage) {
            $dataColumn.addClass(replacedClass);
          } else {
            $documentColumn.addClass(replacedClass);
            $dataResponsiveButton.hide();
          }
        }
        
        var $hiddenColumns = $('.js-task-details-item:not(.' + replacedClass + '):last', $taskItem);
        $responsiveHandleContainer.show();
        if ($mainMenu.hasClass('in') || isDisplaySecondLevelMenu || isSearchResultPage) {
          // Display data column as default when the menu state is changed from no expanded menu to one expanded menu
          var isResponsiveButtonClicked = $responsiveButtons.hasClass('is-clicked');
          if (!isResponsiveButtonClicked) {
            $itemColumns.removeClass(replacedClass);
            $responsiveButtons.removeClass(isSelectedClass);
            $dataColumn.addClass(replacedClass);
          }
          $hiddenColumns = $('.js-task-details-item:not(.' + replacedClass + ')', $taskItem).slice(-2);
          $dataResponsiveButton.show();
        } else {
          $('.js-task-details-item:not(.' + replacedClass + '):nth-child(2)', $taskItem).show().css('opacity', 1);
          responsiveButton($dataColumn, $taskItem).hide();
          if ($dataColumn.hasClass(replacedClass)) {
            $dataColumn.removeClass(replacedClass);
            $documentColumn.addClass(replacedClass);
            $documentColumn.show().css('opacity', 1);
            responsiveButton($documentColumn, $taskItem).addClass(isSelectedClass);
          }
        }
        $hiddenColumns.hide();
        $responsiveButtons.removeClass(isSelectedClass);
        responsiveButton($('.' + replacedClass, $taskItem), $taskItem).addClass(isSelectedClass);
        $taskDetails.addClass(openedClass);
        
        if (($mainMenu.hasClass('in') && isDisplaySecondLevelMenu) || (!$mainMenu.hasClass('in') && !isDisplaySecondLevelMenu)) {
          $('.js-task-details-item:first').css('margin-left', 0);
        } else {
          $('.js-task-details-item:first').removeAttr("style");
        }
      }
      
      function taskItem() {
        var $taskItem = $('.show-task-details-mode').has('.js-task-details:not(.opened)');
        if ($taskItem.length === 0) {
          $taskItem = $('.show-task-details-mode');
        }
        return $taskItem;
      }
      
      function responsiveButton($itemColumn, $taskItem) {
        var theClass = $itemColumn.attr('class').match(/js[\w-]*[\w-]column\b/);
        return $('.' + theClass + '-responsive-button', $taskItem);
      }
};

/***************************Handle responsive for Simple Screen**********************************/	
function SimpleLargeScreen(){
  var marginValWhenMainMenuOpen = 210;
  var marginValWhenTwoMenuClose = 70;

  this.updateMainContainer = function() {
    var $mainMenu = $('.js-left-sidebar');
    var $announcement = $('.js-announcement-message');
    var $simpleMainColumn = $('.js-simple-main-col');

    if ($mainMenu.hasClass('in')) {
      $announcement.animate({ marginLeft : marginValWhenMainMenuOpen }, animateDuration);
      $simpleMainColumn.animate({ marginLeft : marginValWhenMainMenuOpen }, animateDuration);
    } else { 
      $announcement.animate({ marginLeft : marginValWhenTwoMenuClose }, animateDuration);
      $simpleMainColumn.animate({ marginLeft : marginValWhenTwoMenuClose }, animateDuration);
    }
  }
}

function SimpleMediumScreen() {
  var marginValWhenMainMenuOpen = 190;
  var marginValWhenTwoMenuClose = 70;

  this.updateMainContainer = function() {
    var $mainMenu = $('.js-left-sidebar');
    var $secondLevelMenu = $('#second-level-menu');
    var $announcement = $('.js-announcement-message');
    var $simpleMainColumn = $('.js-simple-main-col');

    if ($mainMenu.hasClass('in')) {
      $announcement.animate({marginLeft : marginValWhenMainMenuOpen}, animateDuration);
      $simpleMainColumn.animate({marginLeft : marginValWhenMainMenuOpen}, animateDuration);
    } else {
      $announcement.animate({marginLeft : marginValWhenTwoMenuClose}, animateDuration);
      $simpleMainColumn.animate({marginLeft : marginValWhenTwoMenuClose}, animateDuration);
    }
  }
}

function SimpleSmallScreen() {
  var marginValWhenMainMenuOpen = 130;
  var marginValWhenMenuClose = 0;

  this.updateMainContainer = function() {
    var $mainMenu = $('.js-left-sidebar');
    var $announcement = $('.js-announcement-message');
    var $simpleMainColumn = $('.js-simple-main-col');
    var $logo = $('.js-logo');

    if ($mainMenu.hasClass('in')) {
      $announcement.animate({marginLeft : marginValWhenMainMenuOpen}, animateDuration);
      $simpleMainColumn.animate({marginLeft : marginValWhenMainMenuOpen}, animateDuration);
      $logo.animate({marginLeft : marginValWhenMainMenuOpen}, animateDuration);
    } else {
      $announcement.animate({marginLeft : marginValWhenMenuClose}, animateDuration);
      $simpleMainColumn.animate({marginLeft : marginValWhenMenuClose}, animateDuration);
      $logo.animate({marginLeft : marginValWhenMenuClose}, animateDuration);
    }
  };
}

/***************************Handle responsive for Case List**********************************/
function CaseListLargeScreenHandler() {
  var marginValues = {
    marginValWhenAllMenuClose : 70,
    marginValWhenMainMenuOpen : 210,
    marginValWhenSecondMenuOpen : 280,
    marginValWhenAllMenuOpen : 410,
  }
  
  function animateCaseList($widget, properties) {
    $widget.animate(properties, animateDuration, function() {
      var caseListToolKit = CaseListToolKit();
      caseListToolKit.responsive();
    });
  }

  this.updateMainContainer = function(){
    var $mainMenu = $('.js-left-sidebar');
    var $secondLevelMenu = $('#second-level-menu');
    var $announcement = $('.js-announcement-message');
    var $caseListContainer = $('.js-case-default-widget-container');
    var isDisplaySecondLevelMenu = $secondLevelMenu.hasClass('on') && !$secondLevelMenu.hasClass('hide');

    var isSearchResultPage = $caseListContainer.hasClass('js-for-search-result');

    if ($mainMenu.hasClass('in')) {
      if (isDisplaySecondLevelMenu) {
        animateCaseList($announcement, { marginLeft : marginValues.marginValWhenAllMenuOpen });
        animateCaseList($caseListContainer, { marginLeft : marginValues.marginValWhenAllMenuOpen });
      } else {
        animateCaseList($announcement, { marginLeft : marginValues.marginValWhenMainMenuOpen });
        animateCaseList($caseListContainer, { marginLeft : marginValues.marginValWhenMainMenuOpen });
        if (isSearchResultPage) {
          var simpleLargeScreen = new SimpleLargeScreen();
          simpleLargeScreen.updateMainContainer();
        }
      }
    } else {
      if (isDisplaySecondLevelMenu) {
        animateCaseList($announcement, { marginLeft : marginValues.marginValWhenSecondMenuOpen });
        animateCaseList($caseListContainer, { marginLeft : marginValues.marginValWhenSecondMenuOpen });
      } else {
        animateCaseList($announcement, { marginLeft : marginValues.marginValWhenAllMenuClose });
        animateCaseList($caseListContainer, { marginLeft : marginValues.marginValWhenAllMenuClose });
        if (isSearchResultPage) {
          var simpleLargeScreen = new SimpleLargeScreen();
          simpleLargeScreen.updateMainContainer();
        }
      }
    }
  }
}

function CaseListMediumScreenHandler() {
  var marginValues = {
    marginValWhenAllMenuClose : 60,
    marginValWhenMainMenuOpen : 190,
    marginValWhenSecondMenuOpen : 260,
    marginValWhenAllMenuOpen : 380,
  }

  function animateCaseList($widget, properties) {
    $widget.animate(properties, animateDuration, function() {
      var caseListToolKit = CaseListToolKit();
      caseListToolKit.responsiveInMediumScreen();
    });
  }

  this.updateMainContainer = function(){
    var $mainMenu = $('.js-left-sidebar');
    var $secondLevelMenu = $('#second-level-menu');
    var $announcement = $('.js-announcement-message');
    var $caseListContainer = $('.js-case-default-widget-container');
    var isDisplaySecondLevelMenu = $secondLevelMenu.hasClass('on') && !$secondLevelMenu.hasClass('hide');

    var isSearchResultPage = $caseListContainer.hasClass('js-for-search-result');

    if ($mainMenu.hasClass('in')) {
      if (isDisplaySecondLevelMenu) {
          // Open main menu when second menu is opened
        animateCaseList($announcement, {marginLeft : marginValues.marginValWhenAllMenuOpen});
        animateCaseList($caseListContainer, {marginLeft : marginValues.marginValWhenAllMenuOpen});
      } else {
          // Open main menu when second menu is closed
        animateCaseList($announcement, {marginLeft : marginValues.marginValWhenMainMenuOpen});
        animateCaseList($caseListContainer, {marginLeft : marginValues.marginValWhenMainMenuOpen});
        if (isSearchResultPage) {
          var simpleMediumScreen = new SimpleMediumScreen();
          simpleMediumScreen.updateMainContainer();
        }
      }
    } else {
        if (isDisplaySecondLevelMenu) {
            // Close main menu when second menu is opened
          animateCaseList($announcement, {marginLeft : marginValues.marginValWhenSecondMenuOpen});
          animateCaseList($caseListContainer, {marginLeft : marginValues.marginValWhenSecondMenuOpen});
        } else {
            // Close main menu when second menu is closed
          animateCaseList($announcement, {marginLeft : marginValues.marginValWhenAllMenuClose});
          animateCaseList($caseListContainer, {marginLeft : marginValues.marginValWhenAllMenuClose});
          if (isSearchResultPage) {
            var simpleMediumScreen = new SimpleMediumScreen();
            simpleMediumScreen.updateMainContainer();
          }
        }
    }

    var isCaseDetailsOpened = $('.js-case-details').length !== 0 ? true : false;
    if (isCaseDetailsOpened) {
      updateCaseDetails();
    }
  }

  function updateCaseDetails() {
    var replacedClass = 'replaced';
    var isSelectedClass = 'is-selected';
    var openedClass = 'opened';
    var $mainMenu = $('.js-left-sidebar');
    var $caseItem = caseItem();
    var $caseDetails = $('.js-case-details', $caseItem);

    var $responsiveButtons = $('.js-responsive-handle-button', $caseItem);
    var $relatedTaskResponsiveButton = $('.js-related-task-column-responsive-button', $caseItem);
    var $historyResponsiveButton = $('.js-history-column-responsive-button');

    var $itemColumns = $('.js-case-details-item', $caseItem);
    var $historyColumn = $('.js-history-column', $caseItem);
    var $documentColumn = $('.js-document-column', $caseItem);

    var isAlreadyLoaded = $caseDetails.hasClass(openedClass);

    if (!isAlreadyLoaded) {
      if ($mainMenu.hasClass('in')) {
        $historyColumn.addClass(replacedClass);
      } else {
        $documentColumn.addClass(replacedClass);
        $historyResponsiveButton.hide();
      }
    }

    var $hiddenColumn = $('.js-case-details-item:not(.' + replacedClass + ')', $caseItem).slice(-1);
    $itemColumns.show().css('opacity', 1);
    $relatedTaskResponsiveButton.hide();

    if ($mainMenu.hasClass('in')) {
      // Display data column as default when the menu state is changed from no expanded menu to one expanded menu
      var isResponsiveButtonClicked = $responsiveButtons.hasClass('is-clicked');
      if (!isResponsiveButtonClicked) {
        $itemColumns.removeClass(replacedClass);
        $responsiveButtons.removeClass(isSelectedClass);
        $historyColumn.addClass(replacedClass);
      }
      $hiddenColumn = $('.js-case-details-item:not(.' + replacedClass + ')', $caseItem).slice(-2);
      $historyResponsiveButton.show();
    } else {
      $('.js-case-details-item:not(.' + replacedClass + '):nth-child(2)', $caseItem).show().css('opacity', 1);
      responsiveButton($historyColumn, $caseItem).hide();
      if ($historyColumn.hasClass(replacedClass)) {
        $historyColumn.removeClass(replacedClass);
        $documentColumn.addClass(replacedClass);
        $documentColumn.show().css('opacity', 1);
        responsiveButton($historyColumn, $caseItem).addClass(isSelectedClass);
      }
    }

    $hiddenColumn.hide();
    $responsiveButtons.removeClass(isSelectedClass);
    responsiveButton($('.' + replacedClass, $caseItem), $caseItem).addClass(isSelectedClass);
    $caseDetails.addClass(openedClass);
  }

  function caseItem() {
    var $caseItem = $('.show-case-details-mode').has('.js-case-details:not(.opened)');
    if ($caseItem.length === 0) {
      $caseItem = $('.show-case-details-mode');
    }
    return $caseItem;
  }

  function responsiveButton($itemColumn, $caseItem) {
    var theClass = $itemColumn.attr('class').match(/js[\w-]*[\w-]column\b/);
    return $('.' + theClass + '-responsive-button', $caseItem);
  }
};

function CaseListSmallScreenHandler() {
  var marginValues = {
    marginValWhenAllMenuClose : 60,
    marginValWhenMainMenuOpen : 190,
    marginValWhenSecondMenuOpen : 260,
    marginValForLogoWhenAllMenuOpen : 310,
    marginValWhenAllMenuOpen : 380,
  }

  function animateCaseList($widget, properties) {
    $widget.animate(properties, animateDuration, function() {
      var caseListToolKit = CaseListToolKit();
      caseListToolKit.responsiveInSmallScreen();
    });
   }

  this.updateMainContainer = function(){
    var $mainMenu = $('.js-left-sidebar');
    var $secondLevelMenu = $('#second-level-menu');
    var $announcement = $('.js-announcement-message');
    var $caseListContainer = $('.js-case-default-widget-container');
    var $logo = $('.js-logo');
    var isDisplaySecondLevelMenu = $secondLevelMenu.hasClass('on') && !$secondLevelMenu.hasClass('hide');

    var isSearchResultPage = $caseListContainer.hasClass('js-for-search-result');

    if ($mainMenu.hasClass('in')) {
      if (isDisplaySecondLevelMenu) {
          // Open main menu when second menu is opened
        animateCaseList($announcement, {marginLeft : marginValues.marginValWhenAllMenuOpen});
        animateCaseList($caseListContainer, {marginLeft : marginValues.marginValWhenAllMenuOpen});
        animateCaseList($logo, {marginLeft : marginValues.marginValForLogoWhenAllMenuOpen});
      } else {
          // Open main menu when second menu is closed
        animateCaseList($announcement, {marginLeft : marginValues.marginValWhenMainMenuOpen});
        animateCaseList($caseListContainer, {marginLeft : marginValues.marginValWhenMainMenuOpen});
        animateCaseList($logo, {marginLeft : marginValues.marginValWhenMainMenuOpen});
        if (isSearchResultPage) {
          var simpleSmallScreen = new SimpleSmallScreen();
          simpleSmallScreen.updateMainContainer();
        }
      }
    } else {
        if (isDisplaySecondLevelMenu) {
            // Close main menu when second menu is opened
          animateCaseList($announcement, {marginLeft : marginValues.marginValWhenSecondMenuOpen});
          animateCaseList($caseListContainer, {marginLeft : marginValues.marginValWhenSecondMenuOpen});
          animateCaseList($logo, {marginLeft : marginValues.marginValWhenMainMenuOpen});
        } else {
            // Close main menu when second menu is closed
          animateCaseList($announcement, {marginLeft : marginValues.marginValWhenAllMenuClose});
          animateCaseList($caseListContainer, {marginLeft : marginValues.marginValWhenAllMenuClose});
          animateCaseList($logo, {marginLeft : marginValues.marginValWhenAllMenuClose});
          if (isSearchResultPage) {
            var simpleSmallScreen = new SimpleSmallScreen();
            simpleSmallScreen.updateMainContainer(isSearchResultPage);
          }
        }
    }

    var isCaseDetailsOpened = $('.js-case-details').length !== 0 ? true : false;
    if (isCaseDetailsOpened) {
      updateCaseDetails(isSearchResultPage);
    }
  }

  function updateCaseDetails(isSearchResultPage) {
    typeof(isSearchResultPage) == "undefined" ? false : isSearchResultPage;
    var replacedClass = 'replaced';
    var isSelectedClass = 'is-selected';
    var openedClass = 'opened';
    var $mainMenu = $('.js-left-sidebar');
    var $caseItem = caseItem();
    var $caseDetails = $('.js-case-details', $caseItem);
    var $responsiveHandleContainer = $('.js-responsive-handle-container', $caseItem);

    var $itemColumns = $('.js-case-details-item', $caseItem);
    var $historyColumn = $('.js-history-column', $caseItem);
    var $relatedTaskColumn = $('.js-related-task-column', $caseItem);

    var $responsiveButtons = $('.js-responsive-handle-button', $caseItem);
    var $relatedTaskResponsiveButton = $('.js-related-task-column-responsive-button', $caseItem);
    var $historyResponsiveButton = $('.js-history-column-responsive-button');
    var $secondLevelMenu = $('#second-level-menu');
    var isDisplaySecondLevelMenu = $secondLevelMenu.hasClass('on') && !$secondLevelMenu.hasClass('hide');
    var isAlreadyLoaded = $caseDetails.hasClass(openedClass);

    if (!isAlreadyLoaded) {
      if ($mainMenu.hasClass('in') || isSearchResultPage) {
        $relatedTaskColumn.addClass(replacedClass);
      } else {
        $historyColumn.addClass(replacedClass);
        $relatedTaskResponsiveButton.hide();
      }
    }

    var $hiddenColumns = $('.js-case-details-item:not(.' + replacedClass + ')', $caseItem).slice(-2);
    $responsiveHandleContainer.show();

    if ($mainMenu.hasClass('in') || isDisplaySecondLevelMenu || isSearchResultPage) {
      // Display data column as default when the menu state is changed from no expanded menu to one expanded menu
      var isResponsiveButtonClicked = $responsiveButtons.hasClass('is-clicked');
      if (!isResponsiveButtonClicked) {
        $itemColumns.removeClass(replacedClass);
        $responsiveButtons.removeClass(isSelectedClass);
        $relatedTaskColumn.addClass(replacedClass);
      }
      $hiddenColumns = $('.js-case-details-item:not(.' + replacedClass + ')', $caseItem).slice(-3);
      $relatedTaskResponsiveButton.show();
    } else {
      $('.js-case-details-item:not(.' + replacedClass + '):nth-child(2)', $caseItem).show().css('opacity', 1);
      responsiveButton($relatedTaskColumn, $caseItem).hide();
      if ($relatedTaskColumn.hasClass(replacedClass)) {
        $relatedTaskColumn.removeClass(replacedClass);
        $historyColumn.addClass(replacedClass);
        $historyColumn.show().css('opacity', 1);
        responsiveButton($relatedTaskColumn, $caseItem).addClass(isSelectedClass);
      }
    }

    $hiddenColumns.hide();
    $responsiveButtons.removeClass(isSelectedClass);
    responsiveButton($('.' + replacedClass, $caseItem), $caseItem).addClass(isSelectedClass);
    $caseDetails.addClass(openedClass);

    
    if ($historyColumn.css('display') === 'none' && $historyResponsiveButton.css('display') === 'none') {
      $historyResponsiveButton.show();
    }

    $('.js-case-details-item:first').css('margin-left', 0);
    if (!$mainMenu.hasClass('in')) {
      $caseDetails.removeAttr("style");
    }
  }

  function caseItem() {
    var $caseItem = $('.show-case-details-mode').has('.js-case-details:not(.opened)');
    if ($caseItem.length === 0) {
      $caseItem = $('.show-case-details-mode');
    }
    return $caseItem;
  }

  function responsiveButton($itemColumn, $caseItem) {
    var theClass = $itemColumn.attr('class').match(/js[\w-]*[\w-]column\b/);
    return $('.' + theClass + '-responsive-button', $caseItem);
  }
};

/***************************Handle responsive for Dashboard**********************************/
function DashboardLargeScreen() {
  var marginValWhenMainMenuOpen = 210;
  var marginValWhenTwoMenuClose = 70;

  this.updateMainContainer = function() {
    var $mainMenu = $('.js-left-sidebar');
    var $announcement = $('.js-announcement-message');
    var $dashboard = $('.js-dashboard-default-widget-container');

    if ($mainMenu.hasClass('in')) {
      $announcement.animate({ marginLeft : marginValWhenMainMenuOpen }, animateDuration);
      $dashboard.animate({ marginLeft : marginValWhenMainMenuOpen }, animateDuration);
    } else {
      $announcement.animate({ marginLeft : marginValWhenTwoMenuClose }, animateDuration);
      $dashboard.animate({ marginLeft : marginValWhenTwoMenuClose }, animateDuration);
    }
  }
}

function DashboardMediumScreen() {
  var firstCol = {
    marginValWhenMainMenuOpen : 190,
    marginValWhenMainMenuClose : 70
  };

  this.updateMainContainer = function() {
    updateDashboard();
  }

  function updateDashboard() {
    var $mainMenu = $('.js-left-sidebar');
    var $announcement = $('.js-announcement-message');
    var $dashboard = $('.js-dashboard-default-widget-container');
    if ($mainMenu.hasClass('in')) {
      $announcement.animate({ marginLeft : firstCol.marginValWhenMainMenuOpen }, animateDuration);
      $dashboard.animate({ marginLeft : firstCol.marginValWhenMainMenuOpen }, animateDuration);
    } else {
      $announcement.animate({ marginLeft : firstCol.marginValWhenMainMenuClose }, animateDuration);
      $dashboard.animate({ marginLeft : firstCol.marginValWhenMainMenuClose }, animateDuration);
    }
  }
}

function DashboardSmallScreen() {
  var firstCol = {
    marginValWhenMenuOpen : 110,
    marginValWhenMenuClose : 0
  };

  var secondCol = {
    secondColumnSmallSize : 480,
    secondColumnLargeSize : 540,
    marginValWhenMenuOpen : 20,
    marginValWhenMenuClose : 45
  };

  this.updateMainContainer = function() {
    updateDashboard();
  }

  function moveStatisticsToFirstCol() {
    $('.js-dashboard-main-content-1st-col').append($('.js-statistic-widget'));
    $('.js-dashboard-main-content-3rd-col').addClass('u-hidden');
  }

  function updateDashboard() {
    var $mainMenu = $('.js-left-sidebar');
    var $announcement = $('.js-announcement-message');
    var $dashboardFirstCol = $('.js-dashboard-main-content-1st-col');
    var $dashboardSecondCol = $('.js-dashboard-main-content-2nd-col');
    var $logo = $('.js-logo');
    var paddingLeft = 10;
    moveStatisticsToFirstCol();
    if ($mainMenu.hasClass('in')) {
      // Open main menu
      $dashboardFirstCol.animate({ marginLeft : firstCol.marginValWhenMenuOpen }, animateDuration);
      $dashboardSecondCol.animate({ width : secondCol.secondColumnSmallSize, marginLeft : secondCol.marginValWhenMenuOpen }, animateDuration);
      $announcement.animate({ marginLeft : firstCol.marginValWhenMenuOpen + paddingLeft }, animateDuration);
      $logo.animate({ marginLeft : firstCol.marginValWhenMenuOpen + paddingLeft }, animateDuration);
    } else {
      // Close main menu
      $dashboardFirstCol.animate({ marginLeft : firstCol.marginValWhenMenuClose }, animateDuration);
      $dashboardSecondCol.animate({ width : secondCol.secondColumnLargeSize, marginLeft : secondCol.marginValWhenMenuClose }, animateDuration);
      $announcement.animate({ marginLeft : firstCol.marginValWhenMenuClose + paddingLeft }, animateDuration);
      $logo.animate({ marginLeft : firstCol.marginValWhenMenuClose + paddingLeft }, animateDuration);
    }
  }
}