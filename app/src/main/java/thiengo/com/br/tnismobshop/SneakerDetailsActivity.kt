package thiengo.com.br.tnismobshop

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_sneaker_details.*
import kotlinx.android.synthetic.main.content_sneaker_details.*
import thiengo.com.br.tnismobshop.domain.Sneaker
import thiengo.com.br.tnismobshop.util.Util
import java.util.*

class SneakerDetailsActivity : AppCompatActivity(),
        View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    lateinit var sneaker : Sneaker
    var amount: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sneaker_details)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab.setOnClickListener( this )

        /*
         * Foi prefirível utilizar sneaker como uma propriedade local,
         * pois assim evitaremos a necessidade de sempre ter de verificar
         * null.
         * */
        sneaker = intent.getParcelableExtra<Sneaker>(Sneaker.KEY)

        /*
         * Marca.
         * */
        iv_brand.setImageResource(sneaker.brand.imageResource)
        iv_brand.contentDescription = sneaker.brand.name

        /*
         * Gênero.
         * */
        Util.setGenre(sneaker, iv_genre_male, iv_genre_female)

        /*
         * Galeria de imagens.
         * */
        iv_sneaker_01.setImageResource( sneaker.imageResource )
        iv_sneaker_01.contentDescription = String.format("%s %s", getString(R.string.sneaker), sneaker.model)
        Util.setImageViewBgColor(this, iv_sneaker_01)

        iv_sneaker_02.setImageResource( sneaker.album[0] )
        Util.setImageViewBgColor(this, iv_sneaker_02)
        iv_sneaker_03.setImageResource( sneaker.album[1] )
        Util.setImageViewBgColor(this, iv_sneaker_03)
        iv_sneaker_04.setImageResource( sneaker.album[2] )
        Util.setImageViewBgColor(this, iv_sneaker_04)

        /*
         * Preços, quantidade em estoque e botão comprar.
         * */
        tv_price.text = String.format("%s %s", sneaker.getPriceAsString(), getString(R.string.or_in))
        tv_price_parcels.text = String.format("%s %s", getString(R.string.until), sneaker.getPriceParcelsAsString())
        tv_amount.text = sneaker.extraInfo.getStockFormatted(this)
        bt_buy.setOnClickListener(this)

        sp_amount.setOnItemSelectedListener(this)

        /*
         * Recomendação, tipo, composição, descrição.
         * */
        tv_recommended.text = sneaker.extraInfo.recommended
        tv_type.text = sneaker.extraInfo.type
        tv_composition.text = sneaker.extraInfo.composition
        tv_desc.text = sneaker.extraInfo.fullDescription

        /* RATING, COLOCANDO AS ESTRELAS CORRETAS */
        tv_rating_amount.text = String.format("%d", sneaker.rating.amount)
        Util.setStar(window.decorView, R.id.iv_rating_star_01, 1, sneaker.rating.stars)
        Util.setStar(window.decorView, R.id.iv_rating_star_02, 2, sneaker.rating.stars)
        Util.setStar(window.decorView, R.id.iv_rating_star_03, 3, sneaker.rating.stars)
        Util.setStar(window.decorView, R.id.iv_rating_star_04, 4, sneaker.rating.stars)
        Util.setStar(window.decorView, R.id.iv_rating_star_05, 5, sneaker.rating.stars)
    }

    /*
     * Hackcode para que o título da Toolbar seja alterado de
     * acordo com o tênis acionado em lista.
     * */
    override fun onResume() {
        super.onResume()
        toolbar.title = sneaker.model
    }

    /*
     * Somente para apresentarmos o menu item de "adicionar ao
     * carrinho"
     * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_sneaker_details, menu)
        return true
    }

    override fun onClick( view: View ) {
        /*
         * Respondendo ao acionamento do FloatingActionButton.
         * */
        if( view.id == fab.id ){
            Snackbar
                    .make( view, getString( R.string.share_content_shared ), Snackbar.LENGTH_LONG )
                    .setAction( getString( R.string.share_label ), null )
                    .show()
        }

        /*
         * Respondendo ao acionamento do Button de finalizar
         * compra, presente na caixa de diálogo de pagamento.
         * */
        else if( view.id == R.id.bt_finish_buy ){
            buy()
        }

        /*
         * Respondendo ao acionamento do Button "Comprar".
         * */
        else{
            callPaymentDialog()
        }
    }

    /*
     * Hackcode para centralizar o item selecionado no Spinner de
     * quantidade a comprar de um mesmo modelo de tênis.
     * */
    override fun onItemSelected(
            adapterView: AdapterView<*>,
            view: View,
            position: Int,
            id: Long ) {

        (adapterView.getChildAt(0) as TextView).gravity = Gravity.CENTER
        amount = position + 1
    }
    override fun onNothingSelected(adapterView: AdapterView<*>?) {}

    /*
     * Abre o dialog de pagamento com cartão de crédito. AlertDialog
     * está sendo utilizado por já ser suficiente a essa necessidade
     * do aplicativo, mas um DialogFragment poderia ser utilizado
     * também.
     * */
    fun callPaymentDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogLayout = getLayoutInflater()
                .inflate( R.layout.dialog_payment,null )

        builder.setView(dialogLayout)

        dialogLayout
            .findViewById<TextView>(R.id.tv_total_price)
            .text = String.format( Locale.FRANCE, "R$ %.2f", sneaker.price * amount )

        dialogLayout
            .findViewById<Button>(R.id.bt_finish_buy)
            .setOnClickListener( this )

        builder.create().show()
    }

    fun buy() {
        val intent = Intent( this, ThankYouActivity::class.java )

        intent.putExtra( Sneaker.KEY, sneaker )
        intent.putExtra( Sneaker.KEY_AMOUNT, amount )
        startActivity( intent )
        finish()
    }
}