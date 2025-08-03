package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.request.RequestCreateItemCartDto;
import com.example.eccomerce.model.dtos.request.RequestProductCreateDto;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
import com.example.eccomerce.model.dtos.response.ResponseProductDto;
import com.example.eccomerce.service.interfaces.IItemCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-cart")
@RequiredArgsConstructor
public class ItemCarController {
    private final IItemCartService itemCartService;

    //METODO PARA EL BOTON DE AÑADIR CANTIDADES
    @PostMapping("/add-item")
    public ResponseEntity<ResponseItemCartDto> addItemCart(@RequestBody @Valid RequestCreateItemCartDto createItemCartDto) {
        return new ResponseEntity<>(itemCartService.addItemCart(createItemCartDto), HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<ResponseItemCartDto>>getAll(){
        return new ResponseEntity<>(itemCartService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/list-by-cart-id/{cartId}")
    public ResponseEntity<List<ResponseItemCartDto>>getByCartId(@PathVariable String cartId){
        return new ResponseEntity<>(itemCartService.getByCartId(cartId),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<String>deleteItem(@PathVariable String itemId){
        return new ResponseEntity<>(itemCartService.deleteItemCart(itemId),HttpStatus.OK);
    }
}
