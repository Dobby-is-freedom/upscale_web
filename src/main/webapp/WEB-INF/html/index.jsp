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
    <form action="/upload" method="post" enctype="multipart/form-data">
        <!--uploader begin-->
        <%--파일 선택 버튼--%>
        <div class="imagebox">
            <div class="optionbox-left">이미지 선택 :</div>
            <!--        <input type="file" id="fileElem" multiple accept="image/*" onchange="setThumbnail(event);">-->
            <label class="fileCk" for="fileElem">파일 선택</label>
            <input type="file" id="fileElem" class="file-button" multiple accept="image/*"/>
        </div>

        <%--파일 드래그 앤 드롭 부분--%>
        <div class="drag-drop">
            <input name="file" class="img-drag-in" id="img-drag-in" type='file' multiple accept="image/*"/>
            <div id="drag-text" class="drag-text">
                <label>이미지 끌어다 놓기</label>
            </div>
        </div>

        <%--이미지 보여지는 부분--%>
        <p class="no-file">(파일이 선택되지 않음)</p>
        <table id="file-table" style="display: none">
        </table>

        <script>
            function inputFile(input) {
                const fileView = document.getElementById("file-table")

                if (input.files) {
                    $(".no-file").hide();
                    $("#file-table").show();

                    const fileArr = Array.from(input.files)

                    fileArr.forEach((file, index) => {
                        const id = Math.random();

                        const tableTr = document.createElement("tr")
                        tableTr.id = "tr_" + id;

                        const fileReader = new FileReader()

                        const imgView = document.createElement("img")
                        imgView.classList.add("imageView")

                        const fileName = document.createElement("td")
                        fileName.textContent = file.name
                        fileName.classList.add("imgName")

                        const fileDelete = document.createElement("button")
                        fileDelete.textContent = "선택 취소";
                        fileDelete.classList.add("fileDelete")
                        fileDelete.addEventListener('click', function (event) {
                            console.log(tableTr.id + '를 삭제합니다.');
                            document.getElementById(tableTr.id).remove();
                        });
                        tableTr.appendChild(imgView)
                        tableTr.appendChild(fileName)
                        tableTr.appendChild(fileDelete)

                        fileReader.onload = e => {
                            imgView.src = e.target.result
                        }

                        fileReader.readAsDataURL(file)

                        fileView.appendChild(tableTr)
                    })
                }
            }

            const inImg = document.getElementById("fileElem")
            inImg.addEventListener("change", e => {
                inputFile(e.target)
            })

            const dragImg = document.getElementById("img-drag-in")
            dragImg.addEventListener("change", e => {
                inputFile(e.target)
            })
        </script>

        <!--upload-->
        <div>
            <input type="submit" value="업로드"/>
        </div>

    </form>

    <form action="/download" method="post" enctype="multipart/form-data">
        <!--option-->
        <div class="optionbox">
            <div class="optionbox-right" style="display : inline-block;">

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
            </div>
        </div>
        <!--upload-->
        <div>
            <input type="submit" value="다운로드"/>
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

</body>
</html>

