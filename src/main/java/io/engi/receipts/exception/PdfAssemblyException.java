package io.engi.receipts.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="PDF creation failed")
@Getter
public class PdfAssemblyException extends RuntimeException {
    private final String message;

    public PdfAssemblyException(String message) {
        super(message);
        this.message = message;
    }
}
