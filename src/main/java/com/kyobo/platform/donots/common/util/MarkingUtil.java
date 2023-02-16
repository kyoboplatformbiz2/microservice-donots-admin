package com.kyobo.platform.donots.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class MarkingUtil {


    public String nameMasking(String name){
        String regex = "(^[가-힣]+)$";

        Matcher matcher = Pattern.compile(regex).matcher(name);
        if(matcher.find()) {
            int length = name.length();

            String middleMask = "";
            if(length > 2) {
                middleMask = name.substring(1, length - 1);
            } else {	// 이름이 외자
                middleMask = name.substring(1, length);
            }

            String dot = "";
            for(int i = 0; i<middleMask.length(); i++) {
                dot += "*";
            }

            if(length > 2) {
                return name.substring(0, 1)
                        + middleMask.replace(middleMask, dot)
                        + name.substring(length-1, length);
            } else { // 이름이 외자 마스킹 리턴
                return name.substring(0, 1)
                        + middleMask.replace(middleMask, dot);
            }
        }
        return name;
    }

    // 휴대폰번호 마스킹(가운데 숫자 4자리 마스킹)
    public String phoneMasking(String phoneNo) {
        String regex = "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$";

        Matcher matcher = Pattern.compile(regex).matcher(phoneNo);
        if(matcher.find()) {
            String target = matcher.group(2);
            int length = target.length();
            char[] c = new char[length];
            Arrays.fill(c, '*');

            return phoneNo.replace(target, String.valueOf(c));
        }
        return phoneNo;
    }

    // 이메일 마스킹(앞3자리 이후 '@'전까지 마스킹)
    public String emailMasking(String email)  {
        String regex = "\\b(\\S+)+@(\\S+.\\S+)";
        Matcher matcher = Pattern.compile(regex).matcher(email);
        if (matcher.find()) {
            String id = matcher.group(1); // 마스킹 처리할 부분인 userId
            /*
             * userId의 길이를 기준으로 세글자 초과인 경우 뒤 세자리를 마스킹 처리하고,
             * 세글자인 경우 뒤 두글자만 마스킹,
             * 세글자 미만인 경우 모두 마스킹 처리
             */
            int length = id.length();
            if (length < 3) {
                char[] c = new char[length];
                Arrays.fill(c, '*');
                return email.replace(id, String.valueOf(c));
            } else if (length == 3) {
                return email.replaceAll("\\b(\\S+)[^@][^@]+@(\\S+)", "$1**@$2");
            } else {
                return email.replaceAll("\\b(\\S+)[^@][^@][^@]+@(\\S+)", "$1***@$2");
            }
        }
        return email;
    }

    // 생년월일 마스킹(8자리)
    public String birthMasking(String birthday) throws Exception {
        String regex = "^((19|20)\\d\\d)?([-/.])?(0[1-9]|1[012])([-/.])?(0[1-9]|[12][0-9]|3[01])$";

        Matcher matcher = Pattern.compile(regex).matcher(birthday);
        if(matcher.find()) {
            return birthday.replace("[0-9]", "*");
        }
        return birthday;
    }

    public String idMasking(String id) {
        return koreanEnglishNumberSignMasking(id, 4);
    }

    public String nicknameMasking(String nickname) {
        return koreanEnglishNumberSignMasking(nickname, 4);
    }

    public String emailUsername4LettersMasking(String email) {
        String[] splittedEmail = email.split("@");

        String maskedEmailUsername = koreanEnglishNumberSignMasking(splittedEmail[0], 4);
        String maskedEmail = maskedEmailUsername + "@" + splittedEmail[1];

        return maskedEmail;
    }

    private String koreanEnglishNumberSignMasking(String stringToMask, int numOfMiddleLettersToMask){
        String regex = "(^[A-Za-zㄱ-ㅎㅏ-ㅣ가-힣0-9\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]+)$";

        Matcher matcher = Pattern.compile(regex).matcher(stringToMask);
        if(matcher.find()) {
            final int NUM_OF_LEADING_NON_MASKING_LETTERS = 1;

            String middleLettersToMask = "";
            int middleLettersToMaskLength = 0;
            if (stringToMask.length() > numOfMiddleLettersToMask) {
                middleLettersToMask = stringToMask.substring(NUM_OF_LEADING_NON_MASKING_LETTERS, numOfMiddleLettersToMask + 1);
                middleLettersToMaskLength = middleLettersToMask.length();
            } else {
                middleLettersToMaskLength = stringToMask.length();
            }

            String maskingSignsOfMatchingCount = "";
            for(int i = 0; i < middleLettersToMaskLength; i++) {
                maskingSignsOfMatchingCount += "*";
            }

            // 마스킹 대상 문자수가 마스킹 해야될 자릿수 이하이면 전부 마스킹한다.
            if(stringToMask.length() <= numOfMiddleLettersToMask) {
                return stringToMask.replace(stringToMask, maskingSignsOfMatchingCount);
            } else {
                String maskedString =
                        stringToMask.substring(0, NUM_OF_LEADING_NON_MASKING_LETTERS)
                                + middleLettersToMask.replace(middleLettersToMask, maskingSignsOfMatchingCount)
                                + stringToMask.substring(NUM_OF_LEADING_NON_MASKING_LETTERS + numOfMiddleLettersToMask);

                return maskedString;
            }
        }

        return stringToMask;
    }
}
