package com.ananttonk.ayurvedaassignment.repository

import com.ananttonk.ayurvedaassignment.database.CartDao
import com.ananttonk.ayurvedaassignment.database.ProductDao
import com.ananttonk.ayurvedaassignment.model.CartItem
import com.ananttonk.ayurvedaassignment.model.Product
import kotlinx.coroutines.flow.Flow

class AyurvedaRepoImp(
    private val dao: ProductDao,
    private val cartDao: CartDao
) : AyurvedaRepo {
    override suspend fun getSavedProducts(): List<Product> = dao.getAllProducts()

    override suspend fun getProductById(id: Int): Product = dao.getProductById(id)
    override suspend fun addToCart(product: Product) {
        val existingItem = cartDao.getById(product.productId)
        if (existingItem == null) {
            cartDao.addToCart(CartItem(productId = product.productId, quantity = 1))
        } else {
            cartDao.updateQuantity(product.productId, existingItem.quantity + 1)
        }
    }

    override fun getCartList(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems()
    }

    override suspend fun deleteItemFromCart(id: Int, deleteAll: Boolean) {
        val existingItem = cartDao.getById(id) ?: return
        if (deleteAll || existingItem.quantity <= 1) {
            cartDao.getById(id)?.let { cartDao.deleteFromCart(it) }
        } else {
            cartDao.updateQuantity(id, existingItem.quantity - 1)
        }

    }

    override suspend fun insertSampleProduct() {
        dao.deleteAllProducts()
        val sampleData = listOf(
            Product(
                productId = 1,
                title = "Himalaya Liv.52 DS",
                price = 245.0,
                description = "Himalaya Liv.52 DS Tablet, a dietary supplement formulated to support liver health, is a unique blend of herbs known for their hepatoprotective properties. This tablet promotes liver detoxification and enhances the overall functional efficiency of the liver.\n" +
                        "With its natural ingredients, the Himalaya Liv.52 DS Tablet helps maintain a healthy liver by supporting detoxification and improving digestion.",
            ),
            Product(
                productId = 2,
                title = "Dabur Chyawanprakash Sugarfree",
                price = 345.0,
                description = "Diabetes can weaken your immune system which increases the chances of catching infections. Dabur Chyawanprakash has been specially curated to meet the immunity needs of diabetics and people looking for sugar free solutions to boost their immunity. A result of constant innovation by Dabur Research and Development Center, Dabur Chyawanprakash has been clinically tested and found to be safe for Diabetics. Dabur Chyawanprakash has been made from more than 40 Ayurvedic Herbs like Amla, Ashwagandha, Giloy etc. to give you the goodness of Dabur Chyawanprash with no added sugar. Regular consumption of Dabur Chyawanprakash helps strengthen the immune system and helps keep you fit by protecting you from day to day ailments like cough & cold.",
            ),
            Product(
                productId = 3,
                title = "Dabur Chyawanprash",
                price = 351.0,
                description = "Do you think your child has inner strength? Do you worry that your child will fall ill? Do you feel that your child may miss school due to falling ill? Do you doubt that only good food is not enough for your child’s good health? If your answer to any of the above questions is yes, then Dabur Chyawanprash is the answer to your child’s health worries - A recipe used in Ayurveda since ancient times,chyawanprash offers a wide range of health benefits. Dabur Chyawanprash provides strong immunity and good digestion for children and adults alike. Two spoons daily of Dabur Chyawanprash supports immunity and is beneficial for overall health and well-being. It is also good for the digestive and respiratory systems. The primary action of Dabur Chyawanprash is to bolster the immune system and to support the body’s natural ability to fight illnesses . Amalaki (the main ingredient) aids in the elimination of ama (toxins). Therefore, regular use of Dabur Chyawanprash helps strengthen body’s natural defence systems.",
            ),
            Product(
                productId = 4,
                title = "Revital H Multivitamin For Men",
                price = 231.0,
                description = "Revital H multivitamin capsules for daily health is a balanced combination of Natural Ginseng, 10 Vitamins and 9 minerals which can help fill in nutritional gaps and support general well-being for a healthy, active lifestyle",
            )
        )
        dao.insertAllProducts(sampleData)
    }

}