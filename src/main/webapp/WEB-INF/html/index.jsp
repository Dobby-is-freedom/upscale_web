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
    <%--    <title>Dimension by HTML5 UP</title>--%>
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
        <%--        <div class="logo">--%>
        <%--            <span class="icon fa-gem"></span>--%>
        <%--        </div>--%>
        <div class="content">
            <div class="inner">
                <!-- 안녕하세요 ㅇ소여씨 -->
                <!-- ^이거 누구에욬ㅋㅋㅋㅋㅋㅋ -->
                <h1>dobby is free</h1>
            </div>
        </div>
    </header>

    <!-- Main -->
    <!--uploader begin-->
    <%--파일 선택 버튼--%>
    <div class="imagebox">
        <div class="optionbox-left">이미지 선택 :</div>
        <!--        <input type="file" id="fileElem" multiple accept="image/*" onchange="setThumbnail(event);">-->
        <label class="fileCk" for="fileElem">파일 선택</label>
        <input type="file" id="fileElem" class="file-button" multiple accept="image/*" onchange="setThumbnail(event);"/>
    </div>

    <%--파일 드래그 앤 드롭 부분--%>
    <div class="drag-drop">
        <input class="img-drag-in" type='file' multiple accept="image/*"/>
        <div  id="drag-text" class="drag-text">
            <label>이미지 끌어다 놓기</label>
        </div>
    </div>

    <!--option-->
    <div class="optionbox">
        <div class="optionbox-left">옵션 :</div>
        <div class="optionbox-right" style="display : inline-block;">

            <input type="radio" id="basic" name="selection" value="basic" checked>
            <label class="kr-font" for="basic">기본</label>

            <input type="radio" id="image" name="selection" value="image">
            <label class="kr-font" for="image">그림</label>

            <input type="radio" id="photo" name="selection" value="photo">
            <label class="kr-font" for="photo">사진</label>
        </div>
    </div>

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

</body>
</html>

