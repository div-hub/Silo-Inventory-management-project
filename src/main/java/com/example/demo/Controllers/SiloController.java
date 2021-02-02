package com.example.demo.Controllers;


import com.example.demo.Models.SiloTracker;
import com.example.demo.Repository.SiloTrackerRepository;
import com.example.demo.Repository.InvPostingRepository;
import com.example.demo.Service.ISiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class SiloController {
    @Autowired
    private ISiloService iSiloTrackerService;

    @Autowired
    private SiloTrackerRepository siloTrackerRepository;

    @Autowired
    private InvPostingRepository invp;




    //This current stock method returns batchid, open quantity and materialid
    @GetMapping("/currentStock/{usersilonum}")
    List queryCurrentStock(@PathVariable(value = "usersilonum") Long usersilonum) {
        return iSiloTrackerService.currentStock(usersilonum);


    }
    //This current stock method returns all the details pertaining to a particular silo
    @GetMapping(value = "/getTheCurrentStock/{usersilonum}")
    List<SiloTracker> readingSiloBysiloId(@PathVariable(value = "usersilonum")  Long usersilonum) {

        return iSiloTrackerService.findAllBySilonum(usersilonum);

    }


    //Method for performing Goods Issue
    @GetMapping("/goodsIssue/{usersilonum}/{qty}/{uom}/{materialid}")
    public String GoodsIssue(@PathVariable(value = "usersilonum") Long usersilonum, @PathVariable(value = "qty") Long qty,@PathVariable(value = "uom") String uom,@PathVariable(value = "materialid") String materialid) {

       //Input validation to check whether silo exists
        if(!siloTrackerRepository.existsSiloTrackerBySilonum(usersilonum))
            return "Silo not found.Try Again!";
       //Input validation to check whether material exists
        if(siloTrackerRepository.getMaterial(materialid).isEmpty())
            return "Material not found.Try Again!";
       //Input validation to check the combination of silo and material
        if(!(siloTrackerRepository.checkCombination(usersilonum).get(0).equals(materialid)))
            return "Invalid combination of silo and material.Try Again!";


       //Determining the batch
        String batchnum=siloTrackerRepository.findBatchIdBySiloId(usersilonum);



        //Updating the silo_tracker table
        siloTrackerRepository.updateSiloTracker(qty,batchnum);

        //Updating the Inventory Posting table
        invp.saveGoodsIssueAfterUpdate(usersilonum,materialid,"GI",batchnum,qty,uom);


        return ("updated successfully from batch"+"  "+ batchnum);

    }


    //Methods for performing goods receipt
    @GetMapping(value="/goodsReceipt/{usersilonum}/{materialid}/{postingtype}/{quantity}/{uom}/{batchid}")

   public String GoodsReceipt
            ( @PathVariable(value="usersilonum") Long usersilonum ,

              @PathVariable(value="materialid") String materialid,
              @PathVariable(value="postingtype") String postingtype,
              @PathVariable(value="quantity") Long quantity,
              @PathVariable(value="uom") String uom,
              @PathVariable(value="batchid") String batchid){

   //validation to ensure the right cobination of material and silo while storing
    if(siloTrackerRepository.existsSiloTrackerBySilonum(usersilonum)){
      if(!(siloTrackerRepository.checkCombination(usersilonum).get(0).equals(materialid)))
            return "Invalid combination of silo and material.Try Again!";}


        //Saving the data in inventory_posting table
        iSiloTrackerService.serGoodsReceipt(usersilonum, materialid, postingtype, quantity,uom,batchid);

        //Saving the data in the silo_tracker table
        iSiloTrackerService.serSiloTracker(usersilonum,materialid,quantity,quantity,uom,batchid);


        return "Posted  successfully";

    }

}
