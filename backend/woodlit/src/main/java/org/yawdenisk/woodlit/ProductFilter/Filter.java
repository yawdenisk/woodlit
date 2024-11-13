package org.yawdenisk.woodlit.ProductFilter;

import org.yawdenisk.woodlit.Entites.Product;

import java.util.List;

public interface Filter {
    void setSearchParameters(SearchParameters searchParameters);
    boolean canFilter();
    List<Product> filter(List<Product> products);
}
