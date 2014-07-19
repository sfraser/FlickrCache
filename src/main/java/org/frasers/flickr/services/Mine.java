package org.frasers.flickr.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sfraser on 7/18/14.
 */
@Controller
@RequestMapping("/mine")
public class Mine {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody
    SlimserverScreensaverResponse getMine() {
        return new SlimserverScreensaverResponse(
                "http://fraser.blogs.com/photos/uncategorized/2007/04/28/redbelliedwoodpecker3.jpeg",
                "Test image from Spring Boot");
    }


}
