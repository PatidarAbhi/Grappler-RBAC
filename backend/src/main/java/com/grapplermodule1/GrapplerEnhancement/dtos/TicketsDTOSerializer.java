package com.grapplermodule1.GrapplerEnhancement.dtos;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;


public class TicketsDTOSerializer extends JsonSerializer<TicketsDTO> {

    private JsonGenerator jsonGenerator;

    @Override
    public void serialize(TicketsDTO ticketsDTO, JsonGenerator jsonGenerator, SerializerProvider serializers)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", ticketsDTO.getId());
        jsonGenerator.writeStringField("name", ticketsDTO.getName());
        jsonGenerator.writeNumberField("projectId", ticketsDTO.getProjectId());
        jsonGenerator.writeStringField("creatorName", ticketsDTO.getCreatorName());
    }
}