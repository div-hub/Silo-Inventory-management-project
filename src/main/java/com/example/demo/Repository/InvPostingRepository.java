package com.example.demo.Repository;

import com.example.demo.Models.InventoryPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface InvPostingRepository extends JpaRepository<InventoryPosting,Long> {

   //Inserting the data into the inventory_posting table
    @Modifying
    @Transactional
    @Query(value="insert into inventory_posting(silonum,materialid,postingtype,quantity,uom,batchid) values(:silonum,:materialid,:postingtype,:quantity,:uom,:batchid)", nativeQuery = true)
    void saveGoodsReceipt
            (@Param("silonum") Long silonum,
             @Param("materialid") String materialid,
             @Param("postingtype") String postingtype,
             @Param("quantity") Long quantity,
             @Param("uom") String uom,
             @Param("batchid") String batchid);

    //Inserting the data into the silo_tracker table
    @Modifying
    @Transactional
    @Query(value="insert into inventory_posting(silonum,materialid,postingtype,batchid,quantity,uom) values(:silonum,:materialid,:postingtype,:batchid,:quantity,:uom)", nativeQuery = true)
    void saveGoodsIssueAfterUpdate(@Param("silonum") Long silonum,@Param("materialid") String materialid,@Param("postingtype") String postingtype,@Param("batchid") String batchid,@Param("quantity") Long quantity,@Param("uom") String uom);


}
