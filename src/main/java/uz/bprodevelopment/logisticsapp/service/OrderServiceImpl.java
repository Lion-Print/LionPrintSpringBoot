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
import uz.bprodevelopment.logisticsapp.dto.OrderDto;
import uz.bprodevelopment.logisticsapp.dto.ProductDetailDto;
import uz.bprodevelopment.logisticsapp.entity.Order;
import uz.bprodevelopment.logisticsapp.entity.ProductDetail;
import uz.bprodevelopment.logisticsapp.repo.OrderRepo;
import uz.bprodevelopment.logisticsapp.repo.ProductDetailRepo;
import uz.bprodevelopment.logisticsapp.spec.OrderSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepo repo;
    private final ProductDetailRepo productDetailRepo;

    @Override
    public OrderDto getOne(Long id) {
        Order order = repo.getReferenceById(id);
        return order.toDto();
    }

    @Override
    public List<OrderDto> getListAll(
            Long productId,
            String sort
    ) {
        OrderSpec spec1 = new OrderSpec(new SearchCriteria("id", ">", 0));
        Specification<Order> spec = Specification.where(spec1);

        if (productId != null) spec = spec.and(new OrderSpec(new SearchCriteria("productId", "=", productId)));

        List<Order> orders = repo.findAll(spec, Sort.by(sort).descending());
        List<OrderDto> orderDtos = new ArrayList<>();

        orders.forEach(order -> orderDtos.add(order.toDto()));


        return orderDtos;
    }

    @Override
    public CustomPage<OrderDto> getList(
            Integer page,
            Integer size,
            Long productId,
            String sort
    ) {

        OrderSpec spec1 = new OrderSpec(new SearchCriteria("id", ">", 0));
        Specification<Order> spec = Specification.where(spec1);

        if (productId != null) spec = spec.and(new OrderSpec(new SearchCriteria("productId", "=", productId)));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        Page<Order> orderPage = repo.findAll(spec, pageable);
        List<OrderDto> orderDtos = new ArrayList<>();

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
    public void save(OrderDto item) {
        if(item.getProductId() == null) {
            throw new RuntimeException("productId is null");
        }
        if (item.getAmount() == null) {
            throw new RuntimeException("amount is null");
        }

        Order category = item.toEntity();
        repo.save(category);

    }

    @Override
    public void update(OrderDto item) {
        Order dbOrder = repo.getReferenceById(item.getId());
        if(item.getProductId() == null) {
            throw new RuntimeException("productId is null");
        }
        if(item.getAmount() == null) {
            throw new RuntimeException("amount is null");
        }
        Order order = item.toEntity();
        repo.save(order);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
