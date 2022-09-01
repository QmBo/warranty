<#import "../macros/home.ftlh" as h>
<#include "../macros/security.ftlh">

<@h.home "Записать гарантию">

  <form action="/warranty/add" method="post">

    <div class="mb-3">
      <label for="sn" class="form-label">Серийный номер:</label>
      <input type="text" class="form-control" id="sn" name="sn" autofocus="autofocus" minlength="18" maxlength="18">
      <#if snError??><div class="text-warning">${snError}</div></#if>
    </div>

    <div class="mb-3 form-check">
      <input type="checkbox" class="form-check-input" id="today" checked="checked">
      <label class="form-check-label" for="today">Записать сегодняшней датой</label>
      <label class="form-label" for="date">Дата начала гарантии</label>
      <input type="date" class="form-control" id="date" name="date" disabled="disabled">
    </div>

    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
      <input type="hidden" name="_csrf" value="${_csrf.token}">
    </div>

    <button class="btn btn-success" type="submit">Записать</button>

  </form>
  <#if product??>
    <label>${product.name!!}</label>
  </#if>

  <script>
    function change() {
      const dataForm = document.getElementById("date");
      if (document.getElementById("today").checked) {
        dataForm.setAttribute("disabled", "disabled");
      } else {
        dataForm.removeAttribute("disabled");
        dataForm.setAttribute("value", "")
      }
    }
    document.getElementById("today").addEventListener("change", change);
  </script>

</@h.home>