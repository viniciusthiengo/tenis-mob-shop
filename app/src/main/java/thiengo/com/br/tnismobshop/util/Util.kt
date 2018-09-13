package thiengo.com.br.tnismobshop.util

import android.content.Context
import android.content.res.Resources
import android.databinding.BindingAdapter
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import java.util.*


class Util {
    companion object {

        /*
         * Defini a cor de background do ImageView, que na verdade será
         * útil para que a border com radius não tenha contraste com as
         * cores laterais da imagem.
         * */
        fun setImageViewBgColor( context: Context, imageView: ImageView ) {
            val bitmap = (imageView.getDrawable() as BitmapDrawable).bitmap
            /*
             * Estamos obtendo a cor do pixel na coordenada (2.1dp, 2.1dp),
             * pois sabemos que nesta coordenada se inicia a verdadeira
             * cor de borda da imagem original, isso tendo em mente
             * que o shape image_view_sneaker.xml tem um padding de
             * 2dp e um background branco e não queremos obtê-lo para a
             * definição dinâmica de imagem de background.
             * */
            val initPixelPosition = Util.convertDpToPixel( context.resources, 2.1F )
            val pixel = bitmap.getPixel( initPixelPosition, initPixelPosition )

            val bgShape = imageView.background.current as GradientDrawable
            bgShape.setColor( pixel )
        }

        fun convertDpToPixel( resources: Resources, dp: Float ): Int {
            val metrics = resources.getDisplayMetrics()
            val px = dp * ( metrics.densityDpi / 160F )
            return Math.round( px )
        }

        /*
         * Método que simula a criação de uma código de compra
         * para rastreamento de mercadoria.
         * */
        @JvmStatic
        fun codeBuyGenerator(): String {
            val random = Random()
            var code = ""

            for( i in 0..18 ){
                code += random.nextInt( 10 ).toString()
            }
            return code
        }

        /*
         * Para que o path da imagem seja gerado e então vinculado
         * ao atributo android:src do ImageView de layout.
         * */
        @JvmStatic
        fun getImageSource( context: Context, image: Int ) : String =
                "android.resource://" + context.packageName + "/" + image

        /*
         * Criando um configurador personalizado para que seja
         * possível adicionar a imagem de tênis ao ImageView
         * e também invocar o método Util.setImageViewBgColor()
         * para a configuração de borda de imagem.
         * */
        @JvmStatic
        @BindingAdapter("app:context", "app:src")
        fun setConfImage( iv: ImageView, context: Context, image: Int ) {
            iv.setImageResource( image )
            Util.setImageViewBgColor( context, iv )
        }
    }
}