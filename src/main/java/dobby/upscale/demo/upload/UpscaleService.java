package dobby.upscale.demo.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("upscaleService")
public class UpscaleService {

    public String doConvert(String radio) {

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
