package com.example.demo.Controllers;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GoodsReceiptRequest {

    private String siloId;
    private Long siloNum;
    private String materialId;
    private String batchId;
    private Long quantity;
    private String uom;
    private String postingType;


}