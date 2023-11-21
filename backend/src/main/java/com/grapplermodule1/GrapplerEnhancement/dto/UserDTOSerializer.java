package com.grapplermodule1.GrapplerEnhancement.dto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;


public class UserDTOSerializer extends JsonSerializer<UsersDTO> {
    @Override
    public void serialize(UsersDTO usersDTO, JsonGenerator jsonGenerator, SerializerProvider serializers)
            throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", usersDTO.getId());
        jsonGenerator.writeStringField("name", usersDTO.getName());
        jsonGenerator.writeStringField("email", usersDTO.getEmail());
        jsonGenerator.writeStringField("designation", usersDTO.getDesignation());
    }
}
