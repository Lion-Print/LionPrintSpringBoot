package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.OrderDto;
import uz.bprodevelopment.logisticsapp.entity.Order;
import uz.bprodevelopment.logisticsapp.repo.OrderRepo;
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
    private final UserRepo userRepo;

    @Override
    public OrderDto getOne(Long id) {
        Order order = repo.getReferenceById(id);
        return order.toDto();
    }

    @Override
    public CustomPage<OrderDto> getList(
            Integer page,
            Integer size,
            String name,
            String sort
    ) {

        OrderSpec spec1 = new OrderSpec(new SearchCriteria("id", ">", 0));
        Specification<Order> spec = Specification.where(spec1);

        if (name != null) {
            Specification<Order> spec2 = Specification.where(new OrderSpec(new SearchCriteria("nameUz", ":", name)));
            spec2 = spec2.or(new OrderSpec(new SearchCriteria("nameRu", ":", name)));
            spec = spec.and(spec2);
        }


        User user = userRepo.getReferenceById(BaseAppUtils.getUserId());

        if (user.getCompany() != null) {
            spec = spec.and(new OrderSpec(new SearchCriteria("companyId", "=", user.getCompany().getId())));
        }

        if (user.getSupplier() != null) {
            spec = spec.and(new OrderSpec(new SearchCriteria("supplierId", "=", user.getSupplier().getId())));
        }

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
        item.setStatus(1);
        Order category = item.toEntity();
        repo.save(category);

    }

    @Override
    public void update(OrderDto item) {
        Order dbOrder = repo.getReferenceById(item.getId());
        if (dbOrder.getStatus() > 1 || !dbOrder.getStatus().equals(item.getStatus())) {
            throw new RuntimeException("You can't update order which is status > 1");
        }
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
        Order dbOrder = repo.getReferenceById(id);
        if (dbOrder.getStatus() > 1) {
            throw new RuntimeException("You can't delete order which is status > 1");
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public void changeStatus(Long id, Integer status) {
        Order order = repo.getReferenceById(id);
        User currentUser = userRepo.getReferenceById(BaseAppUtils.getUserId());
        if (currentUser.getSupplier() != null) {
            if (!order.getSupplier().getId().equals(currentUser.getSupplier().getId())) {
                throw new RuntimeException("You are other supplier");
            }
            if (status == -1 && order.getStatus() > 3) {
                throw new RuntimeException("This order delivered. You can't cancel it");
            }
            if (status != -1 && !order.getStatus().equals(status - 1)) {
                throw new RuntimeException("You can increase status only 1");
            }
            if (status == 4) {
                throw new RuntimeException("You can't mark as delivered order. Only companies can mark as delivered");
            }
        }
        if (currentUser.getCompany() != null) {
            if (!order.getCompany().getId().equals(currentUser.getCompany().getId())) {
                throw new RuntimeException("You are other company");
            }
            if (status == -1 && order.getStatus() > 2) {
                throw new RuntimeException("This order delivered or on the way. You can't cancel it");
            }
            if (status != -1 && !order.getStatus().equals(status - 1)) {
                throw new RuntimeException("You can increase status only 1");
            }
            if (status == 2 || status == 3) {
                throw new RuntimeException("You can't mark as received or on the way. Only suppliers can mark like this");
            }
        }
        if (status > 4 || status < -1) {
            throw new RuntimeException("Status can be between [-1...4]");
        }
        order.setStatus(status);
    }

}
