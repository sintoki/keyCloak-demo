package com.example.demo.Controller;

import com.example.demo.Repository.MenuItemRepository;
import com.example.demo.Repository.MenuRepository;
import com.example.demo.Repository.*;
import com.example.demo.entity.Menu;
import com.example.demo.entity.MenuItem;
import com.example.demo.entity.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Restaurant")
public class RestaurantController {


    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @GetMapping
    @RequestMapping("/public/list")
    //Public API
    public List<Restaurant> getRestaurants() {
// here how come
     return restaurantRepository.findAll();
    }
    @GetMapping
    @RequestMapping("/public/menu/{restaurantId}")
    public Menu getMenu(@PathVariable long idd){

       Menu menu= menuRepository.findByRestaurantId(idd);
       menu.setMenuItems(menuItemRepository.findAllByMenuId(idd));
       return  menu;
    }

    @PostMapping("/create")
    //admin can access
  @PreAuthorize("hasRole('admin')")
    public Restaurant createRestaurant( @RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PostMapping
    @RequestMapping("/menu")
   @PreAuthorize("hasRole('manager')")
    public Menu createMenu( @RequestBody Menu menu) {
        menuRepository.save(menu);
        List<MenuItem> menuItems = menu.getMenuItems();
        if (menuItems != null) {
            for (MenuItem menuItem : menuItems) {
                menuItem.setMenuId(menu.getId());
                menuItemRepository.save(menuItem);
            }
        }


        return menu;
    }
@PutMapping("/menu/item/{itemId}/{price}")
@PreAuthorize("hasRole('owner')")
        public MenuItem updateMenuItemPrice(@PathVariable Long itemId, @PathVariable BigDecimal price)
        {
//
            Optional<MenuItem> menuItem= menuItemRepository.findById(itemId);
            menuItem.get().setPrice(
                    price
            );
            menuItemRepository.save(menuItem.get());
            return menuItem.get();

        }



    }





