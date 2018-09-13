package thiengo.com.br.tnismobshop

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_sneaker_details.*
import kotlinx.android.synthetic.main.content_sneaker_details.*
import thiengo.com.br.tnismobshop.databinding.ActivitySneakerDetailsBinding
import thiengo.com.br.tnismobshop.databinding.DialogPaymentBinding
import thiengo.com.br.tnismobshop.domain.Sneaker

class SneakerDetailsActivity : AppCompatActivity(),
        View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    lateinit var sneaker: Sneaker
    var amount: Int = 1

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        /*
         * Realizando o bind extamente onde havia o setContentView(),
         * assim poderemos utilizar as Views em código como elas
         * eram utilizadas com o setContentView(), isso, pois o vinculo
         * de layout também ocorre na invocação de
         * DataBindingUtil.setContentView().
         * */
        val activityBinding = DataBindingUtil
                .setContentView<ActivitySneakerDetailsBinding>(
                        this,
                        R.layout.activity_sneaker_details
                )
        setSupportActionBar( toolbar )
        supportActionBar?.setDisplayHomeAsUpEnabled( true )

        sneaker = intent.getParcelableExtra( Sneaker.KEY )
        activityBinding.sneaker = sneaker
        sp_amount.setOnItemSelectedListener( this )
    }

    /*
     * Somente para apresentarmos o menu item de "adicionar ao
     * carrinho"
     * */
    override fun onCreateOptionsMenu( menu: Menu? ): Boolean {
        val inflater = menuInflater
        inflater.inflate( R.menu.menu_sneaker_details, menu )
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
        /*
         * Inflando o layout junto a classe binding dele gerada.
         * */
        val binding = DialogPaymentBinding
                .inflate( layoutInflater)

        binding.sneaker = sneaker
        binding.amountSneakers = amount

        val builder = AlertDialog.Builder( this )

        /*
         * Colocando o layout bound como custom layout do
         * AlertDialog.
         * */
        builder.setView( binding.root )

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
