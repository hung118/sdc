<input type="hidden" name="${parameters.name}" id="${parameters.id}Input" value="${parameters.rfcValue?default("")}">
<#if parameters.mode == 'input' || parameters.mode == 'label'>
  <#if parameters.mode == 'input'>
     <input name="dojo.${parameters.name}" id="${parameters.id}InputVisible"
  <#else>
     <span id="${parameters.id}InputVisible"  
  </#if>   
  <#if parameters.cssClass?if_exists != "">
     class="${parameters.cssClass?html}"
  </#if>
  <#if parameters.cssStyle?if_exists != "">
     style="${parameters.cssStyle?html}"
  </#if>
  <#if parameters.mode == 'input'>
     >
  <#else>
     ></span>   
  </#if>  
  <img id="${parameters.id}Img" style="vertical-align: middle;" 
     <#if parameters.iconCssClass?if_exists != "">
         class="${parameters.iconCssClass?html}"
     </#if>
     <#if parameters.iconPath?if_exists != "">
         src="${parameters.iconPath}"
     <#else>
         src="${base}/struts/dateIcon.gif"
     </#if>
   > 
</#if>
<br />
<div id="${parameters.id}Container"
<#if parameters.mode == 'input' || parameters.mode == 'label'>
style="display:none"
<#else>
style="position:relative"
</#if>
></div>
<script type="text/javascript">
YAHOO.util.Event.addListener(window, "load", function() {
       YAHOO.namespace("datepickers").${parameters.id} =  new YAHOO.widget.Calendar("${parameters.id}","${parameters.id}Container");
       
       <#if parameters.nameValue?if_exists != "">
           YAHOO.datepickers.${parameters.id}.cfg.setProperty("selected","${parameters.nameValue}",false); 
       </#if>
       <#if parameters.pagedate?if_exists != "">
           YAHOO.datepickers.${parameters.id}.cfg.setProperty("pagedate","${parameters.pagedate}",false); 
       </#if>
       <#if parameters.startDate?if_exists != "">
           YAHOO.datepickers.${parameters.id}.cfg.setProperty("mindate","${parameters.startDate}",false); 
       </#if>
       <#if parameters.endDate?if_exists != "">
           YAHOO.datepickers.${parameters.id}.cfg.setProperty("maxdate","${parameters.endDate}",false); 
       </#if>
       <#if parameters.rfcValue?if_exists != "">
           YAHOO.util.Dom.get("${parameters.id}Input").value = "${parameters.rfcValue}";
       </#if>
       <#if parameters.nameValue?if_exists != "" && (parameters.mode == 'input' || parameters.mode == 'label')>
           var visibleInput = YAHOO.util.Dom.get("${parameters.id}InputVisible");
           <#if parameters.formatFunction?if_exists != "">
               YAHOO.s2yui.showDateValue(visibleInput, window["${parameters.formatFunction}"]("${parameters.nameValue}"));
           <#else>
               YAHOO.s2yui.showDateValue(visibleInput, "${parameters.nameValue}");    
           </#if>
       </#if>
       <#if parameters.mode == 'input' || parameters.mode == 'label'>
           YAHOO.datepickers.${parameters.id}.cfg.setProperty("close", true, false); 
           YAHOO.util.Event.addListener("${parameters.id}Img", "click", function() {
              var display = YAHOO.util.Dom.getStyle("${parameters.id}Container", "display");
              if(display == "none") {
                 YAHOO.datepickers.${parameters.id}.show();
              } else {
                 YAHOO.datepickers.${parameters.id}.hide();
              }
           }, YAHOO.datepickers.${parameters.id}, true);
       </#if>
       <#if parameters.mode == 'input' || parameters.mode == 'label'>
           YAHOO.datepickers.${parameters.id}.cfg.setProperty("close", true, false); 
           YAHOO.util.Event.addListener("${parameters.id}InputVisible", "blur", function() {
              YAHOO.util.Dom.get("${parameters.id}Input").value = YAHOO.util.Dom.get("${parameters.id}InputVisible").value;
           }, YAHOO.datepickers.${parameters.id}, true);
           YAHOO.util.Event.addListener("${parameters.id}InputVisible", "change", function() {
              YAHOO.util.Dom.get("${parameters.id}Input").value = YAHOO.util.Dom.get("${parameters.id}InputVisible").value;
           }, YAHOO.datepickers.${parameters.id}, true);
       </#if>
       
       <#if parameters.language != "en">
       YAHOO.s2yui.loadLanguageStrings(YAHOO.datepickers.${parameters.id}, '${parameters.language}');
       </#if>
       
       YAHOO.datepickers.${parameters.id}.render();
       
       YAHOO.datepickers.${parameters.id}.selectEvent.subscribe(function(type,args,obj) {
         var input = YAHOO.util.Dom.get("${parameters.id}Input");
         var visibleInput = YAHOO.util.Dom.get("${parameters.id}InputVisible");
         var dates = args[0]; 
         var date = dates[0];
         var year = date[0], month = date[1], day = date[2];
         
         if(!YAHOO.lang.isNumber(year)
            || !YAHOO.lang.isNumber(month)
            || !YAHOO.lang.isNumber(day)) {
            //yahoo couldn't parse what the user typed in
            input.value = visibleInput.value;
            return;
         }
         
         var dateObj = this._toDate(date);
         input.value = YAHOO.s2yui.toRFC3339(dateObj);
         
         <#if parameters.mode == 'input' || parameters.mode == 'label'>
            <#if parameters.formatFunction?if_exists != "">
                var fromattedDate = window["${parameters.formatFunction}"](dateObj);
            <#else>
                var fromattedDate = month + "/" + day + "/" + year;    
            </#if>
            YAHOO.s2yui.showDateValue(visibleInput, fromattedDate);
         </#if>   
         <#if parameters.autoClose?default(true)>
            this.hide();
         </#if>
       }, YAHOO.datepickers.${parameters.id}, true);
    }); 
</script>
