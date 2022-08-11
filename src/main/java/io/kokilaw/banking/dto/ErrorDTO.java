package io.kokilaw.banking.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * Created by kokilaw on 2022-08-11
 */
@Data
public class ErrorDTO {

    private String message;
    private String additionalInfo;
    private String errorCode;
    private List<String> subErrors = Collections.emptyList();

}
