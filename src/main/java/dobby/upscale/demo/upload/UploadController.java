package dobby.upscale.demo.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
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
}

