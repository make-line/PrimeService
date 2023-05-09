package com.example.primeservice.model;

import com.example.primeservice.model.enums.Stage;

import java.time.LocalDateTime;

public class NotifyParams {
    private LocalDateTime notifyFirst;

    private LocalDateTime notifySecond;

    private LocalDateTime notifyFinal;

    private Stage status;
}
