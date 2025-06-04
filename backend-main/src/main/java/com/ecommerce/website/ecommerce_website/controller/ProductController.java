package com.ecommerce.website.ecommerce_website.controller;


import com.ecommerce.website.ecommerce_website.model.Product;
import com.ecommerce.website.ecommerce_website.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    ProductService service;

    @Autowired
    ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProdByID(@PathVariable int id){

        if(service.getProdById(id) != null){
            return new ResponseEntity<>(service.getProdById(id), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> postProd(@RequestPart Product product,             //match the name coming for sure
                                      @RequestPart MultipartFile imageFile){
        try{
            service.addProduct(product, imageFile);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte []> getProdImage(@PathVariable int id){
        try{
            Product prod = service.getProdById(id);
            byte[] img = prod.getImgData();

            HttpHeaders headers = new HttpHeaders();                               //just to send image type
            headers.setContentType(MediaType.valueOf(prod.getImgType()));
            return new ResponseEntity<>(img, headers, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProd(@PathVariable int id,
                                       @RequestPart Product product,                //don't forget to match names from frontend
                                       @RequestPart MultipartFile imageFile ){      //juts name mistake and it won't find data
        try{
            service.updateProd(id, product, imageFile);
            return new ResponseEntity<>("producut updated successfully", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("not updated", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> delProd(@PathVariable int id){
        try{
            service.deleteProd(id);
            return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("products/search")
    public ResponseEntity<List<Product>> getProdByQuery(@RequestParam String keyword){
        System.out.println("request to search for:"+ keyword);
        try{
            return new ResponseEntity<>(service.getProdByQuery(keyword), HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
