package com.kyobo.platform.donots.common.exception;

public class PasswordIncludePersonalInformation extends BusinessException {

    public PasswordIncludePersonalInformation() {
        super(ErrorCode.PASSWORD_INCLUDE_PERSONAL_INFORMATION.status, ErrorCode.PASSWORD_INCLUDE_PERSONAL_INFORMATION.message);
    }

    public PasswordIncludePersonalInformation(String caseSpecificMessage) {
        super(ErrorCode.PASSWORD_INCLUDE_PERSONAL_INFORMATION.status, caseSpecificMessage);
    }
}
