package com.homihq.manager.core.seo;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SitemapController {
    private final List<String> URLS = List.of("/", "/signin", "/signup");
    private final String DOMAIN = "https://getarjun.com";

    @GetMapping(value = "/sitemap.txt", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String main() {

        List<String> links = new ArrayList<>();
        for (String link : URLS) {
            links.add(DOMAIN + link);
        }

        return String.join("\n", links);
    }



}
