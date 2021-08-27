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

    <!--Thumbnail slider css-->
    <link rel="stylesheet" href="../../resources/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="../../resources/css/sliderstyle.css" type="text/css">

</head>
<body class="is-preload">

<!-- Wrapper -->
<div id="wrapper">

    <!-- Header -->
    <header id="header">
        <div class="logo">
            <span class="icon fa-gem"></span>
        </div>
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
    <div class="imagebox">
        <div class="optionbox-left" style="display : inline-block; margin-right: 40px">이미지 선택 :</div>
        <!--        <input type="file" id="fileElem" multiple accept="image/*" onchange="setThumbnail(event);">-->
        <input type="file" id="fileElem" class="file-button" multiple accept="image/*" onchange="setThumbnail(event);"/>
    </div>

    <script>
        function setThumbnail(event) {
            var owl = $('.owl-carousel');

            var count = document.querySelectorAll(".item").length;
            for (var i = 0; i < count; i++) {
                owl.trigger('remove.owl.carousel', 0);
            }
            owl.trigger('refresh.owl.carousel');

            for (var image of event.target.files) {
                var reader = new FileReader();

                reader.onload = function (event) {
                    var newimg = document.createElement("img");
                    newimg.setAttribute("src", event.target.result);
                    // newimg.setAttribute("alt", "");
                    var newitem = document.createElement("div");
                    newitem.setAttribute("class", "item");
                    newitem.appendChild(newimg);

                    owl.trigger('add.owl.carousel', newitem).trigger('update.owl.carousel').trigger('refresh.owl.carousel');
                };

                reader.readAsDataURL(image);
            }
        }
    </script>
    <!--uploader end-->

    <!--thumbnail slider-->
    <section>
        <div class="thumbnail-pic">
            <div id="img_container" class="thumbs owl-carousel"></div>
        </div>
    </section>
    <!--thumbnail slider-->

    <!--option-->
    <div class="optionbox">
        <div class="optionbox-left" style="display : inline-block; margin-right: 30px">옵션 :</div>
        <div class="optionbox-right" style="display : inline-block;">

            <input type="radio" id="basic" name="selection" value="basic" checked>
            <label class="kr-font" for="basic">basic</label>

            <input type="radio" id="image" name="selection" value="image">
            <label class="kr-font" for="image">image</label>

            <input type="radio" id="photo" name="selection" value="photo">
            <label class="kr-font" for="photo">photo</label>
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
<!--Uploader Scripts-->
<script src="../../resources/js/jquery-3.3.1.min.js"></script>
<script src="../../resources/js/jquery-ui.min.js"></script>
<script src="../../resources/js/owl.carousel.min.js"></script>
<script src="../../resources/js/uploaderNslider_main.js"></script>

<script>
    $(document).ready(function () {

        $("input:radio[name='selection']:radio[value='basic']").prop('checked', true); // 선택하기
        $("input:radio[name='selection']:radio[value='basic']").prop('checked', false); // 해제하기
    })
</script>

</body>
</html>
