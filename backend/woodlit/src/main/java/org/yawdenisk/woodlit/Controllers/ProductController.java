package org.yawdenisk.woodlit.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yawdenisk.woodlit.Configuration.S3Configuration;
import org.yawdenisk.woodlit.Entites.Product;
import org.yawdenisk.woodlit.ProductFilter.*;
import org.yawdenisk.woodlit.Services.ProductService;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private S3Client s3Client;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadProduct(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("price") float price,
                                           @RequestParam("features") String features,
                                        @RequestParam("image") MultipartFile image){
        try {
            if (image.isEmpty()) {
                return ResponseEntity.badRequest().body("Image file is empty");
            }
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            s3Client.putObject(request -> request
                            .bucket("woodlit")
                            .key(fileName),
                            RequestBody.fromBytes(image.getBytes()));
            String imageUrl = "https://woodlit.s3.amazonaws.com/" + fileName;
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setImage(imageUrl);
            product.setFeatures(features);
            productService.uploadProduct(product);
            return ResponseEntity.ok("Product uploaded successfully");
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error uploading product:" + e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try{
            Optional<Product> product = productService.getProductById(id);
            Product p = product.get();
            s3Client.deleteObject(request -> request.bucket("woodlit").key(p.getImage()));
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted sucessfully");
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error deleting product" + e.getMessage());
        }
    }
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestParam(required = false, name = "name") String name,
                                           @RequestParam(required = false, name = "description") String description,
                                           @RequestParam(required = false, name = "price") Float price,
                                           @RequestParam(required = false, name = "features") String features,
                                           @RequestParam(required = false, name = "image") MultipartFile image){
        try{
            Optional<Product> p = productService.getProductById(id);
            Product result = p.get();
            if(name != null)result.setName(name);
            if(description != null)result.setDescription(description);
            if(price != null)result.setPrice(price);
            if(features != null)result.setFeatures(features);
            if(image != null){
                s3Client.deleteObject(request -> request.bucket("woodlit").key(result.getImage()));
                if (image.isEmpty()) {
                    return ResponseEntity.badRequest().body("Image file is empty");
                }
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                s3Client.putObject(request -> request
                                .bucket("woodlit")
                                .key(fileName),
                        RequestBody.fromBytes(image.getBytes()));
                String imageUrl = "https://woodlit.s3.amazonaws.com/" + fileName;
                result.setImage(imageUrl);
            }
            productService.uploadProduct(result);
            return ResponseEntity.ok("Product updated sucessfully");
        }catch (Exception e){
           return ResponseEntity.status(500).body("Error updating product" + e.getMessage());
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        try{
            Optional<Product> product = productService.getProductById(id);
            return ResponseEntity.ok(product.get());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    @PostMapping("/getfilteredproducts")
    public List<Product> getFilteredProducts(@RequestParam String name,
                                             @RequestParam float priceFrom,
                                             @RequestParam float priceTo){
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.setName(name);
        searchParameters.setPriceFrom(priceFrom);
        searchParameters.setPriceTo(priceTo);
        Filter nameFilter = new NameFilter();
        nameFilter.setSearchParameters(searchParameters);
        Filter priceFilter = new PriceFilter();
        priceFilter.setSearchParameters(searchParameters);
        List<Product> products = productService.getAllProducts();
        GeneralFilter generalFilter = new GeneralFilter();
        generalFilter.setFilters(nameFilter);
        generalFilter.setFilters(priceFilter);
        return generalFilter.filter(products);
    }
    @GetMapping("/getAll")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
}
