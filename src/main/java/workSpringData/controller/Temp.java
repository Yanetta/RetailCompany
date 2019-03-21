package workSpringData.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/temp")
public class Temp {

    @GetMapping
    public @ResponseBody
    String index() {
        return "YYYYYYYYYYYYYY";
    }
}



