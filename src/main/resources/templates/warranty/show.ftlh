<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="warranty" type="ru.qmbo.warranty.domain.Warranty" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "../macros/home.ftlh" as h>
<#include "../macros/security.ftlh">

<@h.home "Проверка по серийному номеру">

  <form action="/warranty" method="get">
    <div class="mb-3">
      <label for="sn" class="form-label">Серийный номер:</label>
      <input type="text" class="form-control" id="sn" name="sn" autofocus="autofocus" minlength="18" maxlength="18" required>
      <#if error??><div class="text-danger">${error!}</div></#if>
    </div>

    <div>
      <input type="hidden" name="_csrf" value="${_csrf.token}">
    </div>

    <button class="btn btn-success" type="submit">Отправить</button>
  </form>
  <#if warranty??>
    <div class="card">
      <div class="card-body">
        <#if warranty.product??>
          <h5 class="card-title">${warranty.product.name!}</h5>
        </#if>
        <p class="card-text">Серийный номер: ${warranty.serialNumber!}</p>
        <#if warranty.date??>
          <p class="card-text">Дата продажи: ${warranty.date?date}</p>
        </#if>
        <#if warranty.buildDate??>
          <p class="card-text">Дата производства по по серийному номеру: ${warranty.buildDate?date}</p>
        </#if>
        <#if !warranty.date?? && !warranty.buildDate??>
          <p class="card-text">Информация о гарантии не найдена.</p>
        </#if>
      </div>
    </div>
  </#if>

</@h.home>