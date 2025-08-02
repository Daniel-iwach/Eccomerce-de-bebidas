package com.example.eccomerce.controller;

import com.example.eccomerce.service.interfaces.IItemCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ItemCart")
@RequiredArgsConstructor
public class ItemCarController {
    private final IItemCartService itemCartService;
}
