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
    @Query("select batchId from SiloTracker where siloId = :#{#siloid} and timestamp=(select min(timestamp) from SiloTracker where siloid =:#{#siloid} and openquantity>0)")
    String findBatchIdBySiloId(String siloid);

//Query to get the materialid for input validation(material exists or not)
    @Query("select materialId from SiloTracker where materialId= :#{#materialid}")
    List getMaterial(String materialid);


    //Query to update the record against the determined batch
    @Modifying
    @Transactional
    @Query("update SiloTracker set openQuantity=openQuantity - :#{#quantity} where batchId=:#{#batchfound} ")
    void updateSiloTracker(@Param("quantity") Long quantity,@Param("batchfound") String batchfound);


//Method to check if silo exists
    boolean existsSiloTrackerBySiloId(String siloid);

//Method to perform validation(Correct combination of siloId and materialId)
    @Query("select materialId from SiloTracker where siloId= :#{#siloid} ")
    List checkCombination(String siloid);


    List<SiloTracker> findAllBySiloId(String siloId);


}

