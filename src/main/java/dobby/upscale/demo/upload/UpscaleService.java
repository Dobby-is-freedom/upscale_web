package dobby.upscale.demo.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("upscaleService")
public class UpscaleService {

    @Value("${file.conda.path}")
    String condaPath;

    @Value("${file.run.path}")
    String runPath;

    @Value("${file.model.path}")
    String modelPath;

    @Value("${file.input.path}")
    String inputPath;

    @Value("${file.output.path}")
    String outputPath;


    // return 값은 result 폴더의 경로
    public String doConvert(String radio) {

        try {

            boolean isWindows = System.getProperty("os.name")
                    .toLowerCase().startsWith("windows");

            System.out.println("실행환경이 윈도우인가? " + isWindows);

            System.out.println(radio);

            ProcessBuilder builder = new ProcessBuilder();
            if (isWindows) {
                // 실행할 파이썬 파일의 루트
                String runRoute = null;
                // 사용할 모델의 루트
                String modelRoute = null;
                // 기타 옵션
                String opthons = null;

                switch(radio) {
                    case "basic":
                        // Real-Esrgan
                        runRoute = runPath + "inference_realesrgan.py";
                        modelRoute = " --model_path " + modelPath + "RealESRGAN_x4plus.pth";
                        opthons = " --input " + inputPath + " --output " + outputPath;
                        break;
                    case "image":
                        runRoute = "";
                        modelRoute = "";
                        opthons = "";
                        break;
                    case "photo":
                        runRoute = "";
                        modelRoute = "";
                        opthons = "";
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + radio);
                }
                builder.command("cmd.exe", "/c", condaPath+" && " + "python "+runRoute+modelRoute+opthons);
            } else {
                builder.command("python", "/Users/psy/study/upscale_web/src/main/resources/python/test.py");
            }
            builder.directory(new File(System.getProperty("user.home")));
            Process process = builder.start();

            UploadShellService.StreamGobbler streamGobbler =
                    new UploadShellService.StreamGobbler(process.getInputStream(), System.out::println);
            ExecutorService tesk1 = Executors.newSingleThreadExecutor();
            tesk1.submit(streamGobbler);

            process.waitFor();
            System.out.println("END");

            process.destroy();
            tesk1.shutdownNow();

            return outputPath;

        } catch (IOException | InterruptedException e) {
            return "fail";
        }

    }

}
