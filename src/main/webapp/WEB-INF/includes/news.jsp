

<@s.iterator value="newsList" status="newsStatus">
<@s.if test="#newsStatus.first">
    
    <table class="innerNews">
    <tr><th colspan="2"><h4>SDC Messages</h4></th>
    <tr>
        <th>News</th>
        <th>Date</th>
    </tr>
</@s.if>
<tr>
    <td>
        <@s.property value="description"/> 
        [<a href="javascript:showNews('news<@s.property value="newsPk"/>');">More</a>]
        <div class="newsMessage" id='news<@s.property value="newsPk"/>' style="display:none;">
            <@s.property value="news"/>
        </div>
    </td>
    <td>
    	<@s.date name="timestamp" format="MM/dd/yyyy"/>
    </td>
</tr>
<@s.if test="#newsStatus.last"></table></@s.if>
</@s.iterator>