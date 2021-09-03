package dobby.upscale.demo.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

                    if (fileList.get(i).getSize() == 0) {
                        // input 태그가 두개라서 둘 중 하나는 빈 곳 존재
                        continue;
                    }
                    File newFile = new File(upload.getPath() + "/" + i +".png");
                    File tempFile = new File("C:\\src\\git\\Real-ESRGAN-210902\\inputs\\" + i+".png");

                    fileList.get(i).transferTo(newFile);

                    this.fileCopy( newFile.getPath(), tempFile.getPath());

            }


            results.put("status", "OK");

        } catch (Exception e) {
            e.printStackTrace();
            results.put("status", "FAIL");
            results.put("msg", e.getMessage());
        }

        String ok = uploadTest(map.get("selection"));
        if (ok =="ok") {
            System.out.println(ok);
        } else {
            System.out.println(ok);
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

    // 파이썬 실행 코드
    public String uploadTest(String radio) {

        try {

            boolean isWindows = System.getProperty("os.name")
                    .toLowerCase().startsWith("windows");

            System.out.println("실행환경이 윈도우인가? " + isWindows);

            System.out.println(radio);

            ProcessBuilder builder = new ProcessBuilder();
            if (isWindows) {
                // 사용할 모델의 루트를 제외한 나머지
                String baseRoute = "C:\\ProgramData\\Anaconda3\\Scripts\\activate.bat " +
                        "C:\\ProgramData\\Anaconda3 " +
                        "&& cd C:\\src\\git\\Real-ESRGAN-210902 " +
                        "&& python inference_realesrgan.py --model_path";
                // 사용할 모델의 루트
                String modelRoute = null;
                switch(radio) {
                    case "basic":
                        modelRoute = " C:\\src\\git\\Real-ESRGAN-210902\\experiments\\pretrained_models\\RealESRGAN_x4plus.pth";
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
                builder.command("cmd.exe", "/c", baseRoute+modelRoute+" --input inputs --face_enhance");
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

            return "ok";
        } catch (IOException | InterruptedException e) {
            return "fail";
        }
    }

}


