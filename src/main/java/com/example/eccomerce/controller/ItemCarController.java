package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.request.RequestAddItemCartDto;
import com.example.eccomerce.model.dtos.request.RequestUpdateItemQuantityDto;
import com.example.eccomerce.model.dtos.response.ItemWithProductInfoDto;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
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
    public ResponseEntity<ResponseItemCartDto> addItemCart(@RequestBody @Valid RequestAddItemCartDto createItemCartDto) {
        return new ResponseEntity<>(itemCartService.addItemCart(createItemCartDto), HttpStatus.OK);
    }

    //INCREMENTA LA CANTIDAD DEL ITEM
    @PostMapping("/inc-item")
    public ResponseEntity<ResponseItemCartDto> incrementQuantity(@RequestBody @Valid RequestUpdateItemQuantityDto updateItemQuantityDto) {
        return new ResponseEntity<>(itemCartService.updateItemQuantity(updateItemQuantityDto,false), HttpStatus.OK);
    }

    //DECREMENTA LA CANTIDAD DEL ITEM
    @PostMapping("/dec-item")
    public ResponseEntity<ResponseItemCartDto> decrementQuantity(@RequestBody @Valid RequestUpdateItemQuantityDto updateItemQuantityDto) {
        return new ResponseEntity<>(itemCartService.updateItemQuantity(updateItemQuantityDto,true), HttpStatus.OK);
    }

    //LISTA TODOS LOS ITEM
    @GetMapping("/listAll")
    public ResponseEntity<List<ResponseItemCartDto>>getAll(){
        return new ResponseEntity<>(itemCartService.getAll(),HttpStatus.OK);
    }

    //LISTA ITEMS POR CARRITO
    @GetMapping("/list-by-cart-id/{cartId}")
    public ResponseEntity<List<ResponseItemCartDto>>getByCartId(@PathVariable String cartId){
        return new ResponseEntity<>(itemCartService.getByCartId(cartId),HttpStatus.OK);
    }


    @GetMapping("/list-item-with-product/{cartId}")
    public ResponseEntity<List<ItemWithProductInfoDto>>getItemWithProductByCartId(@PathVariable String cartId){
        return new ResponseEntity<>(itemCartService.getItemWithProductByCartId(cartId),HttpStatus.OK);
    }

    //OBTIENE EL TOTTAL DE ITEMS POR CARRITO
    @GetMapping("/get-total-quantity/{cartId}")
    public ResponseEntity<Integer>getTotalItemsByCartId(@PathVariable String cartId){
        return new ResponseEntity<>(itemCartService.getTotalItemsByCartId(cartId),HttpStatus.OK);
    }

    //ELIMINA UN ITEM
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<String>deleteItem(@PathVariable String itemId){
        return new ResponseEntity<>(itemCartService.deleteItemCart(itemId),HttpStatus.OK);
    }
}
