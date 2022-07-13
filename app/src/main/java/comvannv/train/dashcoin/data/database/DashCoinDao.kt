package comvannv.train.dashcoin.data.database

import androidx.room.*
import comvannv.train.dashcoin.domain.model.CoinById
import comvannv.train.dashcoin.domain.model.Coins
import kotlinx.coroutines.flow.Flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
@Dao
interface DashCoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coinById: CoinById)

    @Delete
    suspend fun deleteCoin(coinById: CoinById)

    @Query("SELECT * FROM coinbyid")
    fun getALlCoins():Flow<List<CoinById>>
}