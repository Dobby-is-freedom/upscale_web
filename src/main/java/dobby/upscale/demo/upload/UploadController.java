package dobby.upscale.demo.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class UploadController {

    @Autowired
    UploadShellService uploadShellService;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public
 String doUpload(@RequestParam("file") List<MultipartFile> fileList, HttpSession httpSession) {

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
                    File newFile = new File(upload.getPath() + "/" + i +".png");
                    File tempFile = new File("C:/src/git/Real-ESRGAN-210902/inputs/" + i+".png");

                    fileList.get(i).transferTo(newFile);

                    this.fileCopy( newFile.getPath(), tempFile.getPath());

            }

            results.put("status", "OK");

        } catch (Exception e) {
            e.printStackTrace();
            results.put("status", "FAIL");
            results.put("msg", e.getMessage());
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

}


