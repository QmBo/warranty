<#--noinspection HtmlUnknownTarget-->
<#-- @ftlvariable name="product" type="ru.qmbo.warranty.domain.Product" -->
<#import "../macros/home.ftlh" as h>

<@h.home "Добавление продукта">

  <#if error?? >
    <div class="alert alert-danger" role="alert">
      <#--noinspection FtlReferencesInspection-->
      ${error}
    </div>
  </#if>

  <form method="post" action="products/create" enctype="multipart/form-data">

    <div class="form-group row">
      <label class="col-sm-2" for="name">Имя:</label>
      <div class="col-sm-10">
        <input class="form-control" id="name" name="name" value="<#if product??>${product.name!}</#if>">
        <#--noinspection FtlReferencesInspection-->
        <#if nameError??><div class="text-warning">${nameError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2" for="modelName">Название модели:</label>
      <div class="col-sm-10">
        <input class="form-control" id="modelName" name="modelName" value="<#if product??>${product.modelName!}</#if>" maxlength="10" style="text-transform:uppercase">
        <#--noinspection FtlReferencesInspection-->
        <#if modelNameError??><div class="text-warning">${modelNameError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2" for="abbreviature">Аббревиатура:</label>
      <div class="col-sm-10">
        <input class="form-control" id="abbreviature" name="abbreviature" value="<#if product??>${product.abbreviature!}</#if>" maxlength="4" style="text-transform:uppercase">
        <#--noinspection FtlReferencesInspection-->
        <#if abbreviatureError??><div class="text-warning">${abbreviatureError}</div></#if>
      </div>
    </div>

    <#--noinspection FtlReferencesInspection-->
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button class="btn btn-success" type="submit">Создать</button>
  </form>

  <form method="get" action="products">
    <button class="btn btn-info" type="submit">Назад</button>
  </form>

</@h.home>
