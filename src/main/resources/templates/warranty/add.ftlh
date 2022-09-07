<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="warranty" type="ru.qmbo.warranty.domain.Warranty" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "../macros/home.ftlh" as h>
<#include "../macros/security.ftlh">

<@h.home "Записать гарантию">

  <#--noinspection HtmlUnknownTarget-->
  <form action="/warranty/add" method="post">

    <div class="mb-3">
      <label for="sn" class="form-label">Серийный номер:</label>
      <input type="text" class="form-control" id="sn" name="sn" autofocus="autofocus" minlength="18" maxlength="18" required>
      <#if error??><div class="text-danger">${error!}</div></#if>
    </div>

    <div class="mb-3 form-check">
      <input type="checkbox" class="form-check-input" id="today" checked="checked">
      <label class="form-check-label" for="today">Записать сегодняшней датой</label>
    </div>

    <div class="mb-3">
      <label class="form-label" for="date">Дата начала гарантии</label>
      <input type="date" class="form-control" id="date" name="date" disabled="disabled">
    </div>

    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
      <input type="hidden" name="_csrf" value="${_csrf.token}">
    </div>

    <div class="mb-3">
      <button class="btn btn-success" type="submit">Записать</button>
    </div>

  </form>
  <#if warranty??>
    <div class="card <#if warranty.date?? && warranty.date?date?iso_utc != .now?date?iso_utc> text-white bg-danger </#if>">
      <div class="card-body">
        <#if warranty.product??>
          <h5 class="card-title">${warranty.product.name!}</h5>
        </#if>
        <p class="card-text">Серийный номер: ${warranty.serialNumber!}</p>
        <#if warranty.date??>
          <p class="card-text">
            Дата продажи: ${warranty.date?date}
          </p>
        </#if>
        <#if warranty.buildDate??>
          <p class="card-text">Дата производства по по серийному номеру: ${warranty.buildDate?date}</p>
        </#if>
      </div>
    </div>
  </#if>

  <script>
    function change() {
      const dataForm = document.getElementById("date");
      if (document.getElementById("today").checked) {
        dataForm.setAttribute("disabled", "disabled");
      } else {
        dataForm.removeAttribute("disabled");
        dataForm.removeAttribute("value");
      }
    }
    document.getElementById("today").addEventListener("change", change);
  </script>

</@h.home>