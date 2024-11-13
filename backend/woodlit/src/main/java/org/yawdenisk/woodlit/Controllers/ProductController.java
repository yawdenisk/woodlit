package org.yawdenisk.woodlit.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yawdenisk.woodlit.Entites.Product;
import org.yawdenisk.woodlit.Services.ProductService;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadProduct(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("price") float price,
                                        @RequestParam("image") MultipartFile image){
        try {
            if (image.isEmpty()) {
                return ResponseEntity.badRequest().body("Image file is empty");
            }
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setImage(image.getBytes());
            System.out.println(product.toString());
            System.out.println(image.getOriginalFilename());
            System.out.println(image.getSize());
            productService.uploadProduct(product);
            return ResponseEntity.ok("Product uploaded successfully");
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error uploading product:" + e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try{
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
                                           @RequestParam(required = false, name = "image") MultipartFile image){
        try{
            Optional<Product> p = productService.getProductById(id);
            Product result = p.get();
            if(name != null)result.setName(name);
            if(description != null)result.setDescription(description);
            if(price != null)result.setPrice(price);
            if(image != null)result.setImage(image.getBytes());
            productService.uploadProduct(result);
            return ResponseEntity.ok("Product updated sucessfully");
        }catch (Exception e){
           return ResponseEntity.status(500).body("Error updating product" + e.getMessage());
        }
    }
    @GetMapping("getImage/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id){
        Optional<Product> product = productService.getProductById(id);
        Product p = product.get();
        byte[] image = p.getImage();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
    @GetMapping("/get/{id}")
    public Product getProduct(@PathVariable Long id){
        Optional<Product> product = productService.getProductById(id);
        Product result = product.get();
        return result;
    }
}
