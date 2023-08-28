package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.OrderDto;
import uz.bprodevelopment.logisticsapp.dto.OrderStackDto;
import uz.bprodevelopment.logisticsapp.entity.Order;
import uz.bprodevelopment.logisticsapp.entity.OrderStack;
import uz.bprodevelopment.logisticsapp.repo.OrderRepo;
import uz.bprodevelopment.logisticsapp.repo.OrderStackRepo;
import uz.bprodevelopment.logisticsapp.spec.OrderStackSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderStackServiceImpl implements OrderStackService {

    private final OrderStackRepo repo;
    private final OrderRepo orderRepo;
    private final MessageSource messageSource;

    @Override
    public OrderStackDto getOne(Long id) {
        OrderStack orderStack = repo.getReferenceById(id);
        List<Order> orders = orderRepo.findAllByOrderStackId(id);
        List<OrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> orderDtos.add(order.toDto()));
        OrderStackDto orderStackDto = orderStack.toDto();
        orderStackDto.setOrders(orderDtos);
        return orderStackDto;
    }

    @Override
    public CustomPage<OrderStackDto> getList(
            Integer page,
            Integer size,
            String fullName,
            String sort
    ) {

        OrderStackSpec spec1 = new OrderStackSpec(new SearchCriteria("id", ">", 0));
        Specification<OrderStack> spec = Specification.where(spec1);

        if (fullName != null) {
            spec = spec.and(new OrderStackSpec(new SearchCriteria("fullName", "=", fullName)));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        Page<OrderStack> orderPage = repo.findAll(spec, pageable);
        List<OrderStackDto> orderDtos = new ArrayList<>();

        orderPage.getContent().forEach(order -> orderDtos.add(order.toDto()));

        return new CustomPage<>(
                orderDtos,
                orderPage.isFirst(),
                orderPage.isLast(),
                orderPage.getNumber(),
                orderPage.getTotalPages(),
                orderPage.getTotalElements()
        );
    }

    @Override
    public void delete(Long id) {
        if (!orderRepo.findAllByOrderStackId(id).isEmpty()) {
            throw new RuntimeException(messageSource.getMessage("stack_has_orders", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        repo.deleteById(id);
    }

}
