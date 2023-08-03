package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private String titleUz;
    private String titleRu;
    private String titleBg;

    private String messageUz;
    private String messageRu;
    private String messageBg;

}
