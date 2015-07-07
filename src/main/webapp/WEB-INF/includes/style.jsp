
<!-- CSS for Menu -->
<link rel="stylesheet" type="text/css" href="<@s.url value='/common/yui/menu/assets/menu.css'/>" />
<link rel="stylesheet" type="text/css" href="<@s.url value='/common/yui/reset/reset-min.css'/>" />
<link rel="stylesheet" type="text/css" href="<@s.url value='/common/yui/fonts/fonts-min.css'/>" />
<!-- CSS for Panels -->
<link rel="stylesheet" type="text/css" href="<@s.url value='/common/yui/container/assets/container.css'/>" />
<!-- CSS for App -->
<link href="<@s.url value='/common/jquery-ui-1.8.9.custom.css'/>" rel="stylesheet" type="text/css"/>
<link href="<@s.url value='/common/sdc.css'/>" rel="stylesheet" type="text/css"/>

<!-- Dependencies -->                
<script type="text/javascript" src="<@s.url value='/common/yui/yahoo-dom-event.js' includeParams='none'/>"></script> 
<script type="text/javascript" src="<@s.url value='/common/yui/container/container-min.js' includeParams='none'/>"></script> 
        
<!-- Source File --> 
<script type="text/javascript" src="<@s.url value='/common/yui/menu/menu-min.js' includeParams='none'/>"></script> 

<!-- Source File for App --> 
<script type="text/javascript" src="<@s.url value='/common/sdc.js' includeParams='none'/>"></script> 

<!-- Page-specific script -->
<script type="text/javascript">
    YAHOO.example.onMenuBarReady = function() {
    // Instantiate and render the menu bar

    var oMenuBar = new YAHOO.widget.MenuBar("sdcMenu", { autosubmenudisplay:true, hidedelay:750, lazyload:true });
    oMenuBar.render();
    };
        // Initialize and render the menu bar when it is available in the DOM
    YAHOO.util.Event.onContentReady("sdcMenu", YAHOO.example.onMenuBarReady);
</script>
