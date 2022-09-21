<#import "../macros/home.ftlh" as h>
<#import "../macros/table.ftlh" as t>

<#-- @ftlvariable name="warranties" type="java.util.List<ru.qmbo.warranty.domain.Warranty>" -->
<#-- @ftlvariable name="warrantys" type="ru.qmbo.warranty.domain.Warranty" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->

<@h.home "Список Гарантий">

  <div class="mx-auto" style="width: 200px;padding-bottom: 10px;">
    <#--noinspection HtmlUnknownTarget-->
    <a class="btn btn-sm btn-info" href="warranty/add">Добавить гарантию</a>
  </div>

  <@t.table "warranties" />
  <#--noinspection HtmlDeprecatedAttribute-->
  <table id="warranties" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Id</th>
        <th>Серийный номер</th>
        <th>Аппарат</th>
        <th>Дата продажи</th>
        <th>Действия</th>
      </tr>
    </thead>
    <tbody>
      <#list warranties as warranty>
        <tr>
          <td>${warranty.id?c}</td>
          <td>${warranty.serialNumber!}</td>
          <td>${warranty.product.name!}</td>
          <td>${warranty.date?date}</td>
          <td>
            <a class="btn btn-xs btn-info" href="warranty/edit/${warranty.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
