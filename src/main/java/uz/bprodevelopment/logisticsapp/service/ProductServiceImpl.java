package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.ProductDetailDto;
import uz.bprodevelopment.logisticsapp.dto.ProductDto;
import uz.bprodevelopment.logisticsapp.entity.Product;
import uz.bprodevelopment.logisticsapp.entity.ProductDetail;
import uz.bprodevelopment.logisticsapp.repo.ProductDetailRepo;
import uz.bprodevelopment.logisticsapp.repo.ProductRepo;
import uz.bprodevelopment.logisticsapp.spec.ProductSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepo repo;
    private final ProductDetailRepo productDetailRepo;
    private final MessageSource messageSource;

    @Override
    public ProductDto getOne(Long id) {
        Product item = repo.getReferenceById(id);
        return item.toDto();
    }

    @Override
    public List<ProductDto> getListAll(
            String description,
            Double price,
            Integer hasDelivery,
            Integer hasNds,
            Long categoryId,
            Long supplierId,
            String sort,
            Boolean descending
    ) {
        ProductSpec spec1 = new ProductSpec(new SearchCriteria("id", ">", 0));
        Specification<Product> spec = Specification.where(spec1);


        if (description != null) spec = spec.and(new ProductSpec(new SearchCriteria("description", ":", description)));

        if (price != null) spec = spec.and(new ProductSpec(new SearchCriteria("price", "=", price)));

        if (hasDelivery != null) spec = spec.and(new ProductSpec(new SearchCriteria("hasDelivery", "=", hasDelivery)));

        if (hasNds != null) spec = spec.and(new ProductSpec(new SearchCriteria("hasNds", "=", hasNds)));

        if (categoryId != null) spec = spec.and(new ProductSpec(new SearchCriteria("categoryId", ":", categoryId)));

        if (supplierId != null) spec = spec.and(new ProductSpec(new SearchCriteria("supplierId", ":", supplierId)));

        Sort sorting = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC, sort);
        List<Product> products = repo.findAll(spec, sorting);

        List<ProductDto> response = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = product.toDto();
            List<ProductDetail> productDetails = productDetailRepo.findAllByProductId(productDto.getId());
            for (ProductDetail productDetail: productDetails) {
                ProductDetailDto productDetailDto = productDetail.toDto();
                productDto.getDetails().add(productDetailDto);
            }
            response.add(productDto);
        }

        return response;
    }

    @Override
    public CustomPage<ProductDto> getList(
            Integer page,
            Integer size,
            String description,
            Double price,
            Integer hasDelivery,
            Integer hasNds,
            Long categoryId,
            Long supplierId,
            String sort,
            Boolean descending
    ) {

        ProductSpec spec1 = new ProductSpec(new SearchCriteria("id", ">", 0));
        Specification<Product> spec = Specification.where(spec1);

        if (description != null) spec = spec.and(new ProductSpec(new SearchCriteria("description", ":", description)));

        if (price != null) spec = spec.and(new ProductSpec(new SearchCriteria("price", "=", price)));

        if (hasDelivery != null) spec = spec.and(new ProductSpec(new SearchCriteria("hasDelivery", "=", hasDelivery)));

        if (hasNds != null) spec = spec.and(new ProductSpec(new SearchCriteria("hasNds", "=", hasNds)));

        if (categoryId != null) spec = spec.and(new ProductSpec(new SearchCriteria("categoryId", ":", categoryId)));

        if (supplierId != null) spec = spec.and(new ProductSpec(new SearchCriteria("supplierId", ":", supplierId)));

        Sort sorting = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC, sort);
        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<Product> responsePage = repo.findAll(spec, pageable);
        List<ProductDto> dtos = new ArrayList<>();

        responsePage.getContent().forEach(item -> {
            ProductDto dto = item.toDto();
            List<ProductDetail> productDetails = productDetailRepo.findAllByProductId(dto.getId());
            productDetails.forEach(productDetail -> {
                dto.getDetails().add(productDetail.toDto());
            });
            dtos.add(dto);
        });


        return new CustomPage<>(
                dtos,
                responsePage.isFirst(),
                responsePage.isLast(),
                responsePage.getNumber(),
                responsePage.getTotalPages(),
                responsePage.getTotalElements()
        );
    }

    @Override
    @Transactional
    public void save(ProductDto item) {
        if(item.getPrice() == null) {
            String lang = BaseAppUtils.getCurrentLanguage();
            Locale locale = new Locale(lang);
            String message = messageSource.getMessage("enter_price", null, locale);
            throw new RuntimeException(message);
        }
        if(item.getCategoryId() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_category_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if(item.getSupplierId() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_supplier_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if(item.getId() != null) {
            throw new RuntimeException(messageSource.getMessage("sending_id_is_not_acceptable", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }

        Product product = item.toEntity();
        product = repo.save(product);

        for (ProductDetailDto detail: item.getDetails()) {
            ProductDetail productDetail = detail.toEntity();
            productDetail.setProduct(product);
            productDetailRepo.save(productDetail);
        }
    }

    @Override
    @Transactional
    public void update(ProductDto item) {
        if(item.getPrice() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_price", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if(item.getCategoryId() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_category_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if(item.getSupplierId() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_supplier_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if (!repo.existsById(item.getId())) {
            throw new RuntimeException(messageSource.getMessage("enter_valid_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }

        Product product = item.toEntity();
        repo.save(product);

        productDetailRepo.deleteByProductId(item.getId());

        for (ProductDetailDto detail: item.getDetails()) {
            ProductDetail productDetail = detail.toEntity();
            productDetailRepo.save(productDetail);
        }
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
