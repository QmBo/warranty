<#import "macros/home.ftlh" as h>
<#include "macros/security.ftlh">

<@h.home "Ошибка">

  <div class="alert alert-danger" role="alert">
    <#--noinspection FtlReferencesInspection-->
    <h4 class="alert-heading">${error} — ${status}</h4>
    <#--noinspection FtlReferencesInspection-->
    <hr><div><#if message??>${message}</#if></div>
  </div>

</@h.home>
