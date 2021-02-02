package com.example.demo.Service;


import com.example.demo.Models.SiloTracker;
import com.example.demo.Repository.SiloTrackerRepository;
import com.example.demo.Repository.InvPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SiloService implements ISiloService {
    @Autowired
    private SiloTrackerRepository siloTrackerRepository;
    @Autowired
    private InvPostingRepository in;


    @Override
   public String findBatchIdBySiloId(@Param("usersilonum") Long usersilonum) {

        return siloTrackerRepository.findBatchIdBySiloId(usersilonum);
    }

    @Override
    public List<SiloTracker> findAllBySilonum(@Param("usersilonum") Long usersilonum)  {


        List<SiloTracker> s1=siloTrackerRepository.findAllBySilonum(usersilonum);

        return s1;
    }


    @Transactional
    @Override
    public String update(@Param("usersiloid") Long usersiloid,@Param("qty") Long qty,@Param("materialid") String materialid) {







        String batchfound=findBatchIdBySiloId(usersiloid);
        siloTrackerRepository.updateSiloTracker (qty,batchfound);
        return batchfound;
   }

       @Override
    public List currentStock(@Param("usersilonum") Long usersilonum){


        return siloTrackerRepository.checkCurrentStock(usersilonum);
        }


        @Override
public void serSiloTracker(@Param("silonum") Long silonum,

                            @Param("materialid") String materialid,
                            @Param("quantity") Long openquantity,
                            @Param("quantity") Long batchquantity,
                            @Param("uom") String uom,
                            @Param("batchid") String batchid){
        siloTrackerRepository.saveSiloTracker(silonum,materialid,openquantity,batchquantity,uom,batchid);
}
@Override
   public void serGoodsReceipt(@Param("silonum") Long silonum,

                               @Param("materialid") String materialid,
                               @Param("postingtype") String postingtype,
                               @Param("quantity") Long quantity,
                               @Param("uom")  String uom,
                               @Param("batchid") String batchid){

        in.saveGoodsReceipt(silonum,materialid,postingtype,quantity,uom,batchid);
    }

    @Override
public List silovalidation(@Param("silonum") Long silonum){
        List silo=siloTrackerRepository.findAllBySilonum(silonum);

        return silo;


}













    }

