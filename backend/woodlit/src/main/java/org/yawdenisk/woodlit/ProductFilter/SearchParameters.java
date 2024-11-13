package org.yawdenisk.woodlit.ProductFilter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParameters {
    private float priceFrom;
    private float priceTo;
    private String name;
}
