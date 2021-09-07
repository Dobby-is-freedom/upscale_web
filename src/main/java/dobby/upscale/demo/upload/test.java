package dobby.upscale.demo.upload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class test {
    @PostMapping(value = "/test1")
    public String doTest (HttpServletRequest request, HttpServletResponse response) {
        System.out.println("test!");
        return "html/index";
    }
}
