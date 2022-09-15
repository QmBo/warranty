<#import "../macros/home.ftlh" as h>
<#import "../macros/table.ftlh" as t>

<#-- @ftlvariable name="products" type="java.util.List<ru.qmbo.warranty.domain.Product>" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->

<@h.home "Список Продуктов">

  <div class="mx-auto" style="width: 200px;padding-bottom: 10px;">
    <#--noinspection HtmlUnknownTarget-->
    <a class="btn btn-sm btn-info" href="products/add">Добавить продукт</a>
  </div>

  <@t.table "products" />
  <#--noinspection HtmlDeprecatedAttribute-->
  <table id="products" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Id</th>
        <th>Имя</th>
        <th>Название модели</th>
        <th>Аббревиатура</th>
        <th>Действия</th>
      </tr>
    </thead>
    <tbody>
      <#list products as product>
        <tr>
          <td>${product.id?c}</td>
          <td>${product.name}</td>
          <td>${product.modelName}</td>
          <td>${product.abbreviature}</td>
          <td>
            <a class="btn btn-xs btn-info" href="products/edit/${product.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
