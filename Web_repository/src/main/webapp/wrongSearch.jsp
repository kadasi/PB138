<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Repository</title>
    </head>

    <body>
        <h1>Repository</h1>
        <div id="error">YOU DID NOT SELECT SEARCH OPTION OR AN ERROR OCCURED</div>
 
    <article>
        <header>
            <div id="topNav">
                <li><a href ="showWSDL.jsp">WSDL archive</a></li>            
            </div>
            <div id="topNav">
                <li><a href ="showWAR.jsp">WAR archive</a></li>           
            </div>
            <div id="topNav">
                <li><a href ="showXSD.jsp">XSD archive</a></li>
            </div>
        </header>
        <section>
            <p><strong>About<br/></strong></p>
            <p>This site allows you to upload new versions of your application and come back to the old versions.</p>
        </section>
    </article>
    </body>
</html>
