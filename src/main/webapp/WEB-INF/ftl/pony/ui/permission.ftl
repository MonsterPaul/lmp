<#--
<input type="hidden"/>
-->
<#macro permission bizCode=''>
	<#if permissions?? && bizCode?? && bizCode!=''>
		<#assign hasAuth=false />
		<#list bizCode?split("|") as code>
			<#if permissions?seq_contains(code)>
				<#assign hasAuth=true />
                <#nested/>
                <#break />
			</#if>
		</#list>
	</#if>

</#macro>
