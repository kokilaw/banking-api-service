package io.kokilaw.banking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Created by kokilaw on 2022-08-09
 */
@RestController
@RequestMapping("/health/check")
public class HealthController {

    @GetMapping
    public Map<String, Boolean> getHealthStatus() {
        return Collections.singletonMap("status", true);
    }

}
