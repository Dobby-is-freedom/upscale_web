package dobby.upscale.demo.upload;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class DownloadController {

    @Autowired
    protected UpscaleService upscaleService;

    @PostMapping(value = "/download")
    public String doDownload(@RequestParam Map<String, String> map ) {

        String radio = map.get("selection");

        try {
            upscaleService.doConvert(radio);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "html/index";
    }
}
