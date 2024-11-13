package org.yawdenisk.woodlit.ProductFilter;

import org.yawdenisk.woodlit.Entites.Product;

import java.util.List;

public class GeneralFilter {
    private List<Filter> filters;
    private List<Product> products;

    public void setFilters(Filter filter) {
        filters.add(filter);
    }
    public List<Product> filter(List<Product> products){
        for (Filter filter : filters){
            filter.filter(products);
        }
        return products;
    }
}
