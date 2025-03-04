package com.eric.todolist.controller;

import com.eric.todolist.config.security.UserDetail;
import com.eric.todolist.mapper.ChecklistItemTransform;
import com.eric.todolist.mapper.GlobalResponseTransform;
import com.eric.todolist.model.dto.request.ChecklistItemFilterRequest;
import com.eric.todolist.model.dto.request.ChecklistItemRequest;
import com.eric.todolist.model.dto.response.ChecklistItemResponse;
import com.eric.todolist.model.dto.response.GlobalResponse;
import com.eric.todolist.model.entity.ChecklistItem;
import com.eric.todolist.service.ChecklistItemService;
import com.eric.todolist.util.PageableUtil;
import com.eric.todolist.util.constant.ChecklistItemSortingConstant;
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

@Tag(name = "ChecklistItem", description = "Endpoints for managing checklist items within a checklist")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checklist/{checklistId}/item")
public class ChecklistItemController {

    private final ChecklistItemService checklistItemService;
    private final PageTransform checklistItemResponsePageTransform;
    private final ChecklistItemTransform checklistItemTransform;
    private final GlobalResponseTransform globalResponseTransform;

    @Operation(summary = "Get all checklist items for a checklist", description = "Retrieve a paginated list of items for a specific checklist, with optional filtering and sorting.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved checklist items", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content), @ApiResponse(responseCode = "404", description = "Checklist not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GlobalResponse<Object>> getChecklistItems(@Parameter(description = "ID of the checklist", required = true) @PathVariable int checklistId, @Parameter(description = "Pagination information (page, size, sort)") Pageable pageable, @Parameter(description = "Filter request object for checklist items", schema = @Schema(implementation = ChecklistItemFilterRequest.class)) ChecklistItemFilterRequest request) {
        Pageable sanitizedPage = PageableUtil.convertAndFilterSort(pageable, ChecklistItemSortingConstant.CHECKLIST_ITEMS_SORTING_FIELD);
        Page<ChecklistItem> checklistItemsPage = checklistItemService.getAllCheckListItems(checklistId, sanitizedPage, request);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(LocalDateTime.now(), StatusCode.OK, checklistItemResponsePageTransform.toPage(checklistItemsPage.getNumber(), checklistItemsPage.getTotalElements(), checklistItemsPage.getPageable().getOffset(), checklistItemsPage.getSize(), checklistItemsPage.getTotalPages(), checklistItemTransform.toChecklistItemResponses(checklistItemsPage.getContent()))));
    }

    @Operation(summary = "Add a new item to a checklist", description = "Add a new item to the specified checklist.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = GlobalMessage.Response.ChecklistItem.CREATE_CHECKLIST_ITEM, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GlobalResponse.class))), @ApiResponse(responseCode = "400", description = "Bad request - invalid checklist item data", content = @Content), @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content), @ApiResponse(responseCode = "404", description = "Checklist not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addChecklistItem(@Parameter(description = "ID of the checklist", required = true) @PathVariable int checklistId, @Valid @RequestBody @Parameter(description = "Checklist item request object", required = true, schema = @Schema(implementation = ChecklistItemRequest.class)) ChecklistItemRequest checklistItemDTO, @Parameter(hidden = true) @AuthenticationPrincipal UserDetail user) {
        ChecklistItem newChecklistItem = checklistItemService.createChecklistItem(checklistId, checklistItemDTO.getItemName(), user);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(LocalDateTime.now(), StatusCode.OK, GlobalMessage.Response.ChecklistItem.CREATE_CHECKLIST_ITEM, checklistItemTransform.toChecklistItemResponse(newChecklistItem)));
    }

    @Operation(summary = "Get checklist item details", description = "Retrieve details of a specific item within a checklist.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved checklist item details", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GlobalResponse.class))), @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content), @ApiResponse(responseCode = "404", description = "Checklist or checklist item not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping(value = "/{checklistItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getChecklistItemDetail(@Parameter(description = "ID of the checklist", required = true) @PathVariable("checklistId") int checklistId, @Parameter(description = "ID of the checklist item", required = true) @PathVariable("checklistItemId") int checklistItemId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetail user) {
        ChecklistItem checklistItem = checklistItemService.getCheckListItem(checklistId, checklistItemId, user);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(LocalDateTime.now(), StatusCode.OK, checklistItemTransform.toChecklistItemResponse(checklistItem)));
    }

    @Operation(summary = "Update checklist item status", description = "Update the status (e.g., complete/incomplete) of a specific item within a checklist.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Checklist item status updated successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GlobalResponse.class))), @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content), @ApiResponse(responseCode = "404", description = "Checklist or checklist item not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PutMapping(value = "/{checklistItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateChecklistItemStatus(@Parameter(description = "ID of the checklist", required = true) @PathVariable("checklistId") int checklistId, @Parameter(description = "ID of the checklist item", required = true) @PathVariable("checklistItemId") int checklistItemId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetail user) {
        ChecklistItem checklistItem = checklistItemService.updateCheckListItemStatus(checklistId, checklistItemId, user);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(LocalDateTime.now(), StatusCode.OK, checklistItemTransform.toChecklistItemResponse(checklistItem)));
    }

    @Operation(summary = "Delete a checklist item", description = "Delete a specific item from a checklist.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = GlobalMessage.Response.ChecklistItem.DELETE_CHECKLIST_ITEM, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{\"timestamp\": \"2024-07-24T12:00:00\", \"statusCode\": \"OK\", \"message\": \"Checklist item deleted successfully\", \"data\": null}"))), // Example response
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content), @ApiResponse(responseCode = "404", description = "Checklist or checklist item not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @DeleteMapping(value = "/{checklistItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteChecklistItem(@Parameter(description = "ID of the checklist", required = true) @PathVariable("checklistId") int checklistId, @Parameter(description = "ID of the checklist item", required = true) @PathVariable("checklistItemId") int checklistItemId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetail user) {
        checklistItemService.deleteCheckListItem(checklistId, checklistItemId, user);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(LocalDateTime.now(), StatusCode.OK, GlobalMessage.Response.ChecklistItem.DELETE_CHECKLIST_ITEM, null));
    }

    @Operation(summary = "Rename a checklist item", description = "Rename a specific item within a checklist.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Checklist item renamed successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GlobalResponse.class))), @ApiResponse(responseCode = "400", description = "Bad request - invalid checklist item data", content = @Content), @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content), @ApiResponse(responseCode = "404", description = "Checklist or checklist item not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PutMapping(value = "/rename/{checklistItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> renameChecklistItem(@Parameter(description = "ID of the checklist", required = true) @PathVariable("checklistId") int checklistId, @Parameter(description = "ID of the checklist item", required = true) @PathVariable("checklistItemId") int checklistItemId, @Valid @RequestBody @Parameter(description = "Checklist item response object with new item name", required = true, schema = @Schema(implementation = ChecklistItemResponse.class)) ChecklistItemResponse request, @Parameter(hidden = true) @AuthenticationPrincipal UserDetail user) {
        ChecklistItem updatedChecklistItem = checklistItemService.updateCheckListItem(checklistId, checklistItemId, request.getItemName(), user);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(LocalDateTime.now(), StatusCode.OK, checklistItemTransform.toChecklistItemResponse(updatedChecklistItem)));
    }
}