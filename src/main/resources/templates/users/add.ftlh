<#import "../macros/home.ftlh" as h>

<@h.home "Создание Пользователя">

  <form method="post" action="users/create">
    <div class="form-group row">
      <label for="name" class="col-sm-2">Имя:</label>
      <div class="col-sm-10">
        <input id="name" class="form-control" name="name" value="<#if user??>${user.name}</#if>">
        <#--noinspection FtlReferencesInspection-->
        <#if nameError??><div class="text-warning">${nameError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label for="username" class="col-sm-2">Логин:</label>
      <div class="col-sm-10">
        <input id="username" class="form-control" name="username" value="<#if user??>${user.username}</#if>">
        <#if usernameError??><div class="text-warning">${usernameError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label for="password" class="col-sm-2">Пароль:</label>
      <div class="col-sm-10">
        <input id="password" class="form-control" name="password" value="<#if user??>${user.password}</#if>">
        <#--noinspection FtlReferencesInspection-->
        <#if passwordError??><div class="text-warning">${passwordError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label for="role" class="col-sm-2">Роль:</label>
      <div class="col-sm-10">
        <select id="role" class="form-control" name="role">
          <#--noinspection FtlReferencesInspection-->
          <#list roles as role>
            <option value="${role.name()}" <#if user?? && user.role.name() == role>selected</#if>>${role.value}</option>
          </#list>
        </select>
      </div>
    </div>

    <#--noinspection FtlReferencesInspection-->
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
      <button class="btn btn-success" type="submit">Создать</button>
      <form method="get" action="users">
        <button class="btn btn-info" type="submit">Назад</button>
      </form>
    </div>
  </form>

</@h.home>
