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

    private Long inventoryid;
    private String siloid;
    private Long silonum;
    private String materialid;
    private String batchid;
    private Long quantity;
    private String uom;
    private String postingtype;

    @CreationTimestamp
    private Timestamp timestamp;



}
