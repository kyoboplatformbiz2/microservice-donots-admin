package com.kyobo.platform.donots.common.exception;

public class ParentNotFoundException extends BusinessException {

    public ParentNotFoundException() {
        super(ErrorCode.PARENT_NOT_FOUND.status, ErrorCode.PARENT_NOT_FOUND.message);
    }

    public ParentNotFoundException(String caseSpecificMessage) {
        super(ErrorCode.PARENT_NOT_FOUND.status, caseSpecificMessage);
    }
}
