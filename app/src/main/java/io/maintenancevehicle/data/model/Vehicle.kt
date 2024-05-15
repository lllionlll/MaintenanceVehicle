package io.maintenancevehicle.data.model

data class Vehicle(
    /** id to get slide images*/
    val productId: Int,
    /** Xe máy Honda AirBlade 125*/
    val productName: String,
    /** AB1 */
    val productCode: String,
    /** Đỏ */
    val productColor:String,
    /** Honda */
    val productBrand:String,
    /** 98kg */
    val productWeight:String,
    /** 20.3 Pounds */
    val productPrice:String,
    /** Dầu nhớt, dầu phanh, dầu láp, lọc gió, bugi, dây curoa, săm  lốp, nước làm mát. */
    val productDescription:String
)