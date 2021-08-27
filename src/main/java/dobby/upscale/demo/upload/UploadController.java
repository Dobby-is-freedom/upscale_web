package dobby.upscale.demo.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

@Controller
public class UploadController {

    @Autowired
    UploadShellService uploadShellService;


    @RequestMapping(value = "/uploadTest")
    public String uploadTest() throws IOException, InterruptedException {


        try {


            boolean isWindows = System.getProperty("os.name")
                    .toLowerCase().startsWith("windows");

            System.out.println("실행환경이 윈도우인가? " + isWindows);

            ProcessBuilder builder = new ProcessBuilder();
            if (isWindows) {
                builder.command("cmd.exe", "/c", "cd C:\\Users\\qkrdu\\Desktop\\iNNfer-main\\iNNfer-main && python run.py -m fatal");
            } else {
                builder.command("python", "/Users/psy/study/upscale_web/src/main/resources/python/test.py");
            }
            builder.directory(new File(System.getProperty("user.home")));
            Process process = builder.start();
            UploadShellService.StreamGobbler streamGobbler =
                    new UploadShellService.StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
            assert exitCode == 0;
            System.out.println("END");
            System.exit(0);


            return "ok";
        }catch (Exception e) {
            return "fail";
        }



    }

}

