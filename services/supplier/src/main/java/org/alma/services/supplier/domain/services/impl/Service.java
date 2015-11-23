package org.alma.services.supplier.domain.services.impl;

import org.alma.services.supplier.domain.entities.Product;
import org.alma.services.supplier.api.services.IDomainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jeremy on 23/11/15.
 */
public class Service implements IDomainService {

    private Map<String, Product> products;
    private Map<String, Integer> stocks;

    public Service(){
        this.products = new HashMap<>();
        this.stocks = new HashMap<>();

        // http://www.walmart.com/ip/Pilot-G2-Retractable-Gel-Ink-Roller-Ball-Pen/15064190
        // http://www.walmart.com/ip/Dixon-Colored-Woodcase-Pencils-3.3-mm-36-Assorted-Colors-set/21424948
        // http://www.walmart.com/ip/Paper-Mate-Mirado-Woodcase-Pencils-2-Lead-72-Count/14150582
        // http://www.walmart.com/ip/30-K-3rd-Grade-Starter-School-Supplies-Bundle/13432790

        Product ballPen = new Product("RBP5", "Roller Ball Pen", "Write clear, colorful documents with this pen", 12);
        Product coloredPencils = new Product("CP2", "Colored Pencils", "Give your next art project more vibrant colors", 7);
        Product woodPencils = new Product("WP8", "Woodcase Pencils", "They sharpen to a fine exact point for smooth writing", 8);
        Product markers = new Product("PM6", "Permanent Markers", "Make a bold statement with a rainbow of colors", 5);

        this.stockNewProduct(ballPen, 5);
        this.stockNewProduct(coloredPencils, 2);
        this.stockNewProduct(woodPencils, 8);
        this.stockNewProduct(markers, 6);
    }

    public List<Product> GetProductsList(){
        return new ArrayList<Product>(this.products.values());
    }

    public int GetProductAvailability(String productReference){
        return this.stocks.get(productReference);
    }

    public Product GetProduct(String productReference){
        return this.products.get(productReference);
    }

    public boolean OrderProduct(String productReference, int quantity){
        int remainingStock = this.stocks.get(productReference) - quantity;

        if(remainingStock >= 0){
            this.stocks.put(productReference, remainingStock);
            return true;
        }

        return false;
    }

    private void stockNewProduct(Product product, int quantity){
        this.products.put(product.getReference(), product);
        this.stocks.put(product.getReference(), quantity);
    }
}
