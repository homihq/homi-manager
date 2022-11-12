package com.homihq.homimanager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HelloController {

    @GetMapping("/hi")
    public String hello() throws Exception {

        InetAddress inetAddress = InetAddress.getLocalHost();

        return "hi - " + inetAddress.getHostAddress() + " - " + inetAddress.getHostAddress();
    }
}
