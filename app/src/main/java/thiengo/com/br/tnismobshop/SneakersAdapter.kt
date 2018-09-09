package thiengo.com.br.tnismobshop

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import thiengo.com.br.tnismobshop.domain.Sneaker
import thiengo.com.br.tnismobshop.util.Util

class SneakersAdapter(
        private val context: Context,
        private val sneakers: List<Sneaker>) :
        RecyclerView.Adapter<SneakersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int ) : SneakersAdapter.ViewHolder {

        val v = LayoutInflater
            .from(context)
            .inflate(R.layout.sneaker, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int) {

        holder.setData( sneakers[position] )
    }

    override fun getItemCount(): Int {
        return sneakers.size
    }

    inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        var ivSneaker: ImageView
        var ivSneakerGallery01: ImageView
        var ivSneakerGallery02: ImageView
        var ivSneakerGallery03: ImageView
        var tvModel: TextView
        var ivBrand: ImageView
        var ivGenreMale: ImageView
        var ivGenreFemale: ImageView
        var tvPriceParcels: TextView
        var tvPrice: TextView

        init {
            itemView.setOnClickListener(this)

            ivSneaker = itemView.findViewById(R.id.iv_sneaker)
            ivSneakerGallery01 = itemView.findViewById(R.id.iv_sneaker_gallery_01)
            ivSneakerGallery02 = itemView.findViewById(R.id.iv_sneaker_gallery_02)
            ivSneakerGallery03 = itemView.findViewById(R.id.iv_sneaker_gallery_03)
            tvModel = itemView.findViewById(R.id.tv_model)
            ivBrand = itemView.findViewById(R.id.iv_brand)
            ivGenreMale = itemView.findViewById(R.id.iv_genre_male)
            ivGenreFemale = itemView.findViewById(R.id.iv_genre_female)
            tvPriceParcels = itemView.findViewById(R.id.tv_price_parcels)
            tvPrice = itemView.findViewById(R.id.tv_price)
        }

        fun setData( sneaker: Sneaker ) {
            setGallery(sneaker)

            tvModel.text = sneaker.model

            ivBrand.setImageResource( sneaker.brand.imageResource )
            ivBrand.contentDescription = sneaker.brand.name

            Util.setGenre(sneaker, ivGenreMale, ivGenreFemale)
            setRating(sneaker)
            setPrice(sneaker)
        }

        private fun setGallery(sneaker: Sneaker){
            /* IMAGEM PRINCIPAL DO TÊNIS */
            ivSneaker.setImageResource( sneaker.imageResource )
            ivSneaker.contentDescription = String.format("%s %s", context.getString(R.string.sneacker_label), sneaker.model)

            ivSneakerGallery01.setImageResource( sneaker.album[0] )
            ivSneakerGallery02.setImageResource( sneaker.album[1] )
            ivSneakerGallery03.setImageResource( sneaker.album[2] )

            /*
             * Atualizando a cor de background das imagens para que
             * a borda fique sem contraste.
             * */
            Util.setImageViewBgColor(context, ivSneaker)
            Util.setImageViewBgColor(context, ivSneakerGallery01)
            Util.setImageViewBgColor(context, ivSneakerGallery02)
            Util.setImageViewBgColor(context, ivSneakerGallery03)
        }

        /*
         * Coloca os resources corretos de estrelas de acordo com os
         * números das avaliações dos tênis.
         * */
        private fun setRating(sneaker: Sneaker){
            val tvRatingAmount = itemView.findViewById(R.id.tv_rating_amount) as TextView
            tvRatingAmount.text = String.format("%s", sneaker.rating.amount)

            Util.setStar(itemView, R.id.iv_rating_star_01, 1, sneaker.rating.stars)
            Util.setStar(itemView, R.id.iv_rating_star_02, 2, sneaker.rating.stars)
            Util.setStar(itemView, R.id.iv_rating_star_03, 3, sneaker.rating.stars)
            Util.setStar(itemView, R.id.iv_rating_star_04, 4, sneaker.rating.stars)
            Util.setStar(itemView, R.id.iv_rating_star_05, 5, sneaker.rating.stars)
        }

        private fun setPrice(sneaker: Sneaker){
            tvPriceParcels.text = sneaker.getPriceParcelsAsString()
            tvPrice.text = sneaker.getPriceAsString()
        }

        override fun onClick(view: View?) {
            val intent = Intent( context, SneakerDetailsActivity::class.java )

            intent.putExtra( Sneaker.KEY, sneakers[adapterPosition] )
            context.startActivity(intent)
        }
    }
}