package kringlooptilburg.nl.productimageservice.listeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kringlooptilburg.nl.productimageservice.domain.dto.ProductImageDto;
import kringlooptilburg.nl.productimageservice.services.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class RabbitMQListener {

    private final ObjectMapper objectMapper;
    private final PhotoService photoService;

    @RabbitListener(queues = "${rabbitmq.queue.product-image}")
    public void receiveImages(String images) {
        try {
            var productImagesDto = objectMapper.readValue(images, new TypeReference<List<ProductImageDto>>() {
            });

            productImagesDto.forEach(productImageDto -> photoService.addPhoto(productImageDto.getTitle(), productImageDto.getImage(), productImageDto.getProductId()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
