<#--noinspection HtmlUnknownTarget,HtmlFormInputWithoutLabel-->
<#-- @ftlvariable name="product" type="ru.qmbo.warranty.domain.Product" -->
<#import "../macros/home.ftlh" as h>

<@h.home "Изменение товара">

  <#if product??>
    <form method="post" action="products/update" enctype="multipart/form-data">
      <div class="form-group row">
        <label class="col-sm-2">Id:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="id" value="${product.id?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Имя:</label>
        <div class="col-sm-10">
          <input class="form-control" name="name" value="${product.name!}">
          <#--noinspection FtlReferencesInspection-->
          <#if nameError??><div class="text-warning">${nameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Название модели:</label>
        <div class="col-sm-10">
          <input class="form-control" name="modelName" value="${product.modelName!}" maxlength="10" style="text-transform:uppercase">
          <#--noinspection FtlReferencesInspection-->
          <#if modelNameError??><div class="text-warning">${modelNameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Аббревиатура:</label>
        <div class="col-sm-10">
          <input class="form-control" name="abbreviature" value="${product.abbreviature!}" maxlength="4" style="text-transform:uppercase">
          <#--noinspection FtlReferencesInspection-->
          <#if abbreviatureError??><div class="text-warning">${abbreviatureError}</div></#if>
        </div>
      </div>

      <#--noinspection FtlReferencesInspection-->
      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-success" type="submit">Изменить</button>
    </form>

    <form method="get" action="products">
      <button class="btn btn-info" type="submit">Назад</button>
    </form>

    <form method="post" action="products/delete">
      <input type="hidden" name="id" value="${product.id?c}">
      <#--noinspection FtlReferencesInspection-->
      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-danger" type="submit">Удалить</button>
    </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Ошибка</h4>
      <hr>Такого продукта не существует!
    </div>
  </#if>

</@h.home>
