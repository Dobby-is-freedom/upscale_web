package dobby.upscale.demo.upload;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/uploadTest")
    @ResponseBody
    public String uploadTest() {

        try {

            boolean isWindows = System.getProperty("os.name")
                    .toLowerCase().startsWith("windows");

            System.out.println("실행환경이 윈도우인가? " + isWindows);

            // 라디오버튼의 값
            String radio = "basic";

            ProcessBuilder builder = new ProcessBuilder();
            if (isWindows) {
                // 사용할 모델의 루트를 제외한 나머지
                String baseRoute = "C:\\ProgramData\\Anaconda3\\Scripts\\activate.bat " +
                        "C:\\ProgramData\\Anaconda3 " +
                        "&& cd C:\\src\\git\\iNNfer && python run.py -m ";
                // 사용할 모델의 루트
                String modelRoute = null;
                switch(radio) {
                    case "basic":
                        modelRoute = "C:\\src\\git\\iNNfer\\models\\RRDB_ESRGAN_x4.pth";
                        break;
                    case "image":
                        modelRoute = "";
                        break;
                    case "photo":
                        modelRoute = "";
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + radio);
                }
                builder.command("cmd.exe", "/c", baseRoute+modelRoute);
            } else {
                builder.command("python", "/Users/psy/study/upscale_web/src/main/resources/python/test.py");
            }
            builder.directory(new File(System.getProperty("user.home")));
            Process process = builder.start();

            UploadShellService.StreamGobbler streamGobbler =
                    new UploadShellService.StreamGobbler(process.getInputStream(), System.out::println);
            ExecutorService tesk1 = Executors.newSingleThreadExecutor();
            tesk1.submit(streamGobbler);

            //Executors.newSingleThreadExecutor().submit(streamGobbler);
            process.waitFor();
            System.out.println("END");

            process.destroy();
            tesk1.shutdownNow();
            //System.exit(0);

            return "ok";
        } catch (IOException | InterruptedException e) {
            return "fail";
        }
    }


    @RequestMapping(value = "/upload")
    public
 String doUpload(@RequestParam Map<String, String> map, @RequestParam("file") List<MultipartFile> fileList, HttpSession httpSession) {

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


                    File newFile = new File(upload.getPath() + "/" + i );
                    File tempFile = new File("D:\\Study\\upscale_web\\out\\production\\resources\\" + i);

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


