package sptech.school.CRUD.interfaces.dto.Eventos;

import lombok.Data;

@Data
public class EventRequest {
    private String entity;     // Ex: "User"
    private String eventType;  // Ex: "CREATED"
    private String entityId;

}
