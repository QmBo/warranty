<#import "macros/home.ftlh" as h>
<#include "macros/security.ftlh">

<@h.home "Главная">

    <#if answer??>
        <label>${answer.name!!}</label>
    <#else>
        <label>info</label>
    </#if>

</@h.home>