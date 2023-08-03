package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.ChatMessage;


public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long>,
    JpaSpecificationExecutor<ChatMessage>{

}
