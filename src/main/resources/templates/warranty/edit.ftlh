<#--noinspection HtmlUnknownTarget,HtmlFormInputWithoutLabel-->
<#-- @ftlvariable name="warranty" type="ru.qmbo.warranty.domain.Warranty" -->
<#-- @ftlvariable name="timeZone" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="products" type="java.util.List<ru.qmbo.warranty.domain.Product>" -->
<#import "../macros/home.ftlh" as h>

<@h.home "Изменение товара">

  <#if error??>
    <div class="alert alert-warning" role="alert">
      <h4 class="alert-heading">Ошибка</h4>
      <hr>${error}
    </div>
  </#if>

  <#if warranty??>
    <form method="post" action="warranty/update" enctype="multipart/form-data">
      <div class="form-group row">
        <label class="col-sm-2" for="id">Id:</label>
        <div class="col-sm-10">
          <input class="form-control" id="id" type="number" name="id" value="${warranty.id?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2" for="product">Продукт:</label>
        <div class="col-sm-10">
          <input class="form-control" id="product" value="${warranty.product.name} (${warranty.product.modelName})" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2" for="serialNumber">Серийный номер:</label>
        <div class="col-sm-10">
          <input class="form-control" id="serialNumber" name="serialNumber" value="${warranty.serialNumber!}"
                 style="text-transform:uppercase" minlength="18" maxlength="18" required>
          <#--noinspection FtlReferencesInspection-->
          <#if serialNumberError??><div class="text-warning">${serialNumberError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <div>
          <input class="form-control" type="hidden"  name="product" value="${warranty.product.id?c}">
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Дата продажи:</label>
        <div class="col-sm-10">
          <input class="form-control" type="date" name="newDate" value="${warranty.date?date?iso(timeZone)}">
        </div>
      </div>

      <#--noinspection FtlReferencesInspection-->
      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-success" type="submit">Изменить</button>
    </form>

    <form method="get" action="warranty/all">
      <button class="btn btn-info" type="submit">Назад</button>
    </form>

    <form method="post" action="warranty/delete">
      <input type="hidden" name="id" value="${warranty.id?c}">
      <#--noinspection FtlReferencesInspection-->
      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-danger" type="submit">Удалить</button>
    </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Ошибка</h4>
      <hr>Такой гарантийной записи не существует!
    </div>
  </#if>

</@h.home>
