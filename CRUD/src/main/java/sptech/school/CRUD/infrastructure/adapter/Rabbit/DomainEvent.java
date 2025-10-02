package sptech.school.CRUD.infrastructure.adapter.Rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainEvent {
    private String entity;
    private String eventType;
    private String entityId;
    private String timestamp;
}
