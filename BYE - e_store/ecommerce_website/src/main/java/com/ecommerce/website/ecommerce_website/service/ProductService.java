package com.ecommerce.website.ecommerce_website.service;

import com.ecommerce.website.ecommerce_website.model.Product;
import com.ecommerce.website.ecommerce_website.repo.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    ProductRepo repo;

    ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProdById(int id) {
        return repo.findById(id).orElse(null);
    }


    public void addProduct(Product product, MultipartFile image) throws IOException {
//        repo.save(product);           if u do it like that it'll send product without image data, name and type so =>

        product.setImgName(image.getOriginalFilename());                    //don't be suprprised u didn't created those method
        product.setImgType(image.getContentType());             //they are created for multipartfile type
        product.setImgData(image.getBytes());

        repo.save(product);                 // now it will save every entity of product
    }


    public void updateProd(int id, Product prod, MultipartFile img) throws Exception {

        if(repo.existsById(id)){
            prod.setImgName(img.getOriginalFilename());
            prod.setImgType(img.getContentType());
            prod.setImgData(img.getBytes());
            repo.save(prod);
        }else throw new Exception("product not found") ;
    }

    public void deleteProd(int id) throws Exception {
        if(repo.existsById(id)){
            repo.deleteById(id);
        }else throw new Exception("product not found of id: " + id );
    }

    public List<Product> getProdByQuery(String name) {
        return repo.findByQuery(name);
    }
}
