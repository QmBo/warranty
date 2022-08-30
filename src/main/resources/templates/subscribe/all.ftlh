<#include "../macros/security.ftlh">
<#import "../macros/home.ftlh" as h>
<#import "../macros/table.ftlh" as t>

<@h.home "Список подписок">

    <div class="row ">
        <div class="col-md-4">
            <a class="btn btn-sm btn-info" href="subscribe/add">Добавить подписку</a>
        </div>
    </div>

    <@t.table "subscribes" />
    <table id="subscribes" class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>Id</th>
            <th>Название</th>
            <th>Описание</th>
            <th>Чат</th>
            <th>Подписан</th>
            <#if isAdmin><th>Пользователь</th></#if>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody>
        <#list subscribes as subscribe>
            <tr>
                <td>${subscribe.id}</td>
                <td>${subscribe.name}</td>
                <td><#if subscribe.description??>${subscribe.description}</#if></td>
                <td>
                    <#list subscribe.chat as chat>
                        <p>
                            <#if chat.chatName??>${chat.chatName}</#if>(${chat.chatId})
                        </p>
                    </#list>
                </td>
                <td>
                    <#list subscribe.chat as chat>
                        <#if chat.subscribe == true>
                            <i class="fa fa-check" aria-hidden="true" style="color: green"></i>
                        <#else>
                            <i class="fa fa-times" aria-hidden="true" style="color: red"></i>
                        </#if>
                    </#list>
                </td>
                <#if isAdmin><td>${subscribe.user.name}</td></#if>
                <td>
                    <a class="btn btn-xs btn-info" href="subscribe/edit/${subscribe.id?c}">
                        <i class="fa fa-pencil" aria-hidden="true"></i>
                    </a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

</@h.home>
