package com.kyobo.platform.donots.controller;

import com.kyobo.platform.donots.common.exception.RequestBodyEmptyException;
import com.kyobo.platform.donots.model.dto.response.ParentAccountDetailsResponse;
import com.kyobo.platform.donots.model.dto.response.ParentAccountResponse;
import com.kyobo.platform.donots.model.entity.service.parent.ParentType;
import com.kyobo.platform.donots.model.repository.searchcondition.ParentAccountSearchCondition;
import com.kyobo.platform.donots.service.MemberManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
public class MemberManagementController {
    MemberManagementService memberManagementService;

    @Operation(summary = "[회원관리] 회원+계정.목록 > 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping("/v1/members/with-account")
    // Parameter로 받을 때 400 Bad Request 발생하는 것을 확인한 형태:
    // @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime someDateTime
    // @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime someDateTime
    public ResponseEntity<?> findAllMembersWithAccount(ParentAccountSearchCondition condition, String joinFrom, String joinTo, Pageable pageable) {
        log.info("MemberManagementController.findAllMembersWithAccount");
        log.info("joinFrom: "+ joinFrom);
        log.info("joinTo: "+ joinTo);
        condition.setJoinDateFrom(LocalDate.parse(joinFrom).atStartOfDay());
        condition.setJoinDateTo(LocalDate.parse(joinTo).atStartOfDay());

        Page<ParentAccountResponse> parentWithAccountResponses = memberManagementService.findAllMembersWithAccount(condition, pageable);

        return ResponseEntity.ok(parentWithAccountResponses);
    }

    @Operation(summary = "[회원관리] 회원관리.회원+계정상세 > 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping("/v1/members/with-account/{parentKey}/details")
    public ResponseEntity<?> findParentAccountDetails(@PathVariable Long parentKey) throws Exception {
        log.info("MemberManagementController.findParentAccountDetails");
        // TODO 예외처리

        ParentAccountDetailsResponse parentAccountDetailsResponse = memberManagementService.findParentAccountDetails(parentKey);

        return ResponseEntity.ok(parentAccountDetailsResponse);
    }

    @Operation(summary = "[회원관리 상세] 회원.유형 > 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PutMapping("/v1/members/{key}/type")
    public ResponseEntity modifyParentType(@PathVariable Long key, @RequestBody Map<String, ParentType> parentTypeMap) {
        log.info("ParentController.modifyParentType");

        if (parentTypeMap == null)
            throw new RequestBodyEmptyException("RequestBody is empty");

        ParentType parentType = parentTypeMap.get("type");

        if (parentType == null)
            throw new RequestBodyEmptyException("RequestBody is empty");

        memberManagementService.modifyParentType(key, parentType);

        return ResponseEntity.ok().build();
    }
}
