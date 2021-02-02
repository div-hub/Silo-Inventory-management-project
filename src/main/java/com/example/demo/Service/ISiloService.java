package com.example.demo.Service;

import com.example.demo.Models.SiloTracker;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISiloService {


  // String find1();
  // String find2(SiloTracker customer);
   String findBatchIdBySiloId( Long usersilonum);

   List<SiloTracker> findAllBySilonum(Long usersilonum) ;
     //String update(Long usersiloid,Long qty,String materialid);
    List currentStock(Long usersilonum);


    void serSiloTracker(Long silonum,String materialid,Long openquantity,Long batchquantity,  String uom,String batchid);
    void serGoodsReceipt(Long silonum,String materialid,String postingtype,Long quantity, String uom,String batchid);


    //public List silovalidation(@Param("silonum") Long silonum);



}


