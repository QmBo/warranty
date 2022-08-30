<#import "../macros/home.ftlh" as h>
<#include "../macros/security.ftlh">


<@h.home "Создание подписки">

  <#if error?? >
    <div class="alert alert-danger" role="alert">
      ${error}
    </div>
  </#if>
  <form method="post" action="/subscribe/create" enctype="multipart/form-data">

    <div class="form-group row">
      <label for="name" class="col-sm-2">Название:</label>
      <div class="col-sm-10">
        <input id="name" class="form-control" name="name" value="<#if product??>${product.name!}</#if>">
        <#if nameError??><div class="text-warning">${nameError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label for="chat" class="col-sm-2">Чат:</label>
      <div class="col-sm-8">
        <select id="chat" class="form-select" name="chat" data-live-search="true" data-size="4">
          <#list chats as chat>
            <option value="${chat.id?c}">
              ${chat.chatName!}(${chat.chatId}) <#if chat.subscribe == false>Ещё не подписан</#if>
            </option>
          </#list>
        </select>
      </div>
      <div class="col-sm-2">
        <button class="btn btn-info" onclick="null">+</button>
      </div>
    </div>

    <div class="form-group row">
      <label for="description" class="col-sm-2">Описание:</label>
      <div class="col-sm-10">
        <input id="description" class="form-control" name="description"
               value="<#if product??>${product.name!}</#if>" placeholder="Введите описание подписки. Не обязательно.">
        <#if nameError??><div class="text-warning">${nameError}</div></#if>
        <#if descriptionError??><div class="text-warning">${descriptionError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label for="key-words" class="col-sm-2">Ключевые слова:</label>
      <div class="col-sm-10">
        <textarea id="key-words" class="form-control" name="key-words" rows="4"
                placeholder="Перечислите слова через запятую. Например: Мама, мыла,раму..."
        ><#if product??>${product.description!}</#if></textarea>
        <#if descriptionError??><div class="text-warning">${descriptionError}</div></#if>
      </div>
    </div>

    <#if isAdmin>
      <div class="form-group row">
        <label for="user" class="col-sm-2">Пользователь:</label>
        <div class="col-sm-10">
          <select id="user" class="form-select" name="user" data-live-search="true" data-size="4">
            <#list users as us>
              <option value="${us.id?c}" <#if us.name == username>selected</#if>>
                ${us.name}(${us.userChatId})
              </option>
            </#list>
          </select>
        </div>
      </div>
    </#if>

    <#--noinspection FtlReferencesInspection-->
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
      <button class="btn btn-success" type="submit">Создать</button>

      <form method="get" action="subscribe">
        <button class="btn btn-info" type="submit">Назад</button>
      </form>

    </div>
  </form>

</@h.home>
