package uz.bprodevelopment.logisticsapp.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomPage<T> {
    List<T> content;
    Boolean isFirst;
    Boolean isLast;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
