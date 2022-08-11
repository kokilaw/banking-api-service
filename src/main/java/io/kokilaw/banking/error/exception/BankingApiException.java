package io.kokilaw.banking.error.exception;

import io.kokilaw.banking.dto.ErrorDTO;
import io.kokilaw.banking.error.ApiError;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * Created by kokilaw on 2022-08-11
 */
public class BankingApiException extends RuntimeException {

    private final ApiError apiError;
    private final String additionalInfo;
    private List<String> subErrors = Collections.emptyList();

    public BankingApiException(ApiError apiError, String additionalInfo) {
        super(apiError.getErrorMessage());
        this.apiError = apiError;
        this.additionalInfo = additionalInfo;
    }

    public BankingApiException(ApiError apiError, String additionalInfo, List<String> subErrors) {
        super(apiError.getErrorMessage());
        this.apiError = apiError;
        this.additionalInfo = additionalInfo;
        this.subErrors = subErrors;
    }

    public HttpStatus getHttpStatus() {
        return this.apiError.getHttpStatus();
    }

    public ErrorDTO getErrorResponse() {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(this.apiError.getErrorMessage());
        errorDTO.setAdditionalInfo(this.additionalInfo);
        errorDTO.setErrorCode(this.apiError.getErrorCode());
        errorDTO.setSubErrors(subErrors);
        return errorDTO;
    }


}
