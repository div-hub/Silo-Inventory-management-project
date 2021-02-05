package com.example.demo.Controllers;


import com.example.demo.Models.SiloTracker;
import com.example.demo.Repository.SiloTrackerRepository;
import com.example.demo.Service.SiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class SiloController {


    @Autowired
    private SiloTrackerRepository siloTrackerRepository;



    @Autowired
    private SiloService siloService;

    //Posting Goods Receipt
    @PostMapping("/goodsReceiptPosting")
    public String  createGoodsReceipt(@RequestBody GoodsReceiptRequest goodsReceiptRequest){

    //Performing input validation (To check the combination of silo and materialId)
        if(siloTrackerRepository.existsSiloTrackerBySiloId(goodsReceiptRequest.getSiloId())){
            if(!siloTrackerRepository.checkCombination(goodsReceiptRequest.getSiloId()).get(0).equals(goodsReceiptRequest.getMaterialId()))
                return "Invalid combination of material and silo.Try Again!";}

    //Calling the method in the Service class to save Goods Receipt
        siloService.performGoodsReceipt(goodsReceiptRequest);
        return "Posted successfully ";

    }


//Performing Goods Issue
    @PutMapping("/GoodsIssuePosting/{userSiloId}/{userMaterialId}/{Quantity}/{uom}")
    public String createGoodsIssue(@PathVariable(value = "userSiloId") String userSiloId,@PathVariable(value = "userMaterialId") String userMaterialId,@PathVariable(value = "Quantity") Long Quantity,@PathVariable(value="uom") String uom){

        //Validation for the existence of Silo
        if(!siloTrackerRepository.existsSiloTrackerBySiloId(userSiloId))
            return "Silo Not Found.Try Again!";
        //Validation for the existence of materialId
        if(siloTrackerRepository.getMaterial(userMaterialId).isEmpty())
            return "Material Not Found.Try Again!";
        //Validation for the correct combination of silo and materialId
        if(!siloTrackerRepository.checkCombination(userSiloId).get(0).equals(userMaterialId))
            return "Invalid Combination of Silo and material.Try Again! ";

       //Determining the batch and posting Goods Issue
        String batchDetermined=siloService.performGoodsIssue(userSiloId,userMaterialId,Quantity,uom);
        return "Update successfully from Batch with Id:" + batchDetermined;
    }


//To get the Current Stock based on the siloId given
    @GetMapping("/getTheCurrentStock/{userSiloId}")
    List<SiloTracker> currentStock(@PathVariable("userSiloId") String userSiloId){
        return siloTrackerRepository.findAllBySiloId(userSiloId);


    }


}
