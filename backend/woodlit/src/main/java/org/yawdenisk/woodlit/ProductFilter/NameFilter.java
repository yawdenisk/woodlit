package org.yawdenisk.woodlit.ProductFilter;

import org.yawdenisk.woodlit.Entites.Product;

import java.util.List;
import java.util.stream.Collectors;

public class NameFilter implements Filter{
    private SearchParameters searchParameters;
    @Override
    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    @Override
    public boolean canFilter() {
        return searchParameters.getName()!= null;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream()
                .filter(product -> product.getName().contains(searchParameters.getName()))
                .collect(Collectors.toList());
    }
}
