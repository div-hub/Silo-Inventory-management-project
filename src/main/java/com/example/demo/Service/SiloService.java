package com.example.demo.Service;


import com.example.demo.Controllers.GoodsReceiptRequest;
import com.example.demo.Models.InventoryPosting;
import com.example.demo.Models.SiloTracker;
import com.example.demo.Repository.SiloTrackerRepository;
import com.example.demo.Repository.InvPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SiloService {
    @Autowired
    private SiloTrackerRepository siloTrackerRepository;
    @Autowired
    private InvPostingRepository invPostingRepository;



//Method to perform Goods Receipt
public void performGoodsReceipt(GoodsReceiptRequest goodsReceiptRequest){

    InventoryPosting inventoryPosting=new InventoryPosting();
    SiloTracker siloTracker=new SiloTracker();

           //Saving data into inventory_posting table
            inventoryPosting.setSiloId(goodsReceiptRequest.getSiloId());
            inventoryPosting.setMaterialId(goodsReceiptRequest.getMaterialId());
            inventoryPosting.setPostingType(goodsReceiptRequest.getPostingType());
            inventoryPosting.setBatchId(goodsReceiptRequest.getBatchId());
            inventoryPosting.setUom(goodsReceiptRequest.getUom());
            inventoryPosting.setQuantity(goodsReceiptRequest.getQuantity());
            invPostingRepository.save(inventoryPosting);

           //Saving data into silo_tracker table
            siloTracker.setSiloId(goodsReceiptRequest.getSiloId());
            siloTracker.setMaterialId(goodsReceiptRequest.getMaterialId());
            siloTracker.setBatchId(goodsReceiptRequest.getBatchId());
            siloTracker.setUom(goodsReceiptRequest.getUom());
            siloTracker.setBatchQuantity(goodsReceiptRequest.getQuantity());
            siloTracker.setOpenQuantity(goodsReceiptRequest.getQuantity());
            siloTrackerRepository.save(siloTracker);

}

//Method to perform Goods Issue
public String performGoodsIssue(String siloId,String materialId,Long Quantity,String uom){

        //Determining the batch
        String batchDetermined=siloTrackerRepository.findBatchIdBySiloId(siloId);

        //Updating the record against the determined batch
        siloTrackerRepository.updateSiloTracker(Quantity,batchDetermined);

        //Saving Goods Issue entry into the inventory_posting table
        InventoryPosting inventoryPosting=new InventoryPosting();
        inventoryPosting.setMaterialId(materialId);
        inventoryPosting.setUom(uom);
        inventoryPosting.setQuantity(Quantity);
        inventoryPosting.setSiloId(siloId);
        inventoryPosting.setBatchId(batchDetermined);
        inventoryPosting.setPostingType("GI");
        invPostingRepository.save(inventoryPosting);

   //Returning the determined batch
    return batchDetermined;
}











}

