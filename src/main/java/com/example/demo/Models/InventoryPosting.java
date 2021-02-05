package com.example.demo.Models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.persistence.*;
import java.sql.Timestamp;



@Getter
@Setter
@ResponseBody
@EqualsAndHashCode
@Entity
@Table(name="inventory_posting", schema = "public")
public class InventoryPosting {

 @Id
 @Column(columnDefinition = "serial",name="inventoryid", nullable=false, updatable=false)
 @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "inventory_posting_inventoryid_seq")
 @SequenceGenerator(
         name = "inventory_posting_inventoryid_seq", sequenceName = "inventory_posting_inventoryid_seq",
         allocationSize = 1
 )

 private Long inventoryId;
 @Column(name="siloid")
 private String siloId;
 @Column(name="silonum")
 private Long siloNum;
 @Column(name="materialid")
 private String materialId;
 @Column(name="batchid")
 private String batchId;
 @Column(name="quantity")
 private Long quantity;
 @Column(name="uom")
 private String uom;
 @Column(name="postingtype")
 private String postingType;

 @CreationTimestamp
 private Timestamp timestamp;


public InventoryPosting(){

}
}
