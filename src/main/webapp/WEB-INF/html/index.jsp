<%--
  Created by IntelliJ IDEA.
  User: psy
  Date: 2021/08/26
  Time: 3:06 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--
Dimension by HTML5 UP
html5up.net | @ajlkn
Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->

<html>
<head>
    <title>Dobby Is Free</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"/>
    <link rel="stylesheet" href="../../resources/css/main.css"/>
    <noscript>
        <link rel="stylesheet" href="../../resources/css/noscript.css"/>
    </noscript>

</head>
<body class="is-preload">

<!-- Wrapper -->
<div id="wrapper">

    <!-- Header -->
    <header id="header">
        <div class="content">
            <div class="inner">
                <h1>dobby is free</h1>
            </div>
        </div>
    </header>

    <!-- Main -->
    <%--    <form id="uploadForm" method="post" enctype="multipart/form-data">--%>
    <form id="uploadForm" enctype="multipart/form-data">
        <!--uploader begin-->
        <%--파일 선택 버튼--%>
        <div class="button-box">
            <div class="box-left">이미지 선택 :</div>
            <label class="select-file" for="fileButton">파일 선택</label>
            <input type="file" id="fileButton" name="file" class="file-button" multiple accept="image/*"/>
        </div>

        <%--파일 드래그 앤 드롭 부분--%>
        <div class="drag-drop-box">
            <input type="file" id="fileDragIn" name="file" class="file-drag-in" multiple accept="image/*"/>
            <div id="dragText" class="drag-text">
                <label>이미지 끌어다 놓기</label>
            </div>
        </div>

        <%--이미지 보여지는 부분--%>
        <p class="no-file">(파일이 선택되지 않음)</p>
        <table id="fileTable" style="display: none">
        </table>

        <!--option-->
        <div class="option-box">
            <div class="box-left">옵션 :</div>
            <div class="box-right" style="display : inline-block;">

                <input type="radio" id="basic" name="selection" value="basic" checked>
                <label class="kr-font" for="basic">기본</label>

                <input type="radio" id="image" name="selection" value="image">
                <label class="kr-font" for="image">그림</label>

                <input type="radio" id="photo" name="selection" value="photo">
                <label class="kr-font" for="photo">사진</label>
            </div>
        </div>
        <!--upload-->
        <div>
            <button id="uploadBtn">업로드</button>
        </div>
    </form>

    <!-- Footer -->
    <footer id="footer">
        <p class="copyright">&copy; Untitled. Design: <a href="https://html5up.net">HTML5 UP</a>.</p>
    </footer>

</div>

<!-- BG -->
<div id="bg"></div>

<!--MainPage scripts-->
<script src="../../resources/js/jquery.min.js"></script>
<script src="../../resources/js/browser.min.js"></script>
<script src="../../resources/js/breakpoints.min.js"></script>
<script src="../../resources/js/util.js"></script>
<script src="../../resources/js/main.js"></script>
<script src="../../resources/js/fileUpload.js"></script>

</body>
</html>

