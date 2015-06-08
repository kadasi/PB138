<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">  
        <title>Show single WSDL</title>
    </head>
    <body>
        <h1><a href ="index.jsp">WSDL archive</a></h1>
        </br>
        <script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js"></script>
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
        <div id="viewer">
           File: ${actionBean.getDocument().getFileName()}
           <br/>
           Date: ${actionBean.getDocument().getTimestamp()}
           </br></br>
           WSDL </br>
           <pre class="prettyprint">${actionBean.getDocument().getDocument()}
           </pre> 
           Requests and responses
           <pre class="prettyprint">${actionBean.getDocument().getExtract()}
           </pre> 
        </div>
    </body>
</html>
