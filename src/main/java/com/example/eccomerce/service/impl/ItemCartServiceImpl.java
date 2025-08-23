package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.ItemCartMapper;
import com.example.eccomerce.model.ItemCart;
import com.example.eccomerce.model.dtos.request.RequestAddItemCartDto;
import com.example.eccomerce.model.dtos.request.RequestSetQuantityItemDto;
import com.example.eccomerce.model.dtos.request.RequestUpdateItemQuantityDto;
import com.example.eccomerce.model.dtos.response.ItemWithProductInfoDto;
import com.example.eccomerce.model.dtos.response.ResponseItemCartDto;
import com.example.eccomerce.model.dtos.response.ResponseProductDto;
import com.example.eccomerce.repository.ItemCartRepository;
import com.example.eccomerce.service.interfaces.ICartService;
import com.example.eccomerce.service.interfaces.IItemCartService;
import com.example.eccomerce.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemCartServiceImpl implements IItemCartService {
    private final ItemCartRepository itemCartRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final ItemCartMapper itemCartMapper;

    @Override
    public ResponseItemCartDto addItemCart(RequestAddItemCartDto ItemCartDto) {
        ItemCart itemCart;
        Optional<ItemCart>optionalItemCart;
        optionalItemCart=getItemByCartIdAndProductId(ItemCartDto.cartId(),ItemCartDto.productId());

        //PREGUNTA SI EXISTE YA UN ITEM CON ESE PRODUCTO Y CARRITO
        if (optionalItemCart.isPresent()){
            itemCart=optionalItemCart.get();    // OBTIENE EL ITEM EXISTENTE
            itemCart.setQuantity(itemCart.getQuantity()+ItemCartDto.quantity());  //ACTUALIZA LA CANTIDAD
        }else {
            itemCart=itemCartMapper.createItemCartDtoToItemCart(ItemCartDto); //CREA EL ITEM CON EL REQUEST
        }

        //CALCULA SUBTOTAL Y CREA/ACTUALIZA EL ITEM
        itemCart=calculateAndSetSubTotal(itemCart);
        itemCart=itemCartRepository.save(itemCart);

        //ACTUALIZA EL TOTAL DEL CARRITO
        updateTotalCart(itemCart.getCartId().toHexString());
        //ACTUALIZA LA LISTA DEL CARRITO SI TODAVIA NO EXISTE
        if (!optionalItemCart.isPresent()){
            updateListOfCart(itemCart.getCartId().toHexString(), itemCart.getId(), false);
        }

        //RETORNA EL ITEM MAPEADO A DTO
        return itemCartMapper.ItemCartToItemCartDto(itemCart);
    }

    @Override
    public ResponseItemCartDto updateItemQuantity(RequestUpdateItemQuantityDto incrItemCartDto, boolean decrement) {
        ItemCart itemCart=itemCartRepository.findById(incrItemCartDto.itemCartId())
                .orElseThrow(()->new NoSuchElementException
                        ("item cart with id: " + incrItemCartDto.itemCartId() + " not found"));

        if (decrement){
            itemCart.setQuantity(itemCart.getQuantity()-1);
        }else {
            itemCart.setQuantity((itemCart.getQuantity())+1);
        }

        //CALCULA SUBTOTAL Y CREA/ACTUALIZA EL ITEM
        itemCart=calculateAndSetSubTotal(itemCart);
        itemCart=itemCartRepository.save(itemCart);
        //ACTUALIZA EL TOTAL DEL CARRITO
        updateTotalCart(itemCart.getCartId().toHexString());

        //RETORNA EL ITEM MAPEADO A DTO
        return itemCartMapper.ItemCartToItemCartDto(itemCart);
    }

    @Override
    public ResponseItemCartDto setItemQuantity(RequestSetQuantityItemDto quantityItemDto) {
        ItemCart itemCart=itemCartRepository.findById(quantityItemDto.itemId())
                .orElseThrow(()->new NoSuchElementException
                        ("item cart with id: " + quantityItemDto.itemId() + " not found"));
        itemCart.setQuantity(quantityItemDto.quantity());

        //CALCULA SUBTOTAL Y ACTUALIZA EL ITEM
        itemCart=calculateAndSetSubTotal(itemCart);
        itemCart=itemCartRepository.save(itemCart);

        //ACTUALIZA EL TOTAL DEL CARRITO
        updateTotalCart(itemCart.getCartId().toHexString());

        //RETORNA EL ITEM MAPEADO A DTO
        return itemCartMapper.ItemCartToItemCartDto(itemCart);
    }

    @Override
    public List<ResponseItemCartDto> getAll() {
        List<ItemCart> itemCartList=itemCartRepository.findAll();
        return itemCartMapper.ItemCartListToItemCartDtoList(itemCartList);
    }

    @Override
    public ResponseItemCartDto getById(String itemId) {
        ItemCart itemCart=itemCartRepository.findById(itemId)
                .orElseThrow(()->new NoSuchElementException("Items by Id: "+ itemId +" not found"));
        return itemCartMapper.ItemCartToItemCartDto(itemCart);
    }

    @Override
    public List<ResponseItemCartDto> getByCartId(String cartId) {
        List<ItemCart>itemCartList=itemCartRepository.findByCartId(new ObjectId(cartId))
                .orElseThrow(()->new NoSuchElementException("Items by cartId: "+ cartId +" not found"));
        return itemCartMapper.ItemCartListToItemCartDtoList(itemCartList);
    }

    @Override
    public List<ItemWithProductInfoDto>getItemWithProductByCartId(String cartId){
        return itemCartRepository.findItemsWithProductInfoByCartId(new ObjectId(cartId));
    }

    @Override
    public Integer getTotalItemsByCartId(String cartId) {
        return itemCartRepository.sumQuantityByCartId(new ObjectId(cartId))
                .map(ItemCartRepository.TotalQuantityResult::getTotalQuantity)
                .orElse(0);
    }

    @Override
    public String deleteItemCart(String itemId) {
        Optional<ItemCart>optionalItemCart=itemCartRepository.findById(itemId);
        if (optionalItemCart.isPresent()){
            ItemCart itemCart=optionalItemCart.get();
            updateListOfCart(itemCart.getCartId().toHexString(),itemCart.getId(),true);
            itemCartRepository.deleteById(itemId);
            updateTotalCart(itemCart.getCartId().toHexString());
            return "Producto eliminado con exito";
        }else {
            throw new NoSuchElementException("item with id: "+ itemId +" not found");
        }
    }

    @Override
    public String deleteAllItemsByCartId(String cartId) {
        Long deleted= itemCartRepository.deleteAllByCartId(new ObjectId(cartId));
        if (deleted>0){
            cartService.resetCart(cartId);
            return "items borrados";
        }else {
            return "error borrando los items";
        }
    }

    private Optional<ItemCart> getItemByCartIdAndProductId(String cartId, String productId){
        return itemCartRepository.findByCartIdAndProductId(new ObjectId(cartId), new ObjectId(productId));
    }

    private ItemCart calculateAndSetSubTotal(ItemCart itemCart){
        ResponseProductDto productDto=productService.findById(itemCart.getProductId().toHexString());
        itemCart.setSubTotal(productDto.price().multiply(BigDecimal.valueOf(itemCart.getQuantity())));
        return itemCart;
    }

    private void updateTotalCart(String cartId){
        List<ResponseItemCartDto>itemCartDtoList=getByCartId(cartId);
        int total=itemCartDtoList.stream().mapToInt(ResponseItemCartDto::subTotal).sum();
        cartService.updateTotalPrice(cartId, total);
    }

    //AÑADE O BORRA DE LA LISTA
    private void updateListOfCart(String cartId,String itemId, boolean toDelete){
        cartService.updateItemList(cartId, itemId, toDelete);
    }

}
