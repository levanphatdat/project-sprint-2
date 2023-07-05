package com.example.be.controller.product;

import com.example.be.DTO.productDTO.ProductDTO;
import com.example.be.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductRestController {
    StringBuilder stringBuilder = new StringBuilder();
    @Autowired
    private IProductService iProductService;
    @GetMapping("")
    public ResponseEntity<Page<ProductDTO>> findProductByName (
            @RequestParam(required = false, defaultValue = "") String name,
            @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC,size = 8) Pageable pageable ) {
        Page<ProductDTO> productDTOS = iProductService.findByName(pageable, name);
        if (productDTOS.isEmpty()) {
            return new ResponseEntity<>(productDTOS, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ProductDTO>> findAll (
            @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC,size = 2) Pageable pageable ) {
        Page<ProductDTO> productDTOS = iProductService.findAll(pageable);
        if (productDTOS.isEmpty()) {
            return new ResponseEntity<>(productDTOS, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
    @GetMapping("/list4")
    public ResponseEntity<Page<ProductDTO>> findAllProduct (
            @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC,size = 4) Pageable pageable ) {
        Page<ProductDTO> productDTOS = iProductService.findAll(pageable);
        if (productDTOS.isEmpty()) {
            return new ResponseEntity<>(productDTOS, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ProductDTO> findById (@PathVariable Integer id) {
        ProductDTO productDTO = iProductService.findById(id);
        if (productDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
