package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.logisticsapp.dto.ProductDetailDto;
import uz.bprodevelopment.logisticsapp.dto.ProductDto;
import uz.bprodevelopment.logisticsapp.entity.Product;
import uz.bprodevelopment.logisticsapp.entity.ProductDetail;
import uz.bprodevelopment.logisticsapp.repo.ProductDetailRepo;
import uz.bprodevelopment.logisticsapp.repo.ProductRepo;
import uz.bprodevelopment.logisticsapp.spec.ProductSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepo repo;
    private final ProductDetailRepo productDetailRepo;

    @Override
    public Product getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<ProductDto> getListAll(
            String name,
            Double price,
            Integer hasDelivery,
            Integer hasNds,
            Long categoryId,
            Long companyId,
            Long supplierId,
            String sort
    ) {
        ProductSpec spec1 = new ProductSpec(new SearchCriteria("id", ">", 0));
        Specification<Product> spec = Specification.where(spec1);


        if (name != null) spec = spec.and(new ProductSpec(new SearchCriteria("name", ":", name)));

        if (price != null) spec = spec.and(new ProductSpec(new SearchCriteria("price", "=", price)));

        if (hasDelivery != null) spec = spec.and(new ProductSpec(new SearchCriteria("hasDelivery", "=", hasDelivery)));

        if (hasNds != null) spec = spec.and(new ProductSpec(new SearchCriteria("hasNds", "=", hasNds)));

        if (categoryId != null) spec = spec.and(new ProductSpec(new SearchCriteria("categoryId", ":", categoryId)));

        if (companyId != null) spec = spec.and(new ProductSpec(new SearchCriteria("companyId", ":", companyId)));

        if (supplierId != null) spec = spec.and(new ProductSpec(new SearchCriteria("supplierId", ":", supplierId)));

        List<Product> products = repo.findAll(spec, Sort.by(sort).descending());
        List<ProductDto> response = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = product.toDto();
            List<ProductDetail> productDetails = productDetailRepo.findByProductId(productDto.getId());
            for (ProductDetail productDetail: productDetails) {
                ProductDetailDto productDetailDto = productDetail.toDto();
                productDto.getProductDetails().add(productDetailDto);
            }
            response.add(productDto);

        }

        return response;
    }

    @Override
    public Page<ProductDto> getList(
            Integer page,
            Integer size,
            String name,
            Double price,
            Integer hasDelivery,
            Integer hasNds,
            Long categoryId,
            Long companyId,
            Long supplierId,
            String sort
    ) {

        ProductSpec spec1 = new ProductSpec(new SearchCriteria("id", ">", 0));
        Specification<Product> spec = Specification.where(spec1);

        if (name != null) spec = spec.and(new ProductSpec(new SearchCriteria("name", ":", name)));

        if (price != null) spec = spec.and(new ProductSpec(new SearchCriteria("price", "=", price)));

        if (hasDelivery != null) spec = spec.and(new ProductSpec(new SearchCriteria("hasDelivery", "=", hasDelivery)));

        if (hasNds != null) spec = spec.and(new ProductSpec(new SearchCriteria("hasNds", "=", hasNds)));

        if (categoryId != null) spec = spec.and(new ProductSpec(new SearchCriteria("categoryId", ":", categoryId)));

        if (companyId != null) spec = spec.and(new ProductSpec(new SearchCriteria("companyId", ":", companyId)));

        if (supplierId != null) spec = spec.and(new ProductSpec(new SearchCriteria("supplierId", ":", supplierId)));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        Page<Product> products = repo.findAll(spec, pageable);

        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products.getContent()) {
            ProductDto productDto = product.toDto();
            List<ProductDetail> productDetails = productDetailRepo.findByProductId(productDto.getId());
            for (ProductDetail productDetail: productDetails) {
                ProductDetailDto productDetailDto = productDetail.toDto();
                productDto.getProductDetails().add(productDetailDto);
            }

            productDtos.add(productDto);
        }
        return new PageImpl<>(productDtos);
    }

    @Override
    @Transactional
    public void save(ProductDto item) {
        if(item.getName() == null) {
            throw new RuntimeException("Nomini kiriting");
        }
        if(item.getPrice() == null) {
            throw new RuntimeException("Narxini kiriting");
        }
        Product product = item.toEntity();
        repo.save(product);

        for (ProductDetailDto detail: item.getProductDetails()) {
            ProductDetail productDetail = detail.toEntity();
            productDetailRepo.save(productDetail);
        }
    }

    @Override
    @Transactional
    public void update(ProductDto item) {
        if(item.getName() == null) {
            throw new RuntimeException("Nomini kiriting");
        }
        if(item.getPrice() == null) {
            throw new RuntimeException("Narxini kiriting");
        }
        if (item.getId() == null) {
            throw new RuntimeException("ID kiritilmagan");
        }
        Product product = item.toEntity();
        repo.save(product);

        productDetailRepo.deleteByProductId(item.getId());

        for (ProductDetailDto detail: item.getProductDetails()) {
            ProductDetail productDetail = detail.toEntity();
            productDetailRepo.save(productDetail);
        }
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
