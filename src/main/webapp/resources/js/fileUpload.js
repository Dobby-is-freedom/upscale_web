// 이미지 선택
var firstIndex = 0; // 파일 선택 버튼으로 이미지 들어올 때
var secondIndex = 0; // 드래그 드롭으로 이미지 들어올 때

function inputFile(input, num) {
    const fileView = document.getElementById("fileTable")

    if (input.files) { // 파일이 들어오면

        console.log(input.files)

        $(".no-file").hide();
        $("#fileTable").show();

        // 여러 개 파일을 배열로
        const fileArr = Array.from(input.files)
        var innerIndex = 0;

        fileArr.forEach((file, index) => {
            const id = Math.random();

            if (num == 1) {
                innerIndex = firstIndex++;
            } else {
                innerIndex = secondIndex++;
            }

            // <tr> 태그 생성
            const tableTr = document.createElement("tr")
            tableTr.id = "tr" + id;

            const imgTd = document.createElement("td")
            const buttonTd = document.createElement("td")

            const imgView = document.createElement("img")
            imgView.classList.add("image-view")

            const fileReader = new FileReader()
            fileReader.onload = e => {
                imgView.src = e.target.result
            }

            const fileName = document.createElement("td")
            fileName.textContent = file.name
            fileName.classList.add("file-name")

            const fileCancel = document.createElement("button")
            fileCancel.id = "fileCancel_" + num + '_' + innerIndex;
            fileCancel.textContent = "선택 취소";
            fileCancel.classList.add("file-cancel")
            fileCancel.addEventListener('click', function (event) { // click 이벤트

                if (event.target.id.split('_')[1] == '1') { // 파일 선택 버튼으로 들어온 이미지 중

                    // name=file 인 0번째 객체 (파일 선택 input)
                    var fileList = document.getElementsByName('file')[0].files

                    let fileArray = Array.from(fileList);

                    const dataTransfer = new DataTransfer();

                    // 해당 파일 지우기
                    fileArray.splice(parseInt(event.target.id.split('_')[2]), 1)

                    fileArray.forEach(file => {
                        dataTransfer.items.add(file);
                    })

                    // 선택한 파일을 지운 상태를 넣기
                    document.getElementsByName('file')[0].files = dataTransfer.files;

                } else { // 드래그 앤 드롭으로 들어온 이미지 중
                    // name=file 인 1번째 객체 (드래그 드롭 input)
                    var fileList = document.getElementsByName('file')[1].files

                    let fileArray = Array.from(fileList);

                    const dataTransfer = new DataTransfer();

                    fileArray.splice(parseInt(event.target.id.split('_')[2]), 1)

                    fileArray.forEach(file => {
                        dataTransfer.items.add(file);
                    })

                    document.getElementsByName('file')[1].files = dataTransfer.files;
                }

                document.getElementById(tableTr.id).remove();// 해당 id를 가진 태그 지우기
            });

            // img, button 각각 td로 감싸기
            imgTd.appendChild(imgView)
            buttonTd.appendChild(fileCancel)

            // tableTr 로 감싸기
            tableTr.appendChild(imgTd)
            tableTr.appendChild(fileName)
            tableTr.appendChild(buttonTd)

            fileReader.readAsDataURL(file)

            fileView.appendChild(tableTr)
        })
    }
}

const inImg = document.getElementById("fileButton")
inImg.addEventListener("change", e => {
    inputFile(e.target, '1')
})

const dragImg = document.getElementById("fileDragIn")
dragImg.addEventListener("change", e => {
    inputFile(e.target, '2')
})


//이미지 업스케일링
function uploadFile(e) {
    console.log("시작");
    $('#loadingImg').hide();
    e.preventDefault();
    var formData = new FormData($('#uploadForm')[0]);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if (this.readyState == 4 && this.status == 200){

            var filename = "";
            var disposition = xhr.getResponseHeader('Content-Disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
            }

            //this.response is what you're looking for
            console.log(this.response, typeof this.response);
            var a = document.createElement("a");
            var url = URL.createObjectURL(this.response)
            a.href = url;
            a.download = filename;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            console.log("끝");

            window.location.reload();
        }
    }
    xhr.open('POST', '/upload');
    xhr.responseType = 'blob'; // !!필수!!
    xhr.send(formData);
}
/*
            $.ajax({
                type: "POST",
                enctype: "multipart/form-data",
                url: "/upload",
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    console.log("성공");

                    let blob = new Blob([data], { type: 'application/octer-stream' });

                    var link = document.createElement('a');
                    var url = window.URL.createObjectURL(blob);
                    link.href = url;
                    link.target = '_self';
                    document.body.append(link);
                    link.click();
                    link.remove();
                    window.URL.revokeObjectURL(url);

                    // upFile.disabled = true; // 업로드 비활성
                    //
                    // $('#downloadBtn').prop('disabled', false); // 다운로드 활성
                },
                error: function (e) {
                    console.log("실패");
                }
            });

        }
*/

const upFile = document.getElementById("uploadBtn")
upFile.addEventListener("click", e => {
    uploadFile(e)
})