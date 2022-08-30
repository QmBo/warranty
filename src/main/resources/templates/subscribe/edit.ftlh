<#--noinspection HtmlUnknownTarget,HtmlFormInputWithoutLabel-->
<#import "../macros/home.ftlh" as h>
<#include "../macros/security.ftlh">

<@h.home "Изменение подписки">

  <#if subscribe??>

    <form method="post" action="subscribe/update" enctype="multipart/form-data">
      <div class="form-group row">
        <label class="col-sm-2">Id:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="id" value="${subscribe.id?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Изображение:</label>
        <div class="col-sm-10">
          <input type="hidden" name="photoUrl" value="${subscribe.photoUrl!}">
          <input class="form-control" id="photo" type="file" name="photo">
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Категория:</label>
        <div class="col-sm-10">
          <select class="form-control selectpicker" name="category" data-live-search="true" data-size="4">
            <#if !product.category??><option selected>—</option></#if>
            <#list categories as category>
              <option value="${category.id?c}" <#if product.category?? && category == product.category>selected</#if>>
                ${category.name} (${category.storage.name})
              </option>
            </#list>
          </select>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Имя:</label>
        <div class="col-sm-10">
          <input class="form-control" name="name" value="${subscribe.name!}">
          <#--noinspection FtlReferencesInspection-->
          <#if nameError??><div class="text-warning">${nameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Описание:</label>
        <div class="col-sm-10">
          <textarea class="form-control" name="description" rows="4">${subscribe.description!}</textarea>
          <#--noinspection FtlReferencesInspection-->
          <#if descriptionError??><div class="text-warning">${descriptionError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Цена:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="price" step="any"
                 value="<#if product.price??>${product.price?string["0.00;; decimalSeparator='.'"]}</#if>">
          <#--noinspection FtlReferencesInspection-->
          <#if priceError??><div class="text-warning">${priceError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Доступно к заказу:</label>
        <div class="col-sm-10">
          <input class="form-check-input" type="checkbox" name="available"
                 <#if product.available == true>checked</#if>>
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
      <hr>Такой подписки не существует!
    </div>
  </#if>

</@h.home>
