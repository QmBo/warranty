<#import "macros/home.ftlh" as h>

<@h.home "Авторизация">

  <#if RequestParameters.error?? >
    <div class="alert alert-warning" role="alert">
      Неправильный логин или пароль.
    </div>
  </#if>

  <#if RequestParameters.logout?? >
    <div class="alert alert-success" role="alert">
      Вы вышли из системы.
    </div>
  </#if>

  <form action="login" method="post">
    <div class="form-group row">
      <label for="username" class="col-sm-2">Логин:</label>
      <div class="col-sm-10">
        <input id="username" type="text" class="form-control" name="username">
      </div>
    </div>

    <div class="form-group row">
      <label for="password" class="col-sm-2">Пароль:</label>
      <div class="col-sm-10">
        <input id="password" type="password" class="form-control" name="password">
      </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" class="btn btn-primary">Войти</button>
  </form>

</@h.home>
