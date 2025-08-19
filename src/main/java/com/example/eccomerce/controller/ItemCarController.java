package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.request.RequestAddItemCartDto;
import com.example.eccomerce.model.dtos.request.RequestSetQuantityItemDto;
import com.example.eccomerce.model.dtos.request.RequestUpdateItemQuantityDto;
import com.example.eccomerce.model.dtos.response.ItemWithProductInfoDto;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
import com.example.eccomerce.service.interfaces.IItemCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-cart")
@RequiredArgsConstructor
public class ItemCarController {
    private final IItemCartService itemCartService;

    //METODO PARA EL BOTON DE AÑADIR CANTIDADES
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/add-item")
    public ResponseEntity<ResponseItemCartDto> addItemCart(@RequestBody @Valid RequestAddItemCartDto createItemCartDto) {
        return new ResponseEntity<>(itemCartService.addItemCart(createItemCartDto), HttpStatus.OK);
    }

    //INCREMENTA LA CANTIDAD DEL ITEM
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/inc-item")
    public ResponseEntity<ResponseItemCartDto> incrementQuantity(@RequestBody @Valid RequestUpdateItemQuantityDto updateItemQuantityDto) {
        return new ResponseEntity<>(itemCartService.updateItemQuantity(updateItemQuantityDto,false), HttpStatus.OK);
    }

    //DECREMENTA LA CANTIDAD DEL ITEM
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/dec-item")
    public ResponseEntity<ResponseItemCartDto> decrementQuantity(@RequestBody @Valid RequestUpdateItemQuantityDto updateItemQuantityDto) {
        return new ResponseEntity<>(itemCartService.updateItemQuantity(updateItemQuantityDto,true), HttpStatus.OK);
    }

    //SETEAR CANTIDAD DEL ITEM
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<ResponseItemCartDto> updateQuantity(@RequestBody @Valid RequestSetQuantityItemDto quantityItemDto){
        return new ResponseEntity<>(itemCartService.setItemQuantity(quantityItemDto),HttpStatus.OK);
    }

    //LISTA TODOS LOS ITEM
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/listAll")
    public ResponseEntity<List<ResponseItemCartDto>>getAll(){
        return new ResponseEntity<>(itemCartService.getAll(),HttpStatus.OK);
    }

    //OBTIENE ITEM POR ID
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-by-id/{itemId}")
    public ResponseEntity<ResponseItemCartDto>getById(@PathVariable String itemId){
        return new ResponseEntity<>(itemCartService.getById(itemId),HttpStatus.OK);
    }

    //LISTA ITEMS POR CARRITO
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/list-by-cart-id/{cartId}")
    public ResponseEntity<List<ResponseItemCartDto>>getByCartId(@PathVariable String cartId){
        return new ResponseEntity<>(itemCartService.getByCartId(cartId),HttpStatus.OK);
    }

    //OBTIENE TODOS LOS ITEM CON DETALLES DEL PRODUCTO
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/list-item-with-product/{cartId}")
    public ResponseEntity<List<ItemWithProductInfoDto>>getItemWithProductByCartId(@PathVariable String cartId){
        return new ResponseEntity<>(itemCartService.getItemWithProductByCartId(cartId),HttpStatus.OK);
    }

    //OBTIENE EL TOTAL DE ITEMS POR CARRITO
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-total-quantity/{cartId}")
    public ResponseEntity<Integer>getTotalItemsByCartId(@PathVariable String cartId){
        return new ResponseEntity<>(itemCartService.getTotalItemsByCartId(cartId),HttpStatus.OK);
    }

    //ELIMINA UN ITEM
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<String>deleteItem(@PathVariable String itemId){
        return new ResponseEntity<>(itemCartService.deleteItemCart(itemId),HttpStatus.OK);
    }

    //ELIMINA TODOS LOS ITEM DEL CARRITO
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/clean-cart/{cartId}")
    public ResponseEntity<String>cleanCart(@PathVariable String cartId){
        return new ResponseEntity<>(itemCartService.deleteAllItemsByCartId(cartId),HttpStatus.OK);
    }
}
