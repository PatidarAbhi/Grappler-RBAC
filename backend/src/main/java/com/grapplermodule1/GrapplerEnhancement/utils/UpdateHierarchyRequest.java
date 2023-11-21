package com.grapplermodule1.GrapplerEnhancement.utils;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateHierarchyRequest {

    @NotNull( message = "Dragged id is required")
    private  long draggedId;

    @NotNull( message = "Dropped id is required")
    private  long droppedId;
}
