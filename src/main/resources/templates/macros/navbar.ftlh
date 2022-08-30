<#include "security.ftlh">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Администрирование</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbar" aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div id="navbar" class="navbar-collapse collapse">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item"><a class="nav-link" href="subscribe"><i class="fa fa-comment mr-1"></i>Подписки</a></li>
        <#if isAdmin>
          <li class="nav-item"><a class="nav-link" href="users"><i class="fa fa-user-secret mr-1"></i>Пользователи</a></li>
        </#if>
      </ul>

      <form action="logout" method="post" class="pull-right">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="submit" class="btn btn-warning me-2" value="Выйти"/>
      </form>

      <span class="navbar-text">
        <i>${username}</i>
      </span>
    </div>

  </div>
</nav>
