package com.example.demo.Repository;

import java.util.List;

import com.example.demo.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long>{

    List<MenuItem> findAllByMenuId(Long id);

}
