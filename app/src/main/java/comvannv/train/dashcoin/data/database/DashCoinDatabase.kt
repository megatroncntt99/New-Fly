package comvannv.train.dashcoin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import comvannv.train.dashcoin.domain.model.CoinById

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
@Database(entities = [CoinById::class], version = 1, exportSchema = false)
abstract class DashCoinDatabase : RoomDatabase() {
    abstract fun dashCoinDao(): DashCoinDao
}