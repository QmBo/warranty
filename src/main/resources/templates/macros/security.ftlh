<#assign isSecutiryContextExists = Session.SPRING_SECURITY_CONTEXT??>

<#if isSecutiryContextExists>
  <#--noinspection FtlReferencesInspection-->
  <#assign
    isLoggedIn = true
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    username = user.username
    isAdmin = user.authorities[0] == "ADMIN"
    isModer = user.authorities[0] == "MODER" || isAdmin
  >
<#else>
  <#assign
    isLoggedIn = false
    username = "unknown"
    isAdmin = false
    isModer = false
  >
</#if>
