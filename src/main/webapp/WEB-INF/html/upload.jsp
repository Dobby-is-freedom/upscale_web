<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021-08-29
  Time: 오후 6:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="EUC-KR">
    <title>파일 업로드</title>
</head>
<body>
<fieldset>
    <legend>파일 업로드</legend>
    <form action="UploadService" method="post" enctype="multipart/form-data">
        글쓴이 :
        <input type="text" name="author"/>
        제목 :
        <input type="text" name="title"/>
        <input type="file" value="파일 선택" name="file" multiple/>
        <input type="submit" value="업로드"/>
    </form>
    <!--
    <table>
        <form action="UploadService" method="post" enctype="multipart/form-data">
            <tr>
                <td>글쓴이 : </td>
                <td><input type="text" name="author"/></td>
            </tr>
            <tr>
                <td>제목 : </td>
                <td><input type="text" name="title"/></td>
            </tr>
            <tr>
                <td>글쓴이 : </td>
                <td><input type="file" value="파일 선택" name="file" multiple/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="업로드"/></td>
            </tr>
        </form>
    </table>
    -->
</fieldset>
</body>
</html>
