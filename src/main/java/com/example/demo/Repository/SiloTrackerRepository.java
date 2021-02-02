package com.example.demo.Repository;


import com.example.demo.Models.SiloTracker;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import java.util.List;

@Repository
public interface SiloTrackerRepository extends  JpaRepository<SiloTracker,Long>  {

 //Query to determine the batch
 @Transactional
@Query("select batchid from SiloTracker where silonum = :#{#silonum} and timestamp=(select min(timestamp) from SiloTracker where silonum =:#{#silonum} and openquantity>0)")
 String findBatchIdBySiloId(Long silonum);


//Method to get the details of the particular silo
 List<SiloTracker> findAllBySilonum(Long silonum);





    @Query("select materialid from SiloTracker where materialid= :#{#materialid}")
    List getMaterial(String materialid);

    @Query("select materialid from SiloTracker where silonum= :#{#silonum} ")
    List checkCombination(Long silonum);

    @Query("select batchid,materialid,openquantity from SiloTracker where silonum= :#{#silonum}")
    List checkCurrentStock(Long silonum);

    @Query("select batchid from SiloTracker where batchid= :#{#batchid} ")
    String validateBatch(String batchid);

 @Modifying
 @Transactional
 @Query(value="insert into silo_tracker(silonum,materialid,openquantity,batchquantity,uom,batchid) values(:silonum,:materialid,:quantity,:quantity,:uom,:batchid)", nativeQuery = true)
 void saveSiloTracker(@Param("silonum") Long silonum,

                      @Param("materialid") String materialid,
                      @Param("quantity") Long openquantity,
                      @Param("quantity") Long batchquantity,
                      @Param("uom") String uom,
                      @Param("batchid") String batchid);


 //Query to update the record against the determined batch
 @Modifying
 @Transactional
 @Query("update SiloTracker set openquantity=openquantity - :#{#quantity} where batchid=:#{#batchfound} ")
 void updateSiloTracker(@Param("quantity") Long quantity,@Param("batchfound") String batchfound);

 boolean existsSiloTrackerBySilonum(Long usersilonum);

 boolean existsSiloTrackerByMaterialid(String materialid);



}

