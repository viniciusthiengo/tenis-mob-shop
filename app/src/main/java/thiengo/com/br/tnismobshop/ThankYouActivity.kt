package thiengo.com.br.tnismobshop

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_thank_you.*
import kotlinx.android.synthetic.main.content_thank_you.*
import thiengo.com.br.tnismobshop.databinding.ContentThankYouBinding
import thiengo.com.br.tnismobshop.domain.Sneaker


class ThankYouActivity : AppCompatActivity() {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_thank_you )
        setSupportActionBar( toolbar )
        supportActionBar?.setDisplayHomeAsUpEnabled( true )

        /*
         * Realizando o binding de Sneaker com o layout de conteúdo
         * (content_thank_you) de ThankYouActivity.
         * */
        val binding = ContentThankYouBinding.bind( nsv_container )

        binding.sneaker = intent.getParcelableExtra( Sneaker.KEY )
        binding.amountSneakers = intent.getIntExtra( Sneaker.KEY_AMOUNT, 0 )
    }

    fun backToTenisShop( view: View ){
        val intent = Intent( this, SneakersActivity::class.java )
        /*
         * Certificando de que não haverá nenhuma outra atividade
         * na pilha de atividades quando o button de volta for
         * acionado.
         * */
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity( intent )
    }
}
