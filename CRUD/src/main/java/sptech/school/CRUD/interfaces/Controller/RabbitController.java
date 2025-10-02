package sptech.school.CRUD.interfaces.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.CRUD.infrastructure.adapter.Rabbit.RabbitProducer;
import sptech.school.CRUD.interfaces.dto.Eventos.EventRequest;

@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    private final RabbitProducer producer;

    public RabbitController(RabbitProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public String send(@RequestBody EventRequest request) {
        producer.sendEvent(
                request.getEntity(),
                request.getEventType(),
                request.getEntityId()
        );
        return "Evento enviado: " + request;
    }

}
