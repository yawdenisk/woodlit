package org.yawdenisk.woodlit.ProductFilter;

import org.yawdenisk.woodlit.Entites.Product;

import java.util.List;
import java.util.stream.Collectors;

public class PriceFilter implements Filter{
    private SearchParameters searchParameters;
    @Override
    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    @Override
    public boolean canFilter() {
        return searchParameters.getPriceFrom()>0 && searchParameters.getPriceTo()>0;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream()
                .filter(product -> product.getPrice() > searchParameters.getPriceFrom() && product.getPrice() < searchParameters.getPriceTo())
                .collect(Collectors.toList());
    }
}
