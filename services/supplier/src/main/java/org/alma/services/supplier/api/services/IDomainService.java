package org.alma.services.supplier.api.services;

import org.alma.services.supplier.domain.entities.Product;

import java.util.List;

/**
 * Created by jeremy on 23/11/15.
 */
public interface IDomainService {
    public List<Product> GetProductsList();
    public int GetProductAvailability(String productReference);
    public Product GetProduct(String productReference);
    public boolean OrderProduct(String productReference, int quantity);
}
