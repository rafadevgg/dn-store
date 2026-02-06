package com.ecommerce.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(

        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path,
        Map<String, String> errors

) {}