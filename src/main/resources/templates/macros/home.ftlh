
<#--noinspection ALL-->
<#include "security.ftlh">

<#macro home title>

  <!DOCTYPE html>
  <html lang="en">

  <head>
    <base href="${springMacroRequestContext.contextPath}/">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="static/css/bootstrap.css">
    <link rel="stylesheet" href="static/fontawesome/css/fontawesome.css">

    <script src="static/js/jquery-1.10.2.js"></script>
    <script src="static/js/bootstrap.js"></script>
    <script src="https://kit.fontawesome.com/f635e67123.js" crossorigin="anonymous"></script>


    <title>Администрирование | ${title}</title>
  </head>

  <body class="bg-light">
  <#include "navbar.ftlh">
  <div class="container mt-5 mb-5">
    <div class="card">
      <div class="card-header">${title}</div>
      <div class="card-body">
        <#nested>
      </div>
    </div>
  </div>
  </body>

  </html>

</#macro>
