<#import "../macros/home.ftlh" as h>
<#import "../macros/table.ftlh" as t>

<@h.home "Список пользователей">

  <div class="row ">
    <div class="col-md-4">
      <a class="btn btn-sm btn-info" href="users/add">Добавить пользователя</a>
    </div>
  </div>

  <@t.table "users" />
  <table id="users" class="table table-striped table-bordered">
    <thead>
    <tr>
      <th>Id</th>
      <th>Имя</th>
      <th>Логин</th>
      <th>Роль</th>
      <th>Действие</th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
      <tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.username}</td>
        <td>${user.role.value}</td>
        <td>
          <a class="btn btn-xs btn-info" href="users/edit/${user.id?c}">
            <i class="fa fa-pencil" aria-hidden="true"></i>
          </a>
        </td>
      </tr>
    </#list>
    </tbody>
  </table>

</@h.home>
