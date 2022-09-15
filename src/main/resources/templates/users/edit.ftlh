<#--noinspection HtmlUnknownTarget-->
<#import "../macros/home.ftlh" as h>
<#-- @ftlvariable name="user" type="ru.qmbo.warranty.domain.User" -->
<#-- @ftlvariable name="Error" type="java.lang.String" -->

<@h.home "Редактировать пользователя">

  <#if Error??>
    <div class="alert alert-warning" role="alert">
      <h4 class="alert-heading">Ошибка</h4>
      <hr>${Error}
    </div>
  </#if>

  <#if user??>
    <form method="post" action="users/update">
      <div class="form-group row">
        <label for="id" class="col-sm-2">Id:</label>
        <div class="col-sm-10">
          <input id="id" class="form-control" type="number" name="id" value="${user.id?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label for="name" class="col-sm-2">Имя:</label>
        <div class="col-sm-10">
          <input id="name" class="form-control" name="name" value="${user.name}">
          <#--noinspection FtlReferencesInspection-->
          <#if nameError??><div class="text-warning">${nameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label for="username" class="col-sm-2">Логин:</label>
        <div class="col-sm-10">
          <input id="username" class="form-control" name="username" value="${user.username}">
          <#--noinspection FtlReferencesInspection-->
          <#if usernameError??><div class="text-warning">${usernameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label for="password" class="col-sm-2">Пароль:</label>
        <div class="col-sm-10">
          <input id="password" class="form-control" name="password">
          <#--noinspection FtlReferencesInspection-->
          <#if passwordError??><div class="text-warning">${passwordError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label for="role" class="col-sm-2">Роль:</label>
        <div class="col-sm-10">
          <select id="role" class="form-control" name="role">
            <#list user.role.values() as role>
              <option value="${role.name()}" <#if user.role.name() == role>selected</#if>>${role.value}</option>
            </#list>
          </select>
        </div>
      </div>

      <#--noinspection FtlReferencesInspection-->
      <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-success" type="submit">Изменить</button>
    </form>

      <form method="get" action="users">
        <button class="btn btn-info" type="submit">Назад</button>
      </form>

      <form method="post" action="users/delete">
        <input type="hidden" name="id" value="${user.id?c}">
        <#--noinspection FtlReferencesInspection-->
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-danger" type="submit">Удалить</button>
      </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Ошибка</h4>
      <hr>Такого пользователя не существует!
    </div>
  </#if>

</@h.home>
