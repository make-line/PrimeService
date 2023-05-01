package com.example.registrationservice.model;

import com.example.registrationservice.model.enums.Stage;

import java.time.LocalDateTime;

public class NotifyParams {
    private LocalDateTime notifyFirst;

    private LocalDateTime notifySecond;

    private LocalDateTime notifyFinal;

    private Stage status;
}
