package org.example.loginspring_adriansaavedra.common.errors;

import org.springframework.http.HttpStatus;

public record ApiError(String message, HttpStatus status) {
}
