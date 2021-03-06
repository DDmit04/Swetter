<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/home">Sweater</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/home">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/allMessages">All Messages</a>
            </li>
            <#if !gest && !isRegistrationPage>
            <li class="nav-item">
                <a class="nav-link" href="/profile/${currentUserId}">My profile</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/subMessages/${user.id}">My Subscriptions</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/accountSettings">Settings</a>
            </li>
            </#if>
            <#if isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/user">User list</a>
            </li>
            </#if>
        </ul>
        <div class="navbar-text mr-3">${name}</div>
        <#if name != "Gest">
        	<@l.logout />
        <#else>
       	 	<a class="btn btn-primary" href="/login">Sign in</a>
        </#if>
    </div>
</nav>
