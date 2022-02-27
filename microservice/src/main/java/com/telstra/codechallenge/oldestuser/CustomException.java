package com.telstra.codechallenge.oldestuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Data
public class CustomException extends RuntimeException {
    private final String message;
    private final HttpStatus status;
    private final int responseCode;
}
