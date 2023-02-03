package com.kyobo.platform.donots.common.exception;

public class TermsOfServiceNotFoundException extends BusinessException {

    public TermsOfServiceNotFoundException() {
        super(ErrorCode.TERMS_OF_SERVICE_NOT_FOUND.status, ErrorCode.TERMS_OF_SERVICE_NOT_FOUND.message);
    }

    public TermsOfServiceNotFoundException(String caseSpecificMessage) {
        super(ErrorCode.TERMS_OF_SERVICE_NOT_FOUND.status, caseSpecificMessage);
    }
}
