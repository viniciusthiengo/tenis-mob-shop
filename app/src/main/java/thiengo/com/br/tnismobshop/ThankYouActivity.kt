package thiengo.com.br.tnismobshop

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_thank_you.*
import kotlinx.android.synthetic.main.content_thank_you.*
import thiengo.com.br.tnismobshop.domain.Sneaker
import thiengo.com.br.tnismobshop.util.Util
import java.util.*

class ThankYouActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sneaker = intent.getParcelableExtra<Sneaker>(Sneaker.KEY)
        val amount = intent.getIntExtra(Sneaker.KEY_AMOUNT, 1)

        /*
         * Código de rastreamento de compra.
         * */
        tv_tracking_code.text = String.format("%s %s", getString(R.string.track_code_label), codeBuyGenerator())

        /*
         * Modelo tênis.
         * */
        tv_model.text = sneaker.model

        /*
         * Marca.
         * */
        iv_brand.setImageResource(sneaker.brand.imageResource)
        iv_brand.contentDescription = sneaker.brand.name

        /*
         * Imagem.
         * */
        iv_sneaker.setImageResource( sneaker.imageResource )
        iv_sneaker.contentDescription = String.format( "%s %s", getString(R.string.sneaker), sneaker.model )
        Util.setImageViewBgColor(this, iv_sneaker)

        /*
         * Preço.
         * */
        tv_price.text = String
            .format( Locale.FRANCE, "R$ %.2f", sneaker.price * amount )
    }

    /*
     * Método que simula a criação de uma código de compra
     * para rastreamento de mercadoria.
     * */
    fun codeBuyGenerator(): String {
        val random = Random()
        var code = ""

        for( i in 0..18 ){
            code += random.nextInt(10).toString()
        }
        return code
    }

    fun backToTenisShop(view: View){
        val it = Intent(this, SneakersActivity::class.java)
        /*
         * Certificando de que não haverá nenhuma outra atividade
         * na pilha de atividades quando o button de volta for
         * acionado.
         * */
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}