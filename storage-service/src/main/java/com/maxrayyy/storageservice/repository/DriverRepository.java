package com.maxrayyy.storageservice.repository;

import com.maxrayyy.storageservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    /**
     * 查询未分配车辆的司机及其所属仓库信息
     *
     * @return 未分配司机列表
     */
    @Query("SELECT d FROM Driver d JOIN FETCH d.warehouse WHERE d.assignedVehicle IS NULL")
    List<Driver> findAllUnassignedDriversWithWarehouse();

    /**
     * 根据司机所属仓库获取所有司机信息
     *
     * @param warehouseId 仓库 ID
     * @return 指定仓库的司机列表
     */
    @Query("SELECT d FROM Driver d WHERE d.warehouse.warehouseId = :warehouseId")
    List<Driver> findAllDriversByWarehouseId(Integer warehouseId);

    /**
     * 查询是否有指定的司机是否在空闲状态
     *
     * @param isAvailable 是否空闲
     * @return 符合条件的司机列表
     */
    List<Driver> findByIsAvailable(Boolean isAvailable);
}
