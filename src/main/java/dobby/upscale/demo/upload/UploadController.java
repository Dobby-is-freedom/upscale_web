package dobby.upscale.demo.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class UploadController {

    @Autowired
    UploadShellService uploadShellService;

    @Autowired
    UpscaleService upscaleService;

    @Value("${file.temp.path}")
    String fileTempPath;

    @PostMapping(value = "/upload")
    @ResponseBody
    public String doUpload(MultipartHttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {

        List<MultipartFile> fileList = request.getFiles("file");
        String radio = request.getParameter("selection");
        Map results = new HashMap<String, Object>();

        try {

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Calendar calendar = Calendar.getInstance();
            String nowPath = sdf.format(date);

            File upload = new File(httpSession.getServletContext().getRealPath(File.separator) + "/resources" + nowPath);

            if (upload.isDirectory() == false) {
                upload.mkdirs();
            }

            loopFile:
            for (int i = 0; i < fileList.size(); i++) {

                if (fileList.get(i).getSize() == 0) {
                    // input 태그가 두개라서 둘 중 하나는 빈 곳 존재
                    continue;
                }
                File newFile = new File(upload.getPath() + "/" + i + ".png");
                File tempFile = new File(fileTempPath + i + ".png");

                fileList.get(i).transferTo(newFile);

                this.fileCopy(newFile.getPath(), tempFile.getPath());

            }

            results.put("status", "OK");

        } catch (Exception e) {
            e.printStackTrace();
            results.put("status", "FAIL");
            results.put("msg", e.getMessage());
        }

        Map results2 = new HashMap<String, Object>();

        try {

            // 결과물(zip파일) 저장할 디렉토리 설정
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Calendar calendar = Calendar.getInstance();
            String nowPath = sdf.format(date);

            String finalPath = httpSession.getServletContext().getRealPath(File.separator) + "/results" + nowPath;
            File download = new File(finalPath);

            if (!download.isDirectory()) {
                download.mkdirs();
            }

            // upload로 받았던 이미지들 업스케일링 && 리턴된 result 폴더 위치의 파일들을 압축
            System.out.println("doConvert enter");
            String tempStr = upscaleService.doConvert(radio);
            System.out.println("doConvert escape");
            doZip(tempStr, finalPath, "result.zip", response);

            System.out.println("zip and download END");
            results2.put("status", "OK");

        } catch (Exception e) {
            e.printStackTrace();
            results2.put("status", "FAIL");
            results2.put("msg", e.getMessage());
        }

        return "html/index";

    }

    //파일을 복사하는 메소드
    public static void fileCopy(String inFileName, String outFileName) {
        try {
            FileInputStream fis = new FileInputStream(inFileName);
            FileOutputStream fos = new FileOutputStream(outFileName);

            int data = 0;
            while ((data = fis.read()) != -1) {
                fos.write(data);
            }
            fis.close();
            fos.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // inFileParent 폴더 내 파일을 압축하여 zipFilePath 폴더 내 zipFileName 의 이름으로 저장하는 메소드
    public int doZip(String inFileParent, String zipFilePath, String zipFileName, HttpServletResponse response) {

        ZipOutputStream zout = null;
        System.out.println("dozip start");

        // 경로 폴더 내 파일들을 읽어옴
        try {
            List<File> fileList = Files.walk(Paths.get(inFileParent))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            if (!fileList.isEmpty()) {

                zout = new ZipOutputStream(new FileOutputStream(zipFilePath + zipFileName));
                byte[] buffer = new byte[1024];
                FileInputStream fis1 = null;

                for (int i = 0; i < fileList.size(); i++) {

                    fis1 = new FileInputStream(fileList.get(i).getPath());
                    zout.putNextEntry(new ZipEntry(fileList.get(i).getName()));

                    int len;
                    while ((len = fis1.read(buffer)) > 0) {
                        zout.write(buffer, 0, len);
                    }
                    zout.closeEntry();
                    fis1.close();
                }
                zout.close();

//파일다운로드 START
                response.setContentType("application/zip");
                response.addHeader("Content-Disposition", "attachment;filename=" + zipFileName);
                FileInputStream fis = new FileInputStream(zipFilePath + zipFileName);
                BufferedInputStream bis = new BufferedInputStream(fis);

                ServletOutputStream so = response.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(so);

                int n = 0;
                while ((n = bis.read(buffer)) > 0) {
//                    response.getWriter().write(n);
                    bos.write(buffer, 0, n);
                    bos.flush();
                }
//                bis.close();
//                fis.close();

                bos.close();
                bis.close();
                so.close();
                fis.close();

//                response.getWriter().close();
                System.out.println("writer close");

            } else {
                System.out.println("filelist is empty");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zout != null) {
                zout = null;
            }

        }
        System.out.println("dozip end");
        return 0;
    }
}


