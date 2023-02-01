package com.kyobo.platform.donots.common.exception;

public class TermsOfServiceNotFoundException extends BusinessException {

    public TermsOfServiceNotFoundException() {
        super(ErrorCode.REQUEST_BODY_IS_EMPTY.status, ErrorCode.REQUEST_BODY_IS_EMPTY.message);
    }

    public TermsOfServiceNotFoundException(String caseSpecificMessage) {
        super(ErrorCode.REQUEST_BODY_IS_EMPTY.status, caseSpecificMessage);
    }
}
