package com.eric.todolist.controller;

import com.eric.todolist.config.security.UserDetail;
import com.eric.todolist.mapper.ChecklistTransform;
import com.eric.todolist.mapper.GlobalResponseTransform;
import com.eric.todolist.model.dto.request.CheckListRequest;
import com.eric.todolist.model.dto.request.ChecklistSearchRequest;
import com.eric.todolist.model.dto.response.GlobalResponse;
import com.eric.todolist.model.entity.Checklist;
import com.eric.todolist.service.ChecklistService;
import com.eric.todolist.util.PageableUtil;
import com.eric.todolist.util.constant.ChecklistSortingConstant;
import com.eric.todolist.util.constant.GlobalMessage;
import com.eric.todolist.util.enums.StatusCode;
import com.eric.todolist.util.transform.PageTransform;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Checklist", description = "Endpoints for managing checklists")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checklist")
public class CheckListController {

    private final GlobalResponseTransform globalResponseTransform;
    private final PageTransform checklistResponsePageTransform;
    private final ChecklistService checklistService;
    private final ChecklistTransform checklistTransform;

    @Operation(summary = "Get all checklists", description = "Retrieve a paginated list of checklists for the authenticated user, with optional search and sorting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved checklists",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GlobalResponse<Object>> getAllCheckLists(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetail user,
            @Parameter(description = "Pagination information (page, size, sort)") Pageable pageable,
            @Parameter(description = "Search request object for filtering checklists") ChecklistSearchRequest request) {
        Pageable sanitizedPage = PageableUtil.convertAndFilterSort(pageable, ChecklistSortingConstant.CHECKLIST_SORTING_FIELD);
        Page<Checklist> checklistsPage = checklistService.getAllChecklistsByUsername(user, sanitizedPage, request);
        return ResponseEntity.ok(
                globalResponseTransform.generateResponse(
                        LocalDateTime.now(),
                        StatusCode.OK,
                        checklistResponsePageTransform.toPage(
                                checklistsPage.getNumber(),
                                checklistsPage.getTotalElements(),
                                checklistsPage.getPageable().getOffset(),
                                checklistsPage.getSize(),
                                checklistsPage.getTotalPages(),
                                checklistTransform.toChecklistResponses(checklistsPage.getContent())
                        )
                )
        );
    }

    @Operation(summary = "Create a new checklist", description = "Create a new checklist for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = GlobalMessage.Response.Checklist.CREATE_CHECKLIST,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(example = "{\"timestamp\": \"2024-07-24T12:00:00\", \"statusCode\": \"OK\", \"message\": \"Checklist created successfully\", \"data\": null}"))),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid checklist data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createChecklist(
            @Valid @RequestBody @Parameter(description = "Checklist request object", required = true, schema = @Schema(implementation = CheckListRequest.class)) CheckListRequest checklistDTO,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetail user
    ) {
        checklistService.createChecklist(checklistDTO.getName(), user);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(
                LocalDateTime.now(),
                StatusCode.OK,
                GlobalMessage.Response.Checklist.CREATE_CHECKLIST,
                null
        ));
    }

    @Operation(summary = "Delete a checklist", description = "Delete a specific checklist by its ID for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = GlobalMessage.Response.Checklist.DELETE_CHECKLIST,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(example = "{\"timestamp\": \"2024-07-24T12:00:00\", \"statusCode\": \"OK\", \"message\": \"Checklist deleted successfully\", \"data\": null}"))), // Example response
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Checklist not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping(
            value = "/{checklistId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> deleteChecklist(
            @Parameter(description = "ID of the checklist to delete", required = true) @PathVariable int checklistId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetail user
    ) {
        checklistService.deleteChecklist(checklistId, user);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(
                LocalDateTime.now(),
                StatusCode.OK,
                GlobalMessage.Response.Checklist.DELETE_CHECKLIST,
                null
        ));
    }
}
