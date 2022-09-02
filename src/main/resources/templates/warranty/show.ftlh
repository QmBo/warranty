<#import "../macros/home.ftlh" as h>
<#include "../macros/security.ftlh">

<@h.home "Проверка по серийному номеру">

  <form action="/warranty" method="get">
    <div class="mb-3">
      <label for="sn" class="form-label">Серийный номер:</label>
      <input type="text" class="form-control" id="sn" name="sn" autofocus="autofocus" minlength="18" maxlength="18">
      <#if snError??><div class="text-warning">${snError}</div></#if>
    </div>

    <div>
      <input type="hidden" name="_csrf" value="${_csrf.token}">
    </div>

    <button class="btn btn-success" type="submit">Отправить</button>
  </form>
  <#if warranty??>
    <label>${warranty.product.name!!}</label>
  </#if>

</@h.home>