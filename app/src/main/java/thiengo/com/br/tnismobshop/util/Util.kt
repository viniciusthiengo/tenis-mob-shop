package thiengo.com.br.tnismobshop.util

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import thiengo.com.br.tnismobshop.R
import thiengo.com.br.tnismobshop.domain.Sneaker


class Util {
    companion object {

        /*
         * Defini a cor de background do ImageView, que na verdade será
         * útil para que a border com radius não tenha contraste com as
         * cores laterais da imagem.
         * */
        fun setImageViewBgColor(context: Context, imageView: ImageView) {
            val bitmap = (imageView.getDrawable() as BitmapDrawable).bitmap
            /*
             * Estamos obtendo a cor do pixel na coordenada (2.1dp, 2.1dp),
             * pois sabemos que nesta coordenada se inicia a verdadeira
             * cor de borda da imagem original, isso tendo em mente
             * que o shape image_view_sneaker.xml tem um padding de
             * 2dp e um background branco e não queremos obtê-lo para a
             * definição dinâmica de imagem de background.
             * */
            val initPixelPosition = Util.convertDpToPixel(context.resources, 2.1F)
            val pixel = bitmap.getPixel(initPixelPosition, initPixelPosition)

            val bgShape = imageView.background.current as GradientDrawable
            bgShape.setColor(pixel)
        }

        fun convertDpToPixel(resources: Resources, dp: Float): Int {
            val metrics = resources.getDisplayMetrics()
            val px = dp * (metrics.densityDpi / 160f)
            return Math.round(px)
        }

        /*
         * Apresenta ou esconde os ImageViews de gênero de acordo com o perfil
         * do tênis em teste.
         * */
        fun setGenre(sneaker: Sneaker, male: ImageView, female: ImageView){
            male.visibility =
                    if( sneaker.isForMale )
                        View.VISIBLE
                    else
                        View.GONE

            female.visibility =
                    if( sneaker.isForFemale )
                        View.VISIBLE
                    else
                        View.GONE
        }

        /*
         * Coloca estrela cheia ou vazia no ImageView de rating de tênis.
         * */
        fun setStar(parent: View, starResourceId: Int, position: Int, rating: Int){
            val ivStar = parent.findViewById(starResourceId) as ImageView

            ivStar.setImageResource(
                    if( position <= rating )
                        R.drawable.ic_star_black_18dp
                    else
                        R.drawable.ic_star_border_white_18dp
            )
        }
    }
}