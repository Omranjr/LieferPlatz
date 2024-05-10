package com.LieferPlatz.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FallBackController {

    @GetMapping("/*")
    public String redirectToNotFound() {
        return "not-found";
    }
}
