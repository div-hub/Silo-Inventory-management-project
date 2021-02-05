package com.example.demo.Repository;

import com.example.demo.Models.InventoryPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface InvPostingRepository extends JpaRepository<InventoryPosting,Long> {


}
