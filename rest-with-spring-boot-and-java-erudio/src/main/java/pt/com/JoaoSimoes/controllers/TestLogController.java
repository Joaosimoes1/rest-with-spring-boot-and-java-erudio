package pt.com.JoaoSimoes.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.com.JoaoSimoes.services.PersonService;


@RestController
public class TestLogController {

    private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    @GetMapping("/test")
    public String testLog(){
        logger.debug("Thi is an DEBUG log");
        logger.info("Thi is an INFO log");
        logger.warn("Thi is an WARN log");
        logger.error("Thi is an ERROR log");
        return "Logs generated sucessfully";
    }
}
