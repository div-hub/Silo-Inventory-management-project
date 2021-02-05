package com.example.demo.Models;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ResponseBody
@Entity
@Table(name="silo_tracker")

public class SiloTracker {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(
            name = "silo_tracker_inventoryid_seq", sequenceName = "silo_tracker_inventoryid_seq",
            allocationSize = 1
    )
    @Column(columnDefinition = "serial")
    private Long inventoryid;
    @Column(name="siloid")
    private String siloId;
    @Column(name="silonum")
    private Long siloNum;
    @Column(name="materialid")
    private String materialId;
    @Column(name="batchid")
    private String batchId;
    @Column(name="batchquantity")
    private Long batchQuantity;
    @Column(name="openquantity")
    private Long openQuantity;
    @Column(name="uom")
    private String uom;
    @CreationTimestamp
    @Column( nullable = false, updatable = false,columnDefinition = "timestamp default current_timestamp")
    private Timestamp timestamp;
    @Version
    private Integer version;

    public SiloTracker() {
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

}
