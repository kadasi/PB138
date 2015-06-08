<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="d" uri="http://stripes.sourceforge.net/stripes.tld" %>
<!DOCTYPE html>
<jsp:useBean id="actionBean" scope="page"
             class="org.cz.muni.fi.pb138.Beans.WSDLActionBean"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>WAR archives</title>
    </head>
    <body>
        <h1><a href ="index.jsp">WSDL archive</a></h1>
        </br>
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
                <section>
                    <section>
                        <fieldset>
                            <table>
                                <d:form beanclass="org.cz.muni.fi.pb138.Beans.WSDLActionBean" method="GET">
                                    <tr>
                                        <th>Search:  </th>
                                        <th><d:text id="data" name="dataInput"/></th> 
                                        <th><d:submit name="searchData">Search</d:submit></th>
                                        <th><d:radio name="radioButton" id="radioOperation" value="operation/@name"/>Operations</th>
                                        <th><d:radio name="radioButton" id="radioRequest" value="input/@message"/>Requests</th>
                                        <th><d:radio name="radioButton" id="radioResponse" value="output/@message"/>Responses</th>
                                    </tr>
                                </d:form>     
                            </table>
                        </fieldset>     

                        <div id="topNavRight">
                            <d:form action="wsdlUpload" method="POST" beanclass="org.cz.muni.fi.pb138.Beans.WSDLActionBean" >
                                <d:file name="wsdlInput"/>
                                </br>
                                <d:submit name="wsdlUpload">Submit</d:submit>
                            </d:form>
                        </div>
                    </section>
                </section>
                <section>
                    <div id="tabulka">
                        <table class="zakladni" border="1">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Version</th>
                                    <th>Insight</th>
                                </tr>
                            </thead>
                            <s:useActionBean beanclass="org.cz.muni.fi.pb138.Beans.WSDLActionBean" var="actionBean"/>
                            <c:forEach items="${actionBean.getWSDLs()}" var="WSDL">
                                <tr>
                                    <td><c:out value="${WSDL.getFileName()}"/></td>
                                    <td><c:out value="${WSDL.getTimestamp()} "/></td>
                                    <td>
                                        <d:form action="searchId" beanclass="org.cz.muni.fi.pb138.Beans.WSDLActionBean" method="POST">
                                            <button type="submit" name="searchId" value="${WSDL.getId()}">View</button>
                                        </d:form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </section>    

            </section>
        </article>

    </body>
</html>