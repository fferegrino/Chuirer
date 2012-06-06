<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<header>
    <div class="headerBar">
        <div class="innerDiv">
            <h1>
                 <html:link styleClass="appNameLink" forward="go_prof"><bean:message key="app.name" /></html:link>
            </h1>
        </div>
    </div>
</header>
